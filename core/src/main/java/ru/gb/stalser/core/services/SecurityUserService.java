package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.PasswordResetToken;
import ru.gb.stalser.core.services.interfaces.ISecurityUserService;
import ru.gb.stalser.core.services.interfaces.PasswordResetTokenService;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class SecurityUserService implements ISecurityUserService {

    private final PasswordResetTokenService passwordResetTokenService;

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenService.findPasswordResetTokenByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getCreatedAt().before(cal.getTime());
    }



}
