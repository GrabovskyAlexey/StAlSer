package ru.gb.stalser.core.services.interfaces;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import ru.gb.stalser.api.dto.auth.*;
import ru.gb.stalser.core.entity.User;

import java.security.Principal;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);

    AuthResponse register(RegisterRequest registerRequest);

    void updateUser(User user);

    Boolean existsByEmail(String email);

    AuthResponse authenticate(AuthRequest authRequest);

    AuthResponse registerPassUpdate(AuthRequestPassUpdate authRequestPassUpdate, Principal principal);

    AuthResponse refresh(String refreshToken) throws AuthException;

    void createPasswordResetTokenForUser(User user, String token);

    GenericResponse resetPassword(HttpServletRequest request, String userName);

    String showChangePasswordPage(Locale locale, Model model, String token);

    AuthResponse setNewPassword (RequestNewPass requestNewPass);



}
