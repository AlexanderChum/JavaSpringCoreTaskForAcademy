package practice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mapper.CustomerMapper;
import practice.model.Customer;
import practice.model.dto.CustomerRequest;
import practice.model.dto.CustomerResponse;
import practice.model.exceptions.NotFoundException;
import practice.repository.CustomerRepository;
import practice.service.CustomerService;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getAllUsers(Pageable pageable) {
        log.info("Получен запрос на получение пользователей");
        return customerRepository.findAll(pageable)
                .map(customerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getUser(UUID id) {
        Customer customer = checkIfExists(id);
        log.info("Получен пользователь из бд");
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponse createUser(CustomerRequest request) {
        Customer customer = customerMapper.toEntity(request);
        Customer saved = customerRepository.save(customer);
        log.info("Создан новый пользователь");
        return customerMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteByUUID(UUID id) {
        Customer customer = checkIfExists(id);
        customerRepository.delete(customer);
        log.info("Пользователь удален");
    }

    @Override
    @Transactional
    public CustomerResponse updateUser(UUID id, CustomerRequest request) {
        Customer customer = checkIfExists(id);
        customerMapper.updateEntity(request, customer);
        Customer updated = customerRepository.save(customer);
        log.info("Пользователь обновлен");
        return customerMapper.toResponse(updated);
    }

    private Customer checkIfExists(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден, id: " + id));
    }
}