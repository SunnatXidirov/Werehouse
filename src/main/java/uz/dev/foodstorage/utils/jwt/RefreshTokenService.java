package uz.dev.foodstorage.utils.jwt;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Service
@Slf4j
public class RefreshTokenService implements TokenService {

    private final JWTUtils jwtUtils;
    private final String secret;
    private final Integer amountToAdd;
    private final TemporalUnit timeUnit = ChronoUnit.DAYS;

    public RefreshTokenService(@Lazy JWTUtils jwtUtils,
                               @Value("${jwt.refresh.token.secret}") String secret,
                               @Value("${jwt.refresh.token.expiry.adding.amount}") Integer amountToAdd) {
        this.jwtUtils = jwtUtils;
        this.secret = secret;
        this.amountToAdd = amountToAdd;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return jwtUtils.jwt(userDetails.getUsername(), secret, amountToAdd, timeUnit);
    }

    @Override
    public boolean isValid(String token) {
        return jwtUtils.isTokenValid(token, secret);
    }

    @Override
    public String getSubject(String token) {
        return jwtUtils.getSubject(token, secret);
    }

}
