package practice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @NotNull(message = "id покупателя не может быть пустым")
    UUID customerId;

    @NotNull(message = "Список товаров не может быть пустым")
    List<UUID> productIds;

    @NotBlank(message = "Адрес доставки не может быть пустым")
    String shippingAddress;
}
