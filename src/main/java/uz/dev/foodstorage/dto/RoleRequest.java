
package uz.dev.foodstorage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record RoleRequest(@NotBlank(message = "name is required") String name,
                          @NotBlank(message = "code is required") String code) {

}
