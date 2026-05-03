package practice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
import practice.model.dto.OrderRequest;
import practice.model.dto.OrderResponse;
import practice.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {
    OrderService service;

    @PostMapping("/createOrder")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
        log.info("Получен запрос на создание заказа");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createOrder(request));
    }

    @GetMapping("/user/{customerId}")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable(name = "customerId")
                                                                 @NotNull(message = "id должен быть указан")
                                                                 UUID customerId) {
        log.info("Получен запрос на получение заказов пользователя");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getOrdersByUserId(customerId));
    }

    @GetMapping("/{orderId}")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable(name = "orderId")
                                                      @NotNull(message = "id должен быть указан")
                                                      UUID orderId) {
        log.info("Получен запрос на получение заказа по id");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getOrderById(orderId));
    }

    @DeleteMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable(name = "orderId")
                            @NotNull(message = "id должен быть указан")
                            UUID orderId) {
        log.info("Получен запрос на удаление заказа");
        service.deleteByUUID(orderId);
    }

    @PutMapping("/updateOrder/{UUID}")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable(name = "UUID")
                                                     @NotNull(message = "id должен быть указан")
                                                     UUID id,

                                                     @RequestBody @Valid OrderRequest request) {
        log.info("Получен запрос на обновление заказа");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateOrder(id, request));
    }
}
