package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.stalser.api.dto.auth.AuthRequest;
import ru.gb.stalser.api.dto.auth.AuthResponse;
import ru.gb.stalser.api.dto.auth.RegisterRequest;
import ru.gb.stalser.core.entity.Role;
import ru.gb.stalser.core.entity.User;
import ru.gb.stalser.core.exceptions.EmailAlreadyExistsException;
import ru.gb.stalser.core.exceptions.UserAlreadyExistsException;
import ru.gb.stalser.core.repositories.UserRepository;
import ru.gb.stalser.core.services.interfaces.RoleService;
import ru.gb.stalser.core.services.interfaces.UserService;
import ru.gb.stalser.core.utils.JwtTokenUtil;

import javax.persistence.EntityNotFoundException;
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

        return new AuthResponse(jwtTokenUtil.generateToken(userDetails));
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
        String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
