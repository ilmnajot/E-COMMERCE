package uz.ilmnajot.registration.service;

import uz.ilmnajot.registration.apiResponse.ApiResponse;
import uz.ilmnajot.registration.dto.ProductDto;
import uz.ilmnajot.registration.dto.ProductForm;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto getProduct(UUID id);

    ProductDto registerProduct(ProductForm form);

    List<ProductDto> getProducts();

    ApiResponse deleteProduct(UUID id);

    ProductDto editProduct(UUID id, ProductForm form);
}
