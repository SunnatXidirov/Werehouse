package uz.dev.foodstorage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Date;

@Builder
public record ProductRequest(@NotBlank(message = "name is required") String name,
                             @NotBlank(message = "quantity is required") double quantity,
                             @NotBlank(message = "Term is required") Date term,
                             @NotBlank(message = "category is required")long categoryId) {
}
