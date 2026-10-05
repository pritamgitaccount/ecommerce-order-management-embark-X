package ecom_application.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Response DTO containing product details exposed by the API.
 */
@Data
@JsonPropertyOrder({"id", "name", "description", "price", "stockQuantity", "category", "imageUrl"})
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
}
