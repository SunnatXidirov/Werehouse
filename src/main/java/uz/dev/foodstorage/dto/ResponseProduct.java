package uz.dev.foodstorage.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record ResponseProduct(long productId, String name,
                              double quantity, Date term, String category) {

}
