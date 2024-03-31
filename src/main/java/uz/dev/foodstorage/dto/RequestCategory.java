package uz.dev.foodstorage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record RequestCategory(@NotBlank(message = "category name is required")String name) {
}
