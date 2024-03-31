package uz.dev.foodstorage.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.dev.foodstorage.domain.enums.UserStatus;


import java.util.Date;
import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;
    @CreatedBy
    private long createdBy;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Date createdAt;
    @LastModifiedBy
    private long updatedBy;
    @LastModifiedDate
    private Date updatedAt;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Roles> roles;

    public boolean isActive() {
        return UserStatus.ACTIVE.equals(this.status);
    }


}
