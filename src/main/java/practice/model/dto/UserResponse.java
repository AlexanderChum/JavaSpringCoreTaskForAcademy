package practice.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    @JsonView(InfoScopes.Internal.class)
    UUID id;

    @JsonView(InfoScopes.Public.class)
    String name;

    @JsonView(InfoScopes.Public.class)
    String email;

    @JsonView(InfoScopes.Internal.class)
    List<OrderResponse> orders;
}
