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
import java.util.List;
import java.util.UUID;

@Entity(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    private Integer quantity;

    private String color;

    private Double price;

    private boolean exists = true;

    @OneToMany
    private List<Image> images;

    @ManyToOne
    private Basket basket;

    @ManyToOne
    private User user;



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
