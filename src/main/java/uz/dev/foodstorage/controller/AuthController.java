package uz.dev.foodstorage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.foodstorage.GlobalURL.authUrl.AuthUrl;
import uz.dev.foodstorage.dto.*;
import uz.dev.foodstorage.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping(AuthUrl.auth)
public class AuthController {
    private final AuthService authService;

    @PostMapping(AuthUrl.login)
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        ResponseToken responseToken = authService.login(authRequest);
        return ResponseEntity.ok(responseToken);
    }
    @PostMapping(AuthUrl.refreshToken)
    public ResponseToken refresh(@Valid @RequestParam RefreshTokenRequest tokenRequest) {
        authService.refreshToken(tokenRequest);
        return null;
    }


}
