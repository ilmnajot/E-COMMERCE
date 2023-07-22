package uz.ilmnajot.registration.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.registration.entity.Product;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByIdAndExistsTrue(UUID id);

    boolean existsByNameAndExistsTrue(String name);

    List<Product> findAllByExistsTrue();
}
