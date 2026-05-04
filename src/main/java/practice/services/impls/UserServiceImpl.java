package practice.services.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mapper.UserMapper;
import practice.model.User;
import practice.model.dto.UserRequest;
import practice.model.dto.UserResponse;
import practice.model.exceptions.NotFoundException;
import practice.repositories.UserRepository;
import practice.services.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("Получен запрос на возврат всех пользователей");
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(UUID id) {
        User user = checkIfExists(id);
        log.info("Пользователь получен из бд");
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        log.info("Пользователь сохранен");
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse updateUser(UUID id, UserRequest request) {
        User user = checkIfExists(id);
        userMapper.updateEntity(user, request);
        User updated = userRepository.save(user);
        log.info("Пользователь обновлен");
        return userMapper.toResponse(updated);
    }

    @Override
    public void deleteUser(UUID id) {
        checkIfExists(id);
        userRepository.deleteById(id);
        log.info("Пользователь удален");
    }

    private User checkIfExists(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Такой пользователь не найден"));
    }
}
