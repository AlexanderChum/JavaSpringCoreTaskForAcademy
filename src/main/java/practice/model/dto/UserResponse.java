package practice.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import practice.model.Order;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    @JsonView(InfoScopes.Internal.class)
    Integer id;

    @JsonView(InfoScopes.Public.class)
    String name;

    @JsonView(InfoScopes.Public.class)
    String email;

    @JsonView(InfoScopes.Internal.class)
    List<Order> orders;
}
