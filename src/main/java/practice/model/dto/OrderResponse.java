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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    @JsonView(InfoScopes.Internal.class)
    Integer id;

    @JsonView(InfoScopes.Internal.class)
    Integer userId;

    @JsonView(InfoScopes.Internal.class)
    List<String> products;

    @JsonView(InfoScopes.Internal.class)
    Integer orderSum;

    @JsonView(InfoScopes.Internal.class)
    OrderStatus orderStatus;
}
