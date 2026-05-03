package practice.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {

    @JsonView(InfoScopes.Internal.class)
    UUID customerId;

    @JsonView(InfoScopes.Public.class)
    String firstName;

    @JsonView(InfoScopes.Public.class)
    String lastName;

    @JsonView(InfoScopes.Public.class)
    String email;

    @JsonView(InfoScopes.Internal.class)
    String contactNumber;
}
