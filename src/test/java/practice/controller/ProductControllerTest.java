package practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import practice.model.dto.ProductRequest;
import practice.model.dto.ProductResponse;
import practice.service.ProductService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ProductControllerTest {

    final UUID PRODUCT_ID = UUID.fromString("123e4567-e89b-12d3-a456-123456789012");

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ProductService service;

    final ProductResponse sample = ProductResponse.builder()
            .productId(PRODUCT_ID)
            .name("Мышка")
            .description("Тест")
            .price(29999.99)
            .quantityInStock(50)
            .build();

    @Test
    void getProductById() throws Exception {
        when(service.getProductById(PRODUCT_ID)).thenReturn(sample);

        mvc.perform(get("/products/{productId}", PRODUCT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID.toString()))
                .andExpect(jsonPath("$.name").value("Мышка"))
                .andExpect(jsonPath("$.description").exists());
    }

    @Test
    void createProduct() throws Exception {
        when(service.createProduct(any())).thenReturn(sample);

        ProductRequest request = ProductRequest.builder()
                .name("Мышка")
                .description("Тест")
                .price(29999.99)
                .quantityInStock(50)
                .build();

        mvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(PRODUCT_ID.toString()))
                .andExpect(jsonPath("$.name").value("Мышка"));
    }

    @Test
    void updateProduct() throws Exception {
        when(service.updateProduct(any(), any())).thenReturn(sample);

        ProductRequest request = ProductRequest.builder()
                .name("Мышка")
                .description("Тест2")
                .price(39999.99)
                .quantityInStock(30)
                .build();

        mvc.perform(put("/products/{productId}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Мышка"))
                .andExpect(jsonPath("$.price").value(29999.99));
    }

    @Test
    void deleteProduct() throws Exception {
        doNothing().when(service).deleteByUUID(PRODUCT_ID);

        mvc.perform(delete("/products/{productId}", PRODUCT_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void createProductWithInvalidPriceShouldReturnBadRequest() throws Exception {
        ProductRequest invalid = ProductRequest.builder()
                .name("Мышка")
                .price(-100.0)
                .quantityInStock(10)
                .build();

        mvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProductWithNullNameShouldReturnBadRequest() throws Exception {
        ProductRequest invalid = ProductRequest.builder()
                .name(null)
                .price(100.0)
                .quantityInStock(10)
                .build();

        mvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProductWithNullQuantityShouldReturnBadRequest() throws Exception {
        ProductRequest invalid = ProductRequest.builder()
                .name("Мышка")
                .price(100.0)
                .quantityInStock(null)
                .build();

        mvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}
