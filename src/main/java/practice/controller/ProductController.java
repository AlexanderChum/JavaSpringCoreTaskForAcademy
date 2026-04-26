package practice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import practice.model.dto.ProductRequest;
import practice.model.dto.ProductResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductController {
    ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("Получен запрос на получение всех продуктов");
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(name = "productId")
                                                          @NotNull(message = "id должен быть указан")
                                                          @Positive(message = "id продукта не может быть меньше 1")
                                                          Integer productId) {
        log.info("Получен запрос на получение продукта по id");
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getProductById(productId));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        log.info("Получен запрос на создание продукта");
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createProduct(request));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable(name = "productId")
                                                         @NotNull(message = "id должен быть указан")
                                                         @Positive(message = "id продукта не может быть меньше 1")
                                                         Integer productId,

                                                         @RequestBody @Valid ProductRequest request) {
        log.info("Получен запрос на обновление продукта");
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateProduct(productId, request));
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(name = "productId")
                              @NotNull(message = "id должен быть указан")
                              @Positive(message = "id продукта не может быть меньше 1")
                              Integer productId) {
        log.info("Получен запрос на удаление продукта");
        service.doServiceJob();
    }

    //Все та же заглушка сервиса
    @Service
    static class ProductService {
        void doServiceJob() {
            System.out.println("ProductService выполнил свою работу");
        }

        List<ProductResponse> getAllProducts() {
            return new ArrayList<>();
        }

        ProductResponse getProductById(Integer id) {
            return new ProductResponse();
        }

        ProductResponse createProduct(ProductRequest request) {
            return new ProductResponse();
        }

        ProductResponse updateProduct(Integer id, ProductRequest request) {
            return new ProductResponse();
        }
    }
}
