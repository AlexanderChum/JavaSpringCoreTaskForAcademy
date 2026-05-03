package practice.service;

import practice.model.dto.CustomerRequest;
import practice.model.dto.CustomerResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerResponse> getAllUsers();

    CustomerResponse getUser(UUID id);

    CustomerResponse createUser(CustomerRequest request);

    void deleteByUUID(UUID id);

    CustomerResponse updateUser(UUID id, CustomerRequest request);
}
