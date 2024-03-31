package uz.dev.foodstorage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.foodstorage.dao.UserRepository;
import uz.dev.foodstorage.domain.User;
import uz.dev.foodstorage.dto.*;
import uz.dev.foodstorage.exception.MyConflictException;
import uz.dev.foodstorage.exception.MyNotFoundException;
import uz.dev.foodstorage.utils.jwt.TokenService;


import java.util.Optional;

@Service
@Slf4j
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenService accessTokenService;
    private final TokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository,
                       @Qualifier("accessTokenService") TokenService accessTokenService,
                       @Qualifier("refreshTokenService") TokenService refreshTokenService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;

    }


    public ResponseToken login(@NonNull AuthRequest authRequest) {
        Optional<User> optional = userRepository.findByUsername(authRequest.username());
        if (optional.isEmpty())
            throw new MyNotFoundException("user not found by this username");
        User user = optional.get();
        boolean matches = passwordEncoder.matches(authRequest.password(), user.getPassword());
        if (!matches || !user.isActive())
            throw new MyConflictException("wrong password or user not active");
        String accessToken = accessTokenService.generateToken(new uz.dev.foodstorage.config.UserDetails(user));
        String refreshToken = refreshTokenService.generateToken(new uz.dev.foodstorage.config.UserDetails(user));
        return new ResponseToken("Bearer", accessToken, refreshToken);
    }

    public ResponseToken refreshToken(@NonNull RefreshTokenRequest tokenRequest) {
        String token = tokenRequest.token();
        if (accessTokenService.isValid(token)) {
            throw new MyConflictException("Refresh token invalid");
        }
        String subject = accessTokenService.getSubject(token);
        UserDetails userDetails = loadUserByUsername(subject);
        String accessToken = accessTokenService.generateToken(userDetails);
        return new ResponseToken("Bearer", accessToken, tokenRequest.token());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isEmpty())
            throw new MyNotFoundException("user not found");
        return new uz.dev.foodstorage.config.UserDetails(optional.get());
    }




}
