package practice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import practice.model.dto.InfoScopes;
import practice.model.dto.UserRequest;
import practice.model.dto.UserResponse;
import practice.services.UserService;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService service;

    @GetMapping("/users")
    @JsonView(InfoScopes.Public.class)
    public ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {
        log.info("Получен запрос в контроллер на получение всех пользователей");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllUsers(pageable));
    }

    @GetMapping("/user/{UUID}")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<UserResponse> getUser(@PathVariable(name = "UUID")
                                                @NotNull(message = "id должен быть указан")
                                                UUID id) {
        log.info("Получен запрос на получение пользователя");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getUser(id));
    }

    @PostMapping("/createUser")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        log.info("Получен запрос на создание пользователя");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createUser(request));
    }

    @DeleteMapping("/{UUID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "UUID")
                           @NotNull(message = "id должен быть указан")
                           UUID id) {
        log.info("Получен запрос на удаление пользователя");
        service.deleteUser(id);
    }

    @PutMapping("/updateUser/{UUID}")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<UserResponse> updateUser(@PathVariable(name = "UUID")
                                                   @NotNull(message = "id должен быть указан")
                                                   UUID id,

                                                   @RequestBody @Valid UserRequest request) {
        log.info("Получен запрос на обновление пользователя");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateUser(id, request));
    }
}
