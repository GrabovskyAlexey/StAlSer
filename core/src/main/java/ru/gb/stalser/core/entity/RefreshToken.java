package ru.gb.stalser.core.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("RefreshToken")
public class RefreshToken {
    private String id;
    private String token;
}
