package ecom_application.service;

import ecom_application.dto.CartItemRequest;
import ecom_application.entity.CartItem;
import ecom_application.entity.Product;
import ecom_application.entity.User;
import ecom_application.repository.CartRepository;
import ecom_application.repository.ProductRepository;
import ecom_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if (productOpt.isEmpty()) {
            return false;
        }

        Product product = productOpt.get();
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            return false;
        }

        if (product.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) return false;
        User user = userOpt.get();

        CartItem existingCartItem = cartRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            int newQuantity = existingCartItem.getQuantity() + request.getQuantity();
            if (product.getStockQuantity() < newQuantity) {
                return false;
            }

            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setPrice(product.getPrice().multiply(valueOf(newQuantity)));
            cartRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setUser(user);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(request.getQuantity());
            newCartItem.setPrice(product.getPrice().multiply(valueOf(request.getQuantity())));
            cartRepository.save(newCartItem);
        }
        return true;
    }

    public boolean removeItemFromCart(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (productOpt.isPresent() && userOpt.isPresent()) {
            cartRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCartItemDetails(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(cartRepository::findByUser)
                .orElseGet(List::of);

    }
}
