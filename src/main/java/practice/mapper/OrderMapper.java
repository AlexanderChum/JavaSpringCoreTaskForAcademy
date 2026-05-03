package practice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import practice.model.Order;
import practice.model.dto.OrderRequest;
import practice.model.dto.OrderResponse;
import practice.model.dto.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ProductMapper productMapper;

    public OrderResponse toResponse(Order order) {
        List<ProductResponse> productResponses = order.getProducts().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .customerId(order.getCustomer().getCustomerId())
                .products(productResponses)
                .orderDate(order.getOrderDate())
                .address(order.getShippingAddress())
                .orderSum(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public Order toEntity(OrderRequest request) {
        return Order.builder()
                .shippingAddress(request.getShippingAddress())
                .build();
    }
}
