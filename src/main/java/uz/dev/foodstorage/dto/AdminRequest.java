package uz.dev.foodstorage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AdminRequest(@NotBlank(message = "name is required") String name,
                           @NotBlank(message = "username is required") String username,
                           @NotBlank(message = "password is required") String password,
                           @NotBlank(message ="role is required" ) String roleCode,
                           @NotBlank(message = "permission is required") String permissionCode) {
}
