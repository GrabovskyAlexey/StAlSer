package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.ConfirmToken;
import ru.gb.stalser.core.repositories.ConfirmTokenRepository;
import ru.gb.stalser.core.services.interfaces.SecurityUserService;

import javax.security.auth.message.AuthException;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {

    private final ConfirmTokenRepository confirmTokenRepository;

    @Override
    public String validatePasswordResetToken(String userName) throws AuthException {
        final ConfirmToken confirmToken = confirmTokenRepository.findById(userName)
                .orElseThrow(() -> new AuthException("Невалидный токен"));

        return !isTokenFound(confirmToken) ? "invalidToken"
                : isTokenExpired(confirmToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(ConfirmToken confirmToken) {
        return confirmToken != null;
    }

    private boolean isTokenExpired(ConfirmToken confirmToken) {
        final Calendar cal = Calendar.getInstance();
        return confirmToken.getCreatedAt().before(cal.getTime());
    }



}
