package ecom_application.repository;

import ecom_application.entity.CartItem;
import ecom_application.entity.Product;
import ecom_application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);
}
