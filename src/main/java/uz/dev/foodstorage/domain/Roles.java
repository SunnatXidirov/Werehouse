package uz.dev.foodstorage.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;


import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class Roles implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String code;
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
    private Set<Permission> permission;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.code;
    }

}
