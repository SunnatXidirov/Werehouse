package uz.dev.foodstorage.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ResponseCategory {
    private Long id;
    private String name;
    private String categoryName;
    private long productCount;
    private String status;
    private long createdBy;
    private LocalDateTime createdAt;
    private long updatedBy;
    private LocalDateTime updatedAt;
}
