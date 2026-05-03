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
import practice.service.CustomerService;

import java.util.UUID;

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

    final UUID CUSTOMER_ID = UUID.fromString("123e4567-e89b-12d3-a456-123456789012");

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CustomerService service;

    final CustomerResponse internal = CustomerResponse.builder()
            .customerId(CUSTOMER_ID)
            .firstName("Иван")
            .lastName("Иванов")
            .email("ivan@itk.com")
            .contactNumber("88005553535")
            .build();

    @Test
    void getUser() throws Exception {
        when(service.getUser(CUSTOMER_ID)).thenReturn(internal);
        mvc.perform(get("/customer/{id}", CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(CUSTOMER_ID.toString()))
                .andExpect(jsonPath("$.contactNumber").value("88005553535"));
    }

    @Test
    void createUser() throws Exception {
        when(service.createUser(any())).thenReturn(internal);
        CustomerRequest request = CustomerRequest.builder()
                .firstName("Иван")
                .lastName("Иванов")
                .email("ivan@itk.com")
                .contactNumber("88005553535")
                .build();

        mvc.perform(post("/createCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(CUSTOMER_ID.toString()));
    }

    @Test
    void updateUser() throws Exception {
        when(service.updateUser(any(), any())).thenReturn(internal);
        CustomerRequest request = CustomerRequest.builder()
                .firstName("Иван")
                .lastName("Иванов")
                .email("ivan@itk.com")
                .contactNumber("88005553535")
                .build();

        mvc.perform(put("/updateCustomer/{id}", CUSTOMER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactNumber").value("88005553535"));
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