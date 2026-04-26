package practice.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import practice.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    @JsonView(InfoScopes.Internal.class)
    Integer orderId;

    @JsonView(InfoScopes.Internal.class)
    Integer customerId;

    @JsonView(InfoScopes.Internal.class)
    List<ProductResponse> products;

    @JsonView(InfoScopes.Internal.class)
    LocalDateTime orderDate;

    @JsonView(InfoScopes.Internal.class)
    String address;

    @JsonView(InfoScopes.Internal.class)
    Integer orderSum;

    @JsonView(InfoScopes.Internal.class)
    OrderStatus orderStatus;
}
