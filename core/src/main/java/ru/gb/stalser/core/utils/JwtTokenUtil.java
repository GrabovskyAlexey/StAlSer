package ru.gb.stalser.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.gb.stalser.api.dto.ConfirmToken;
import ru.gb.stalser.core.exceptions.IncorrectConfirmTokenException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.gb.stalser.api.dto.ConfirmToken.TokenType;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
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
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
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
                .signWith(SignatureAlgorithm.HS256, secret)
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
