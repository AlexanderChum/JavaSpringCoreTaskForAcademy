package practice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id", updatable = false, nullable = false)
    UUID customerId;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(unique = true, nullable = false)
    String email;

    @Column(name = "contact_number")
    String contactNumber;
}
