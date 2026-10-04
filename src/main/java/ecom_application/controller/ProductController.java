package ecom_application.controller;

import ecom_application.dto.ProductRequest;
import ecom_application.dto.ProductResponse;
import ecom_application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    // URL : http://localhost:8080/api/v1/products/create
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody ProductRequest
                    productRequest) {
        ProductResponse productResponse = productService.createProduct(productRequest);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    //create update product endpoint
    // URL : http://localhost:8080/api/v1/products/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(
            @RequestBody ProductRequest productRequest,
            @PathVariable Long id) {
        return productService.updateProduct(id, productRequest)
                .map(productResponse -> ResponseEntity.ok(Map.of(
                                        "msg", "Product updated",
                                        "product", productResponse
                                )
                        )
                )
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // URL : http://localhost:8080/api/v1/products/all
    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // URL : http://localhost:8080/api/v1/products/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        boolean deletedProduct = productService.deleteProduct(id);
        return deletedProduct
                ? ResponseEntity.ok(Map.of("msg", "Product deleted successfully"))
                : ResponseEntity.notFound().build();
    }

    // URL : http://localhost:8080/api/v1/products/search?keyword=example
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }
}