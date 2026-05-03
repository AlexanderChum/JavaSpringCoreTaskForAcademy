package practice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {

    @NotBlank(message = "Имя не может быть пустым")
    String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    String lastName;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неверный формат email")
    String email;

    String contactNumber;
}
