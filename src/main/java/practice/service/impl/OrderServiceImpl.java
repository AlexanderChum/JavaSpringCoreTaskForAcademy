package practice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mapper.OrderMapper;
import practice.model.Customer;
import practice.model.Order;
import practice.model.OrderStatus;
import practice.model.Product;
import practice.model.dto.OrderRequest;
import practice.model.dto.OrderResponse;
import practice.model.exceptions.NotFoundException;
import practice.repository.CustomerRepository;
import practice.repository.OrderRepository;
import practice.repository.ProductRepository;
import practice.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    CustomerRepository customerRepository;
    ProductRepository productRepository;
    OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        List<Product> products = productRepository.findAllById(request.getProductIds());
        if (products.isEmpty()) {
            throw new NotFoundException("Продукты в заказе не найдены");
        }

        double total = products.stream().mapToDouble(Product::getPrice).sum();

        Order order = orderMapper.toEntity(request);
        order.setCustomer(customer);
        order.setProducts(products);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(total);
        order.setOrderStatus(OrderStatus.NEW);

        Order saved = orderRepository.save(order);
        log.info("Заказ создан в системе");
        return orderMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getOrdersByUserId(UUID customerId, Pageable pageable) {
        return orderRepository.findByCustomerId(customerId, pageable)
                .map(orderMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(UUID orderId) {
        Order order = checkIfExists(orderId);
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public void deleteByUUID(UUID orderId) {
        Order order = checkIfExists(orderId);
        orderRepository.delete(order);
        log.info("Заказ удален");
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(UUID id, OrderRequest request) {
        Order order = checkIfExists(id);
        List<Product> products = productRepository.findAllById(request.getProductIds());
        if (products.isEmpty()) {
            throw new NotFoundException("Продукты в заказе не найдены");
        }
        double newTotal = products.stream().mapToDouble(Product::getPrice).sum();

        order.setProducts(products);
        order.setShippingAddress(request.getShippingAddress());
        order.setTotalPrice(newTotal);

        Order updated = orderRepository.save(order);
        log.info("Заказ обновлен");
        return orderMapper.toResponse(updated);
    }

    private Order checkIfExists(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заказ не найден"));
    }
}
