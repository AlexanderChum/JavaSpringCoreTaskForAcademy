package practice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import practice.model.dto.InfoScopes;
import practice.model.dto.OrderRequest;
import practice.model.dto.OrderResponse;

import java.util.ArrayList;
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
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createOrder(request));
    }

    @GetMapping("/user/{userId}")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable(name = "userId")
                                                                 @NotNull(message = "id должен быть указан")
                                                                 @Positive(message = "id пользователя должен быть > 0")
                                                                 Integer userId) {
        log.info("Получен запрос на получение заказов пользователя");
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getOrdersByUserId(userId));
    }

    @GetMapping("/{orderId}")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable(name = "orderId")
                                                      @NotNull(message = "id должен быть указан")
                                                      @Positive(message = "id пользователя не может быть меньше 1")
                                                      Integer orderId) {
        log.info("Получен запрос на получение заказа по id");
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getOrderById(orderId));
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable(name = "orderId")
                            @NotNull(message = "id должен быть указан")
                            @Positive(message = "id пользователя не может быть меньше 1")
                            Integer orderId) {
        log.info("Получен запрос на удаление заказа");
        service.doServiceJob();
    }

    @PostMapping("/updateOrder/{UUID}")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable(name = "UUID")
                                                     @NotNull(message = "id должен быть указан")
                                                     @Positive(message = "id пользователя не может быть меньше 1")
                                                     Integer id,

                                                     @RequestBody @Valid OrderRequest request) {
        log.info("Получен запрос на обновление заказа");
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateOrder(id, request));
    }


    //Сервис заглушка
    @Service
    static class OrderService {
        void doServiceJob() {
            System.out.println("Сервис выполнил свою работу");
        }

        OrderResponse createOrder(OrderRequest request) {
            return new OrderResponse();
        }

        List<OrderResponse> getOrdersByUserId(Integer userId) {
            return new ArrayList<>();
        }

        OrderResponse getOrderById(Integer orderId) {
            return new OrderResponse();
        }

        OrderResponse updateOrder(Integer id, OrderRequest request) {
            return new OrderResponse();
        }
    }
}
