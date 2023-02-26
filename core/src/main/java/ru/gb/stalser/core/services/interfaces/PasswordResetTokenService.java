package ru.gb.stalser.core.services.interfaces;

import ru.gb.stalser.core.entity.PasswordResetToken;

public interface PasswordResetTokenService {

    PasswordResetToken save(PasswordResetToken passwordResetToken);

    PasswordResetToken findById(Long id);

    PasswordResetToken findPasswordResetTokenByToken(String token);


    void deleteById(Long id);

}
