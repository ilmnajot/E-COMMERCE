package uz.ilmnajot.registration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String name;

    private String surname;

    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    private Card card;


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
}
