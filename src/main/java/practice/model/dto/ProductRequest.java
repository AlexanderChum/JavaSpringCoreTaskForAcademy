package practice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank(message = "Название продукта не может быть пустым")
    String name;

    String description;

    @NotNull(message = "Цена не может быть пустой")
    @Positive(message = "Цена должна быть положительной")
    Double price;

    @NotNull(message = "Количество на складе не может быть пустым")
    @Positive(message = "Количество на складе должно быть положительным")
    Integer quantityInStock;
}
