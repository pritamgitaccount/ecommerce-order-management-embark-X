package ecom_application.dto;

import lombok.Data;

/**
 * Request DTO identifying a product and quantity to add to a cart.
 */
@Data
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
}
