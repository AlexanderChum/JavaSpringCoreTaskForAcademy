package practice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.model.dto.ProductRequest;
import practice.model.dto.ProductResponse;

import java.util.UUID;

public interface ProductService {
    Page<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse getProductById(UUID id);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(UUID id, ProductRequest request);

    void deleteByUUID(UUID id);
}
