package practice.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
public class OrderResponse {

    @JsonView(InfoScopes.Internal.class)
    UUID id;

    @JsonView(InfoScopes.Internal.class)
    UUID userId;

    @JsonView(InfoScopes.Internal.class)
    List<String> products;

    @JsonView(InfoScopes.Internal.class)
    Integer orderSum;

    @JsonView(InfoScopes.Internal.class)
    OrderStatus orderStatus;
}
