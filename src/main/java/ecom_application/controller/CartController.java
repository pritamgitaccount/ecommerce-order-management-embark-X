package ecom_application.controller;

import ecom_application.dto.CartItemRequest;
import ecom_application.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
