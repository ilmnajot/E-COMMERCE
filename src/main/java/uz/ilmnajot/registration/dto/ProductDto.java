package uz.ilmnajot.registration.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uz.ilmnajot.registration.entity.Basket;
import uz.ilmnajot.registration.entity.Image;
import uz.ilmnajot.registration.entity.Product;
import uz.ilmnajot.registration.entity.User;

import java.util.List;
import java.util.UUID;

@Data
public class ProductDto {

    private UUID id;

    private String name;

    private String description;

    private Integer quantity;

    private String color;

    private Double price;

    private Boolean exists = false;

    private List<Image> images;


        public static ProductDto toDto(Product product){
            ProductDto dto = new ProductDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setQuantity(product.getQuantity());
            dto.setColor(product.getColor());
            dto.setPrice(product.getPrice());
            dto.setExists(product.isExists());
            dto.setImages(product.getImages());
            return dto;
        }

}
