package practice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.model.dto.CustomerRequest;
import practice.model.dto.CustomerResponse;

import java.util.UUID;

public interface CustomerService {
    Page<CustomerResponse> getAllUsers(Pageable pageable);

    CustomerResponse getUser(UUID id);

    CustomerResponse createUser(CustomerRequest request);

    void deleteByUUID(UUID id);

    CustomerResponse updateUser(UUID id, CustomerRequest request);
}
