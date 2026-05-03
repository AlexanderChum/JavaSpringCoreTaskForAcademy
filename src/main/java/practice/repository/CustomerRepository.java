package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.model.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
