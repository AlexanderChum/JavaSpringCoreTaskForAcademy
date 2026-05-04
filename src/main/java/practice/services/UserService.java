package practice.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.model.dto.UserRequest;
import practice.model.dto.UserResponse;

import java.util.UUID;

public interface UserService {

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUser(UUID id);

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(UUID id, UserRequest request);

    void deleteUser(UUID id);
}
