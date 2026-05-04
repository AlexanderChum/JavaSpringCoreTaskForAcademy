package practice.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import practice.model.OrderStatus;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {

    @NotNull(message = "Не может отсутствовать")
    UUID userId;

    @NotEmpty(message = "Список продуктов не может быть пустым")
    List<String> products;

    @NotNull(message = "Сумма заказа обязательна")
    @Positive(message = "Сумма заказа должна быть положительной")
    Integer orderSum;

    OrderStatus orderStatus;
}
