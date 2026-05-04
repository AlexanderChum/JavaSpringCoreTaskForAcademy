package practice.mapper;

import org.springframework.stereotype.Component;
import practice.model.Order;
import practice.model.OrderStatus;
import practice.model.dto.OrderRequest;
import practice.model.dto.OrderResponse;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequest request) {
        return Order.builder()
                .userId(request.getUserId())
                .products(request.getProducts())
                .orderSum(request.getOrderSum())
                .orderStatus(OrderStatus.NEW)
                .build();
    }

    public void updateEntity(Order existing, OrderRequest request) {
        existing.setProducts(request.getProducts());
        existing.setOrderSum(request.getOrderSum());
        existing.setUserId(request.getUserId());
        if (request.getOrderStatus() != null) {
            existing.setOrderStatus(request.getOrderStatus());
        }
    }

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .products(order.getProducts())
                .orderSum(order.getOrderSum())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}