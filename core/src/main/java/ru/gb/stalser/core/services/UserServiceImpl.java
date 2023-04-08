package ru.gb.stalser.core.services;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.stalser.api.dto.ConfirmToken;
import ru.gb.stalser.api.dto.auth.*;
import ru.gb.stalser.core.entity.RefreshToken;
import ru.gb.stalser.core.entity.Role;
import ru.gb.stalser.core.entity.User;
import ru.gb.stalser.core.exceptions.*;
import ru.gb.stalser.api.dto.notify.SimpleTextEmailMessage;
import ru.gb.stalser.api.dto.util.MessageDto;
import ru.gb.stalser.core.entity.*;
import ru.gb.stalser.core.exceptions.EmailAlreadyExistsException;
import ru.gb.stalser.core.exceptions.PasswordNotConfirmedException;
import ru.gb.stalser.core.exceptions.ResetPasswordTokenExeption;
import ru.gb.stalser.core.exceptions.UserAlreadyExistsException;
import ru.gb.stalser.core.repositories.PasswordResetTokenRepository;
import ru.gb.stalser.core.repositories.RefreshTokenRepository;
import ru.gb.stalser.core.repositories.UserRepository;
import ru.gb.stalser.core.services.interfaces.RoleService;
import ru.gb.stalser.core.services.interfaces.UserService;
import ru.gb.stalser.core.utils.JwtTokenUtil;
import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import javax.security.auth.message.AuthException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${stalser.invite.url}")
    private String url;
    @Value("${stalser.invite.email-address-from}")
    private String emailFrom;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenRepository refreshRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final KafkaTemplate<String, SimpleTextEmailMessage> kafkaTemplate;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User id=" + id + "{} not found"));
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new EntityNotFoundException("User login=" + login + "{} not found"));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email= {" + email + "} not found"));
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {

        Role role = roleService.findByName("ROLE_USER")
                .orElse(new Role("ROLE_USER", "Пользователь"));
        if (Objects.isNull(role.getId())) {
            roleService.save(role);
        }
        if (userRepository.existsByLoginIgnoreCase(registerRequest.getLogin())) {
            throw new UserAlreadyExistsException(
                    "Пользователь с логином {" + registerRequest.getLogin() + "} уже существует");
        }
        if (userRepository.existsByEmailIgnoreCase(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Пользователь с электронной почтой {" + registerRequest.getEmail() + "} уже существует");
        }
        String uuid = UUID.randomUUID().toString();
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        User user = new User();
        user.setLogin(registerRequest.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setIsActivated(false);
        user.setIsEnabled(true);
        user.setActivationCode(uuid);
        user.setRoles(roles);
        userRepository.save(user);

        final UserDetails userDetails = loadUserByUsername(registerRequest.getLogin());

        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        final RefreshToken refresh = RefreshToken.builder()
                .id(userDetails.getUsername())
                .token(refreshToken)
                .build();
        refreshRepository.save(refresh);
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));

        UserDetails userDetails = loadUserByUsername(authRequest.getLogin());
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        final RefreshToken refresh = RefreshToken.builder()
                .id(userDetails.getUsername())
                .token(refreshToken)
                .build();
        refreshRepository.save(refresh);
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refresh(String refreshToken) throws AuthException {
        if (jwtTokenUtil.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtTokenUtil.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshRepository.findById(login)
                    .orElseThrow(() -> new AuthException("Невалидный JWT токен")).getToken();
            if (saveRefreshToken.equals(refreshToken)) {
                final UserDetails user = loadUserByUsername(login);
                final String accessToken = jwtTokenUtil.generateAccessToken(user);
                final String newRefreshToken = jwtTokenUtil.generateRefreshToken(user);
                final RefreshToken refresh = RefreshToken.builder()
                        .id(login)
                        .token(newRefreshToken)
                        .build();
                refreshRepository.save(refresh);
                return new AuthResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    @Override
    public User getUserFromPrincipal(Principal principal){
        return findByLogin(principal.getName());
    }

    @Override
    @Transactional
    public AuthResponse activateUser(String confirmToken) {
        ConfirmToken token = jwtTokenUtil.parseConfirmToken(confirmToken);
        if(!token.isValidToken() || token.getType() != ConfirmToken.TokenType.REGISTER) {
            throw new IncorrectConfirmTokenException("Неправильный код подтверждения");
        }
        User user = findByEmail(token.getEmail());
        if (user.getIsActivated()) {
            throw new UserAlreadyActivatedException("Пользователь уже активирован");
        }
        if (!user.getActivationCode().equals(token.getCode())) {
            throw new IncorrectConfirmTokenException("Неправильный код подтверждения");
        }
        user.setIsActivated(true);
        userRepository.save(user);
        final UserDetails details = loadUserByUsername(user.getLogin());
        final String accessToken = jwtTokenUtil.generateAccessToken(details);
        final String newRefreshToken = jwtTokenUtil.generateRefreshToken(details);
        final RefreshToken refresh = RefreshToken.builder()
                .id(user.getLogin())
                .token(newRefreshToken)
                .build();
        refreshRepository.save(refresh);
        return new AuthResponse(accessToken, newRefreshToken);
    }

    @Override
    public AuthResponse registerPassUpdate(AuthRequestPassUpdate authRequestPassUpdate, Principal principal){

        User user = userRepository.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", principal.getName())));

        if (!bCryptPasswordEncoder.matches(authRequestPassUpdate.getOldPassword(), user.getPassword())) {
            throw new PasswordNotConfirmedException(String.format("Пароль пользователя '%s' не подтвержден", user.getLogin()));
        }
            user.setPassword(bCryptPasswordEncoder.encode(authRequestPassUpdate.getNewPassword()));
            userRepository.save(user);
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), authRequestPassUpdate.getNewPassword(), mapRolesToAuthorities(user.getRoles()));
            String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
            String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
            return new AuthResponse(accessToken, refreshToken);
    }
    @Override
    public MessageDto resetPassword(String userEmail){
        User user = null;
        SimpleTextEmailMessage message = null;
        user = userRepository.findByEmail(userEmail).get();
        if (user == null){
            return new MessageDto("На предоставленный адрес электронной почты выслано письмо");
        }
        String token = UUID.randomUUID().toString();
        ConfirmToken confirmToken = ConfirmToken.builder()
                .code(token)
                .type(ConfirmToken.TokenType.RESET_PASSWORD)
                .email(user.getEmail())
                .build();
        String resetToken = jwtTokenUtil.generateConfirmationToken(confirmToken);
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .id(user.getEmail())
                .token(resetToken)
                .build();
        passwordResetTokenRepository.save(passwordResetToken);
        message = configureMessage(user.getEmail(), user.getLogin());
        if (user.getIsActivated()) {
            //если пользователь активирован, кидаем его на страницу где он сформирует requestNewPass и отправит на /password/new
            message.setText(url + "/password/newpage?code=" + resetToken);
            kafkaTemplate.send("simple-text-email", message);
        }
        return new MessageDto(String.format("Отправлено письмо на электронную почту '%s' для смены пароля", user.getEmail()));
    }
    @Override
    public AuthResponse setNewPassword(RequestNewPass requestNewPass) throws AuthException {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findById(requestNewPass.getUserEmail())
                .orElseThrow(() -> new ResetPasswordTokenExeption("На почту отправителя токен не отправлялся"));
        ConfirmToken confirmTokenFromRedis = jwtTokenUtil.parseConfirmToken(passwordResetToken.getToken());
       ConfirmToken confirmTokenFromUser = jwtTokenUtil.parseConfirmToken(requestNewPass.getToken());
       if(!confirmTokenFromUser.getType().equals(ConfirmToken.TokenType.RESET_PASSWORD)){
           throw new ResetPasswordTokenExeption("Некорректный тип токена");
       }
       if(confirmTokenFromUser.getCode().equals(confirmTokenFromRedis.getCode())){
           User user = userRepository.findByLogin(requestNewPass.getUserEmail())
                   .orElseThrow(() -> new UsernameNotFoundException(String.format("User with emile '%s' not found", requestNewPass.getUserEmail())));
           user.setPassword(bCryptPasswordEncoder.encode(requestNewPass.getNewPassword()));
           userRepository.save(user);
           passwordResetTokenRepository.deleteById(requestNewPass.getUserEmail());
           UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), requestNewPass.getNewPassword(), mapRolesToAuthorities(user.getRoles()));
           String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
           String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
           return new AuthResponse(accessToken, refreshToken);
       }
       throw new ResetPasswordTokenExeption("Token не подтвержден");
    }

    private SimpleTextEmailMessage configureMessage(String email, String userName) {
        return SimpleTextEmailMessage.builder()
                .from(emailFrom)
                .to(email)
                .subject("Ссылка для сброса пароля для пользователя " + userName)
                .build();
    }
}

