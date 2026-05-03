package practice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import practice.model.dto.ProductRequest;
import practice.model.dto.ProductResponse;
import practice.service.ProductService;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductController {
    ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable) {
        log.info("Получен запрос на получение всех продуктов");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllProducts(pageable));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(name = "productId")
                                                          @NotNull(message = "id должен быть указан")
                                                          UUID productId) {
        log.info("Получен запрос на получение продукта по id");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getProductById(productId));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        log.info("Получен запрос на создание продукта");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createProduct(request));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable(name = "productId")
                                                         @NotNull(message = "id должен быть указан")
                                                         UUID productId,

                                                         @RequestBody @Valid ProductRequest request) {
        log.info("Получен запрос на обновление продукта");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateProduct(productId, request));
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(name = "productId")
                              @NotNull(message = "id должен быть указан")
                              UUID productId) {
        log.info("Получен запрос на удаление продукта");
        service.deleteByUUID(productId);
    }
}
