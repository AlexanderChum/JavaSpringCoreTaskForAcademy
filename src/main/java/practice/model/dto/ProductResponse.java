package practice.model.dto;

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
public class ProductResponse {
    UUID productId;
    String name;
    String description;
    Double price;
    Integer quantityInStock;
}
