package com.springwebapp6.spring6restmvc.Service;

import com.springwebapp6.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer getCustomerId(UUID id);

    List<Customer> getAllCustomers();
}
