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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import practice.model.dto.InfoScopes;
import practice.model.dto.CustomerRequest;
import practice.model.dto.CustomerResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    UserService service;

    @GetMapping("/customers")
    @JsonView(InfoScopes.Public.class)
    public ResponseEntity<List<CustomerResponse>> getAllUsers() {
        log.info("Получен запрос в контроллер на получение всех пользователей");
        service.doServiceJob();   //Здесь и далее действует как заглушка для имитации действий сервиса
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllUsers());
    }

    @GetMapping("/customer/{UUID}")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<CustomerResponse> getUser(@PathVariable(name = "UUID")
                                                    @NotNull(message = "id должен быть указан")
                                                    @Positive(message = "id пользователя не может быть меньше 1")
                                                    Integer id) { //Integer как заглушка UUID
        log.info("Получен запрос на получение пользователя");
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getUser(id));
    }

    @PostMapping("/createCustomer")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<CustomerResponse> createUser(@RequestBody @Valid CustomerRequest request) {
        log.info("Получен запрос на создание пользователя");
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createUser(request));
    }

    @DeleteMapping("/customer/{UUID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "UUID")
                           @NotNull(message = "id должен быть указан")
                           @Positive(message = "id пользователя не может быть меньше 1")
                           Integer id) {
        log.info("Получен запрос на удаление пользователя");
        service.doServiceJob();
    }

    @PutMapping("/updateCustomer/{UUID}")
    @JsonView(InfoScopes.Internal.class)
    public ResponseEntity<CustomerResponse> updateUser(@PathVariable(name = "UUID")
                                                       @NotNull(message = "id должен быть указан")
                                                       @Positive(message = "id пользователя не может быть меньше 1")
                                                       Integer id,

                                                       @RequestBody @Valid CustomerRequest request) {
        log.info("Получен запрос на обновление пользователя");
        service.doServiceJob();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateUser(id, request));
    }


    //Класс заглушка для имитации деятельности сервиса и возврата корректных типов
    @Service
    static class UserService {

        void doServiceJob() {
            System.out.println("Сервис выполнил свою работу");
        }

        List<CustomerResponse> getAllUsers() {
            return new ArrayList<>();
        }

        CustomerResponse getUser(Integer id) {
            return new CustomerResponse();
        }

        CustomerResponse createUser(CustomerRequest request) {
            //создали пользователя, вернули ответ
            return new CustomerResponse();
        }

        CustomerResponse updateUser(Integer id, CustomerRequest request) {
            //получили пользователя из бд по id, поменяли ему поля, вернули
            return new CustomerResponse();
        }
    }
}
