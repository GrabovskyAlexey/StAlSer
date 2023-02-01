package ru.gb.stalser.api.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthRequest {

    private String login;
    private String password;
}
