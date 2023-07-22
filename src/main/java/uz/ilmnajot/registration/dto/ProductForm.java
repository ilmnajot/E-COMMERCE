package uz.ilmnajot.registration.dto;

import lombok.Data;
import uz.ilmnajot.registration.entity.Basket;
import uz.ilmnajot.registration.entity.Image;
import uz.ilmnajot.registration.entity.User;

import java.util.List;
@Data
public class ProductForm {

    private String name;

    private String description;

    private Integer quantity;

    private String color;

    private Double price;

    private List<Image> images;



}
