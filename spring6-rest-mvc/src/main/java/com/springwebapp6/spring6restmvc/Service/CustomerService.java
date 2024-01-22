package com.springwebapp6.spring6restmvc.Service;

import com.springwebapp6.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Optional<CustomerDTO> getCustomerId(UUID id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID uuid, CustomerDTO customer);

    Boolean deleteById(UUID uuid);

    Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer);
}
