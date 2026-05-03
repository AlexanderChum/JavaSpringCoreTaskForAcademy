package practice.mapper;

import org.springframework.stereotype.Component;
import practice.model.Customer;
import practice.model.dto.CustomerRequest;
import practice.model.dto.CustomerResponse;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .contactNumber(customer.getContactNumber())
                .build();
    }

    public Customer toEntity(CustomerRequest request) {
        return Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .contactNumber(request.getContactNumber())
                .build();
    }

    public void updateEntity(CustomerRequest request, Customer customer) {
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setContactNumber(request.getContactNumber());
    }
}
