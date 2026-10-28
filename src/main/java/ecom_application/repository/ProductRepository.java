package ecom_application.repository;

import ecom_application.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides database operations for {@link Product} records.
 * <p>
 * In addition to the product-specific queries below, this repository inherits
 * common create, read, update, and delete operations from {@link JpaRepository}.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Finds every product that is currently marked as active.
     *
     * @return the active products, or an empty list if none are found
     */
    List<Product> findByActiveTrue();

    /**
     * Searches for active, in-stock products whose names contain the keyword,
     * ignoring differences between uppercase and lowercase letters.
     *
     * @param keyword text to look for within each product name
     * @return matching products, or an empty list if none are found
     */
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.stockQuantity > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);

}
