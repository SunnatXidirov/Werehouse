package uz.dev.foodstorage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


import java.util.List;

@Builder
public record UserRequest(@NotBlank(message = "name is required") String name,
                          @NotBlank(message = "username is required") String username,
                          @NotBlank(message = "password is required") String password
) {
}
