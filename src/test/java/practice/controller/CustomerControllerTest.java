package practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import practice.model.dto.CustomerRequest;
import practice.model.dto.CustomerResponse;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CustomerController.UserService service;

    final CustomerResponse internal = CustomerResponse.builder()
            .customerId(1)
            .firstName("Иван")
            .lastName("Иванов")
            .email("ivan@itk.com")
            .contactNumber("88005553535")
            .build();
    final CustomerResponse publicView = CustomerResponse.builder()
            .firstName("Иван")
            .lastName("Иванов")
            .email("ivan@itk.com")
            .build();

    @Test
    void getAllUsers() throws Exception {
        when(service.getAllUsers()).thenReturn(List.of(publicView));
        mvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(jsonPath("$[0].orders").doesNotExist())
                .andExpect(jsonPath("$[0].firstName").value("Иван"));
    }

    @Test
    void getUser() throws Exception {
        when(service.getUser(1)).thenReturn(internal);
        mvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").exists())
                .andExpect(jsonPath("$.contactNumber").exists());
    }

    @Test
    void createUser() throws Exception {
        when(service.createUser(any())).thenReturn(internal);
        mvc.perform(post("/createCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new CustomerRequest("Иван", "Иванов",
                                "ivan@itk.com", "88005553535"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    void updateUser() throws Exception {
        when(service.updateUser(any(), any())).thenReturn(internal);
        mvc.perform(put("/updateCustomer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new CustomerRequest("Иван", "Иванов",
                                "ivan@itk.com", "88005553535"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactNumber").exists());
    }

    @Test
    void createUserInvalidEmailShouldReturnBadRequest() throws Exception {
        CustomerRequest invalid = CustomerRequest.builder()
                .firstName("Test")
                .lastName("Test2")
                .email("bad")
                .build();
        mvc.perform(post("/createCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}