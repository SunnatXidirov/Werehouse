package uz.dev.foodstorage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RequestCategoryWithParent(@NotBlank(message = "category name is required")String name,
                                        @NotBlank(message = "parent id is required")long id) {
}
