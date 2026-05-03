package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerId(UUID customerId);
}
