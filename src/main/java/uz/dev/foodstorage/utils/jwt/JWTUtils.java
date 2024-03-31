package uz.dev.foodstorage.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;


@Component
public class JWTUtils {

    public boolean isTokenValid(String token, String secret) {
        String subject = getSubject(token, secret);
        Date expiration = getClaim(token, Claims::getExpiration, secret);
        return (Objects.nonNull(subject) && !isTokenExpired(expiration));
    }

    public String getSubject(String token, String secret) {
        return getClaim(token, Claims::getSubject, secret);
    }

    public <T> T getClaim(String token, Function<Claims, T> func, String secret) {
        Claims claims = jwtClaims(token, secret);
        return func.apply(claims);
    }

    public String jwt(@NonNull String subject,
                      @NonNull String secret,
                      int amountToAdd, TemporalUnit unit) {
        return jwtBuilder(subject, secret, amountToAdd, unit).compact();
    }


    private boolean isTokenExpired(Date expiration) {
        Instant now = Instant.now(Clock.systemDefaultZone());
        return now.isAfter(expiration.toInstant());
    }

    private Claims jwtClaims(@NonNull String token, @NonNull String secret) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private JwtBuilder jwtBuilder(String subject,
                                  String secret,
                                  int amountToAdd,
                                  TemporalUnit unit) {
        Instant now = Instant.now(Clock.systemDefaultZone());
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(now.plus(amountToAdd, unit)))
                .signWith(getSecretKey(secret));
    }

    private Key getSecretKey(String tokenKey) {
        byte[] bytes = Decoders.BASE64.decode(tokenKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}
