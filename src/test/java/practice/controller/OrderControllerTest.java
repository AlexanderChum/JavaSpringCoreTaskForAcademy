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
import practice.model.OrderStatus;
import practice.model.dto.OrderRequest;
import practice.model.dto.OrderResponse;
import practice.model.dto.ProductResponse;
import practice.service.OrderService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class OrderControllerTest {

    final UUID ORDER_ID = UUID.fromString("123e4567-e89b-12d3-a456-123456789012");
    final UUID CUSTOMER_ID = UUID.fromString("123e4567-e89b-12d3-a456-123456789013");
    final UUID PRODUCT_ID_1 = UUID.fromString("123e4567-e89b-12d3-a456-123456789014");
    final UUID PRODUCT_ID_2 = UUID.fromString("123e4567-e89b-12d3-a456-123456789015");

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    OrderService service;

    final OrderResponse example = OrderResponse.builder()
            .orderId(ORDER_ID)
            .customerId(CUSTOMER_ID)
            .products(List.of(
                    ProductResponse.builder()
                            .productId(PRODUCT_ID_1)
                            .name("Product 1")
                            .price(100.0)
                            .build(),
                    ProductResponse.builder()
                            .productId(PRODUCT_ID_2)
                            .name("Product 2")
                            .price(200.0)
                            .build()
            ))
            .orderSum(1500.0)
            .orderStatus(OrderStatus.NEW)
            .build();

    @Test
    void createOrder() throws Exception {
        when(service.createOrder(any())).thenReturn(example);

        OrderRequest request = OrderRequest.builder()
                .customerId(CUSTOMER_ID)
                .productIds(List.of(PRODUCT_ID_1, PRODUCT_ID_2))
                .shippingAddress("улица Пушкина")
                .build();

        mvc.perform(post("/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(ORDER_ID.toString()))
                .andExpect(jsonPath("$.customerId").value(CUSTOMER_ID.toString()))
                .andExpect(jsonPath("$.products").isArray());
    }

    @Test
    void getOrderById() throws Exception {
        when(service.getOrderById(ORDER_ID)).thenReturn(example);

        mvc.perform(get("/{orderId}", ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").exists())
                .andExpect(jsonPath("$.customerId").exists())
                .andExpect(jsonPath("$.orderStatus").value("NEW"));
    }

    @Test
    void getOrdersByUserId() throws Exception {
        when(service.getOrdersByUserId(CUSTOMER_ID)).thenReturn(List.of(example));

        mvc.perform(get("/user/{customerId}", CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").exists())
                .andExpect(jsonPath("$[0].orderSum").exists());
    }

    @Test
    void createOrderInvalidUserIdShouldReturnBadRequest() throws Exception {
        OrderRequest invalid = OrderRequest.builder()
                .customerId(null)
                .productIds(List.of(PRODUCT_ID_1, PRODUCT_ID_2))
                .shippingAddress("улица Пушкина")
                .build();

        mvc.perform(post("/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}