package ru.gb.stalser.core.utils;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.gb.stalser.api.dto.ConfirmToken;
import ru.gb.stalser.core.exceptions.IncorrectConfirmTokenException;

import java.util.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.gb.stalser.api.dto.ConfirmToken.TokenType;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.access.secret}")
    private String accessSecret;

    @Value("${jwt.access.lifetime}")
    private Integer accessLifetime;

    @Value("${jwt.refresh.secret}")
    private String refreshSecret;

    @Value("${jwt.refresh.lifetime}")
    private Integer refreshLifetime;


    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        final Instant refresh = LocalDateTime.now().plusMinutes(accessLifetime).toInstant(ZoneOffset.UTC);
        final Date expiredDate = Date.from(refresh);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, accessSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public List<String> getRoles(String token) {
        return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return getClaims(token, accessSecret);
    }

    private Claims getClaims(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, refreshSecret);
    }

    private boolean validateToken(String token, String secret) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();;
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public boolean validateRefreshToken(String refreshToken){
        return validateToken(refreshToken, refreshSecret);
    }

    public String generateRefreshToken(UserDetails user) {
        Date issuedDate = new Date();
        final Instant refresh = LocalDateTime.now().plusDays(refreshLifetime).toInstant(ZoneOffset.UTC);
        final Date refreshExpiration = Date.from(refresh);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS256, refreshSecret)
                .compact();
    }

    public String generateConfirmationToken(ConfirmToken token) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("confirm", token);
        Calendar calendar = Calendar.getInstance();
        Date issuedDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date expiredDate = calendar.getTime();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, accessSecret)
                .compact();
    }

    public ConfirmToken parseConfirmToken(String token) {
        try {
            final Map<String, String> confirm =
                    getClaimFromToken(token, (Function<Claims, Map<String, String>>) claims -> claims.get("confirm", Map.class));

            final ConfirmToken confirmToken = ConfirmToken.builder()
                    .code(confirm.get("code"))
                    .email(confirm.get("email"))
                    .type(TokenType.getByName(confirm.get("type")))
                    .build();
            if (confirmToken.isValidToken()) {
                return confirmToken;
            }
        } catch (SignatureException ignored) {

        }
        throw new IncorrectConfirmTokenException("Ошибка в данных токена");
    }
}
