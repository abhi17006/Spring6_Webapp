package com.springwebapp6.spring6restmvc.Service;

import com.springwebapp6.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerDTO getCustomerId(UUID id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    void updateCustomerById(UUID uuid, CustomerDTO customer);

    void deleteById(UUID uuid);
}
