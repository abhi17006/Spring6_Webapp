package com.springwebapp6.spring6restmvc.Service;

import com.springwebapp6.spring6restmvc.mappers.CustomerMapper;
import com.springwebapp6.spring6restmvc.model.CustomerDTO;
import com.springwebapp6.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public Optional<CustomerDTO> getCustomerId(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(id).orElse(null)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomerById(UUID uuid, CustomerDTO customer) {

    }

    @Override
    public void deleteById(UUID uuid) {

    }
    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
}
