package ru.gb.stalser.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.stalser.core.entity.PasswordResetToken;
import ru.gb.stalser.core.repositories.PasswordResetTokenRepository;
import ru.gb.stalser.core.services.interfaces.PasswordResetTokenService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken){
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken findById(Long id){
        return passwordResetTokenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PasswordResetToken id=" + id + "{} not found"));
    }

    @Override
    public PasswordResetToken findPasswordResetTokenByToken(String token){
        return passwordResetTokenRepository.findPasswordResetTokenByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("PasswordResetToken token=" + token + "{} not found"));
    }

    @Override
    public void deleteById(Long id){
        passwordResetTokenRepository.deleteById(id);
    }
}
