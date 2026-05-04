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
import practice.services.UserService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    UserService userService;

    final UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-123456789012");

    final UserResponse internal = UserResponse.builder()
            .id(userId)
            .name("Иван")
            .email("ivan@itk.com")
            .orders(List.of())
            .build();

    @Test
    void getUser() throws Exception {
        when(userService.getUser(userId)).thenReturn(internal);

        mvc.perform(get("/user/{UUID}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.name").value("Иван"));
    }

    @Test
    void createUser() throws Exception {
        when(userService.createUser(any(UserRequest.class))).thenReturn(internal);

        UserRequest request = UserRequest.builder()
                .name("Иван")
                .email("ivan@itk.com")
                .build();

        mvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userId.toString()));
    }

    @Test
    void updateUser() throws Exception {
        when(userService.updateUser(any(UUID.class), any(UserRequest.class))).thenReturn(internal);

        UserRequest updateRequest = UserRequest.builder()
                .name("Новое имя")
                .email("new@itk.com")
                .build();

        mvc.perform(put("/updateUser/{UUID}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.orders").exists());
    }

    @Test
    void deleteUser() throws Exception {
        mvc.perform(delete("/{UUID}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void createUserInvalidEmailShouldReturnBadRequest() throws Exception {
        UserRequest invalid = UserRequest.builder()
                .name("Test")
                .email("bad")
                .build();

        mvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserBlankNameShouldReturnBadRequest() throws Exception {
        UserRequest invalid = UserRequest.builder()
                .name("")
                .email("valid@itk.com")
                .build();

        mvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}