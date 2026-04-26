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

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @NotNull(message = "id покупателя не может быть пустым")
    @Positive(message = "id покупателя не может быть меньше 1")
    Integer customerId;

    @NotNull(message = "Список товаров не может быть пустым")
    List<Integer> productIds;

    @NotBlank(message = "Адрес доставки не может быть пустым")
    String shippingAddress;
}
