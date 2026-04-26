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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    OrderController.OrderService service;

    final OrderResponse sample = OrderResponse.builder()
            .orderId(100)
            .customerId(1)
            .products(List.of(new ProductResponse(), new ProductResponse()))
            .orderSum(1500)
            .orderStatus(OrderStatus.NEW)
            .build();

    @Test
    void createOrder() throws Exception {
        when(service.createOrder(any())).thenReturn(sample);
        mvc.perform(post("/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(
                                new OrderRequest(1, List.of(1, 2, 3), "улица Пушкина"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(100))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.products").isArray());
    }

    @Test
    void getOrderById() throws Exception {
        when(service.getOrderById(100)).thenReturn(sample);
        mvc.perform(get("/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").exists())
                .andExpect(jsonPath("$.customerId").exists())
                .andExpect(jsonPath("$.orderStatus").value("NEW"));
    }

    @Test
    void getOrdersByUserId() throws Exception {
        when(service.getOrdersByUserId(1)).thenReturn(List.of(sample));
        mvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").exists())
                .andExpect(jsonPath("$[0].orderSum").exists());
    }

    @Test
    void createOrderInvalidUserIdShouldReturnBadRequest() throws Exception {
        OrderRequest invalid = OrderRequest.builder()
                .customerId(-1)
                .productIds(List.of(1 , 2 , 3))
                .shippingAddress("улица Пушкина")
                .build();
        mvc.perform(post("/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}
