package practice.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mapper.OrderMapper;
import practice.model.Order;
import practice.model.User;
import practice.model.dto.OrderRequest;
import practice.model.dto.OrderResponse;
import practice.model.exceptions.NotFoundException;
import practice.repositories.OrderRepository;
import practice.repositories.UserRepository;
import practice.services.OrderService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        checkUserIfExists(request.getUserId());
        Order order = orderMapper.toEntity(request);
        Order saved = orderRepository.save(order);
        log.info("Заказ сохранен");
        return orderMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getOrdersByUserId(UUID userId, Pageable pageable) {
        checkUserIfExists(userId);
        log.info("Получаем заказы пользователя");
        return orderRepository.findByUserId(userId, pageable)
                .map(orderMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(UUID orderId) {
        Order order = checkIfExists(orderId);
        log.info("Получаем заказ по id");
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse updateOrder(UUID orderId, OrderRequest request) {
        Order order = checkIfExists(orderId);
        checkUserIfExists(request.getUserId());
        orderMapper.updateEntity(order, request);
        Order updated = orderRepository.save(order);
        log.info("Обновили заказ");
        return orderMapper.toResponse(updated);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        checkIfExists(orderId);
        orderRepository.deleteById(orderId);
    }

    private Order checkIfExists(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Такого заказа не найдено"));
    }

    private User checkUserIfExists(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }
}
