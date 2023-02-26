package ru.gb.stalser.core.services.interfaces;

public interface ISecurityUserService {

    String validatePasswordResetToken(String token);

}
