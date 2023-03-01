package ru.gb.stalser.core.services.interfaces;

import javax.security.auth.message.AuthException;

public interface SecurityUserService {

    String validatePasswordResetToken(String token) throws AuthException;

}
