package ru.gb.stalser.core.services;

import com.sun.xml.bind.v2.TODO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.gb.stalser.api.dto.auth.*;
import ru.gb.stalser.core.entity.PasswordResetToken;
import ru.gb.stalser.core.entity.RefreshToken;
import ru.gb.stalser.core.entity.Role;
import ru.gb.stalser.core.entity.User;
import ru.gb.stalser.core.exceptions.EmailAlreadyExistsException;
import ru.gb.stalser.core.exceptions.InviteWithoutBoardException;
import ru.gb.stalser.core.exceptions.PasswordNotConfirmedException;
import ru.gb.stalser.core.exceptions.UserAlreadyExistsException;
import ru.gb.stalser.core.repositories.RefreshTokenRepository;
import ru.gb.stalser.core.repositories.UserRepository;
import ru.gb.stalser.core.services.interfaces.PasswordResetTokenService;
import ru.gb.stalser.core.services.interfaces.RoleService;
import ru.gb.stalser.core.services.interfaces.UserService;
import ru.gb.stalser.core.utils.JwtTokenUtil;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    private final RefreshTokenRepository refreshRepository;

    private final PasswordResetTokenServiceImpl passwordResetTokenServiceImpl;

    private final MessageSource messages;

    private final Environment env;

    private final JavaMailSender mailSender;

    private final SecurityUserService securityUserService;

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
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenServiceImpl.save(myToken);
    }

    @Override
    public GenericResponse resetPassword(HttpServletRequest request, String userName){
        User user = userRepository.findByLogin(userName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", userName)));
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request),
                request.getLocale(), token, user));
        return new GenericResponse(
                messages.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
    }

    @Override
    public String showChangePasswordPage(Locale locale, Model model, String token) {
        String result = securityUserService.validatePasswordResetToken(token);
        if(result != null) {
            String message = messages.getMessage("auth.message." + result, null, locale);
  //          TODO Отправляем страницу login
            return "redirect:/login.html?lang=" + locale.getLanguage() + "&message=" + message;
        } else {
            //          TODO Отправляем страницу, с которой отправляется запрос на password/new
            model.addAttribute("token", token);
            return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        }
    }

    @Override
    public AuthResponse setNewPassword(RequestNewPass requestNewPass){

        String result = securityUserService.validatePasswordResetToken(requestNewPass.getToken());

        if (result != null) {
            throw new PasswordNotConfirmedException("Токен пользователя не подтвержден");
        }

        PasswordResetToken passwordResetToken = passwordResetTokenServiceImpl.findPasswordResetTokenByToken(requestNewPass.getToken());
        User user = passwordResetToken.getUser();

        user.setPassword(bCryptPasswordEncoder.encode(requestNewPass.getNewPassword()));
        userRepository.save(user);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), requestNewPass.getNewPassword(), mapRolesToAuthorities(user.getRoles()));
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        return new AuthResponse(accessToken, refreshToken);

    }


    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/user/changePassword?token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }



}
