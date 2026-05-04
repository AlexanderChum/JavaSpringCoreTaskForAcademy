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
import practice.services.OrderService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    OrderService orderService;

    final UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-123456789012");
    final UUID orderId = UUID.fromString("123e4567-e89b-12d3-a456-123456789013");

    final OrderResponse sample = OrderResponse.builder()
            .id(orderId)
            .userId(userId)
            .products(List.of("Product A", "Product B"))
            .orderSum(1500)
            .orderStatus(OrderStatus.NEW)
            .build();

    @Test
    void createOrder() throws Exception {
        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(sample);

        OrderRequest request = OrderRequest.builder()
                .userId(userId)
                .products(List.of("Product X"))
                .orderSum(100)
                .orderStatus(OrderStatus.NEW)
                .build();

        mvc.perform(post("/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products[0]").value("Product A"));
    }

    @Test
    void getOrderById() throws Exception {
        when(orderService.getOrderById(orderId)).thenReturn(sample);

        mvc.perform(get("/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.orderStatus").value("NEW"));
    }

    @Test
    void deleteOrder() throws Exception {
        mvc.perform(delete("/{orderId}", orderId))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateOrder() throws Exception {
        when(orderService.updateOrder(any(UUID.class), any(OrderRequest.class))).thenReturn(sample);

        OrderRequest updateRequest = OrderRequest.builder()
                .userId(userId)
                .products(List.of("Updated Product"))
                .orderSum(2000)
                .orderStatus(OrderStatus.INPROGRESS)
                .build();

        mvc.perform(post("/updateOrder/{UUID}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(jsonPath("$.orderStatus").value("NEW"));
    }

    @Test
    void createOrderInvalidUserIdShouldReturnBadRequest() throws Exception {
        OrderRequest invalid = OrderRequest.builder()
                .userId(null)
                .products(List.of("X"))
                .orderSum(100)
                .build();

        mvc.perform(post("/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}