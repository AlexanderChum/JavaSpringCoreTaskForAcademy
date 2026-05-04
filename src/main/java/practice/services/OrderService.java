package practice.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.model.dto.OrderRequest;
import practice.model.dto.OrderResponse;

import java.util.UUID;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    Page<OrderResponse> getOrdersByUserId(UUID userId, Pageable pageable);

    OrderResponse getOrderById(UUID orderId);

    OrderResponse updateOrder(UUID orderId, OrderRequest request);

    void deleteOrder(UUID orderId);
}
