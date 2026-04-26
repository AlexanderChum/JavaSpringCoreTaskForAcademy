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
import practice.model.dto.UserRequest;
import practice.model.dto.UserResponse;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserController.UserService service;

    final UserResponse internal = UserResponse.builder()
            .id(1).name("Иван")
            .email("ivan@itk.com")
            .orders(List.of())
            .build();
    final UserResponse publicView = UserResponse.builder()
            .name("Иван")
            .email("ivan@itk.com").build();

    @Test
    void getAllUsers() throws Exception {
        when(service.getAllUsers()).thenReturn(List.of(publicView));
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(jsonPath("$[0].orders").doesNotExist())
                .andExpect(jsonPath("$[0].name").value("Иван"));
    }

    @Test
    void getUser() throws Exception {
        when(service.getUser(1)).thenReturn(internal);
        mvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.orders").exists());
    }

    @Test
    void createUser() throws Exception {
        when(service.createUser(any())).thenReturn(internal);
        mvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new UserRequest("Иван", "ivan@itk.com"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateUser() throws Exception {
        when(service.updateUser(any(), any())).thenReturn(internal);
        mvc.perform(put("/updateUser/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new UserRequest("New", "new@itk.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders").exists());
    }

    @Test
    void createUserInvalidEmailShouldReturnBadRequest() throws Exception {
        UserRequest invalid = UserRequest.builder().name("Test").email("bad").build();
        mvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}