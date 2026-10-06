package ecom_application.controller;

import ecom_application.dto.CartItemRequest;
import ecom_application.entity.CartItem;
import ecom_application.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    //URL : http://localhost:8080/api/cart/add
    @PostMapping("/add")
    public ResponseEntity<String> add_to_cart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request) {
        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest().body(
                    "Product not found, invalid quantity, or insufficient stock."
            );
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added to cart successfully.");
    }

    //URL : http://localhost:8080/api/cart/remove/{productId}
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId) {
        boolean deleted = cartService.removeItemFromCart(userId, productId);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    //URL : http://localhost:8080/api/cart/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCartItem(
            @RequestHeader("X-User-ID") String userId) {
//        if (userId == null) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
        List<CartItem> cartItemDetails = cartService.getCartItemDetails(userId);
        return ResponseEntity.ok(cartItemDetails);
    }
}
