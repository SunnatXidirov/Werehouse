package uz.dev.foodstorage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.lang.NonNull;

@Builder
public record AuthRequest(@NotBlank(message = "username is required") String username,
                          @NotBlank(message = "password is required") String password) {
}
