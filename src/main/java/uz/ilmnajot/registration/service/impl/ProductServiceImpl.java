package uz.ilmnajot.registration.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import uz.ilmnajot.registration.apiResponse.ApiResponse;
import uz.ilmnajot.registration.dto.ProductDto;
import uz.ilmnajot.registration.dto.ProductForm;
import uz.ilmnajot.registration.entity.Product;
import uz.ilmnajot.registration.exception.AppException;
import uz.ilmnajot.registration.repository.ProductRepository;
import uz.ilmnajot.registration.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto getProduct(UUID id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()) {
            Product product = byId.get();
            return ProductDto.toDto(product);
        }
        throw new AppException("Product not found");
    }

    @Transactional
    @Override
    public ProductDto registerProduct(ProductForm form) {
        boolean existsByNameAndExistsTrue = productRepository.existsByNameAndExistsTrue(form.getName());
        if (existsByNameAndExistsTrue) {
            throw new AppException("Product already exists with name " + form.getName());
        }
        Product product = new Product();
        product.setName(form.getName());
        product.setDescription(form.getDescription());
        product.setQuantity(form.getQuantity());
        product.setColor(form.getColor());
        product.setPrice(form.getPrice());
        product.setImages(form.getImages());
        Product saved = productRepository.save(product);
        return ProductDto.toDto(saved);
    }

    @Override
    public List<ProductDto> getProducts() {
        try {

            List<Product> allByExistsTrue = productRepository.findAllByExistsTrue();
            List<ProductDto> productDtoList = new ArrayList<>();
            for (Product product : allByExistsTrue) {
                ProductDto dto = ProductDto.toDto(product);
                productDtoList.add(dto);
            }
            return productDtoList;
        } catch (Exception ignored) {
            throw new AppException("there is no list of products");
        }
    }

    @Override
    public ApiResponse deleteProduct(UUID id) {
        Product product = productRepository.findByIdAndExistsTrue(id);
        if (product!=null) {
            product.setExists(false);
            productRepository.save(product);
            return new ApiResponse("successfully product deleted", true);
        }
        return new ApiResponse("failed to delete product", false);
    }

    @Override
    public ProductDto editProduct(UUID id, ProductForm form) {
        Product product = productRepository.findByIdAndExistsTrue(id);
        if (product==null){
            throw new AppException("Product not found to edit");
        }
        product.setId(id);
        product.setName(form.getName());
        product.setDescription(form.getDescription());
        product.setQuantity(form.getQuantity());
        product.setColor(form.getColor());
        product.setPrice(form.getPrice());
        product.setImages(form.getImages());
        Product saved = productRepository.save(product);
        return ProductDto.toDto(saved);
    }
    
}
