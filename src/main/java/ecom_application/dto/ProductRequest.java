package ecom_application.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Request DTO containing the product details submitted to the API.
 */
@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
}
