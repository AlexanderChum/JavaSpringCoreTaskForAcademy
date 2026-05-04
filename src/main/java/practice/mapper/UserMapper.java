package practice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import practice.model.User;
import practice.model.dto.UserRequest;
import practice.model.dto.UserResponse;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final OrderMapper orderMapper;

    public User toEntity(UserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .orders(user.getOrders().stream()
                        .map(orderMapper::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public void updateEntity(User existing, UserRequest request) {
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
    }
}
