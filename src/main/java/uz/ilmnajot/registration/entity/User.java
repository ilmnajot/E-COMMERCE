package uz.ilmnajot.registration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ilmnajot.registration.enums.RoleName;

import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private RoleName roleName;

    private String emailCode;

    @ColumnDefault("false")
    private boolean enabled = false;

    private String hashedCode;

    private String code;


    @Column(updatable = false, name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @Column(name = "createdBy", updatable = false)
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updatedBy")
    private String updatedBy;

    public User(String fullName, String username, String password, RoleName roleName, boolean enabled) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.roleName = roleName;
        this.enabled = enabled;
    }




}
