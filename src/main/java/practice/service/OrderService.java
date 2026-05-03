package practice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.model.dto.OrderRequest;
import practice.model.dto.OrderResponse;

import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    Page<OrderResponse> getOrdersByUserId(UUID customerId, Pageable pageable);

    OrderResponse getOrderById(UUID orderId);

    void deleteByUUID(UUID orderId);

    OrderResponse updateOrder(UUID id, OrderRequest request);
}
