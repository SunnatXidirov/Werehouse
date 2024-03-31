package uz.dev.foodstorage.dto;

import lombok.Builder;

@Builder
public record ResponseDto(String message, boolean check) {


}
