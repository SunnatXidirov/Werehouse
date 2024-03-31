package uz.dev.foodstorage.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.dev.foodstorage.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double quantity;
    @Column(nullable = false)
    private Date term;
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Category category;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @CreatedBy
    private long createdBy;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Date createdAt;
    @LastModifiedBy
    private long updatedBy;
    @LastModifiedDate
    private Date updatedAt;

}
