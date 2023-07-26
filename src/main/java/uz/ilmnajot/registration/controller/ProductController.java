package uz.ilmnajot.registration.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.registration.apiResponse.ApiResponse;
import uz.ilmnajot.registration.dto.ProductDto;
import uz.ilmnajot.registration.dto.ProductForm;
import uz.ilmnajot.registration.service.ProductService;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<ProductDto> getProduct(@PathVariable UUID id) {
        ProductDto productDto = productService.getProduct(id);
        return ResponseEntity.ok(productDto);
    }

    @PreAuthorize("hasanyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping("/registerProduct")
    public HttpEntity<ProductDto> registerProduct(@RequestBody ProductForm form) {
        ProductDto productDto = productService.registerProduct(form);
        return ResponseEntity.ok(productDto);
    }

    @PreAuthorize(("hasAnyAuthority('ROLE_ADMIN, ROLE_USER_ROLE_MANAGER')"))
    @GetMapping("/allProducts")
    public HttpEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN, ROLE_MANAGER')")
    public HttpEntity<ApiResponse> deleteProduct(@PathVariable UUID id) {
        ApiResponse apiResponse = productService.deleteProduct(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<ProductDto> updateProduct(@PathVariable UUID id, @RequestBody ProductForm form) {
        ProductDto productDto = productService.editProduct(id, form);
        return ResponseEntity.ok(productDto);
    }

}
