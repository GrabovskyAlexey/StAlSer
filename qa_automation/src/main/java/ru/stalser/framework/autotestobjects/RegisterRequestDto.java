package ru.stalser.framework.autotestobjects;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gb.stalser.api.dto.auth.RegisterRequest;

import java.time.LocalDateTime;

@RequiredArgsConstructor(staticName = "of")
@Data
public class RegisterRequestDto {

    private final RegisterRequest registerRequest;
    private String userId;
    private LocalDateTime creationTime = LocalDateTime.now();
    private LocalDateTime updatedTime = LocalDateTime.now();
    private String token;
}
