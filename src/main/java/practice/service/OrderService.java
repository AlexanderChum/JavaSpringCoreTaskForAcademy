package practice.service;

import practice.model.dto.OrderRequest;
import practice.model.dto.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getOrdersByUserId(UUID customerId);

    OrderResponse getOrderById(UUID orderId);

    void deleteByUUID(UUID orderId);

    OrderResponse updateOrder(UUID id, OrderRequest request);
}
