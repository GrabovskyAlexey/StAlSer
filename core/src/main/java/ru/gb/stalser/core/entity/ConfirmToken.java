package ru.gb.stalser.core.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@RedisHash("ConfirmToken")
public class ConfirmToken {
    private static final int EXPIRATION = 60 * 24;
    private String id;
    private String token;
    private Date createdAt;

    public ConfirmToken(String id, String token) {
        this.id = id;
        this.token = token;
        this.createdAt = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}
