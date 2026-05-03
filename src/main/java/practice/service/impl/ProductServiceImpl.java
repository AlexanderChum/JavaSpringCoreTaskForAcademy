package practice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mapper.ProductMapper;
import practice.model.Product;
import practice.model.dto.ProductRequest;
import practice.model.dto.ProductResponse;
import practice.model.exceptions.NotFoundException;
import practice.repository.ProductRepository;
import practice.service.ProductService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        log.info("Получен запрос на получение всех продуктов");
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id) {
        Product product = checkIfExists(id);
        log.info("Получен продукт из бд");
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);
        Product saved = productRepository.save(product);
        log.info("Добавлен новый продукт");
        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest request) {
        Product product = checkIfExists(id);
        productMapper.updateEntity(request, product);
        Product updated = productRepository.save(product);
        log.info("Продукт обновлен");
        return productMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteByUUID(UUID id) {
        Product product = checkIfExists(id);
        productRepository.delete(product);
        log.info("Продукт удален");
    }

    private Product checkIfExists(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Продукт не найден"));
    }
}
