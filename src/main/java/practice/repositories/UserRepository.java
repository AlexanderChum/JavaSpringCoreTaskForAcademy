package practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
