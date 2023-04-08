package ru.gb.stalser.core.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("PasswordResetToken")
public class PasswordResetToken {
    private String id;
    private String token;
}
