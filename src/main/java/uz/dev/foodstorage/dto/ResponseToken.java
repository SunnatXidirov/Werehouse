package uz.dev.foodstorage.dto;


import lombok.Builder;

@Builder
public record ResponseToken(String tokenType, String accessToken, String refreshToken) {
}
