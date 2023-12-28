package com.springwebapp6.spring6restmvc.controller;

import com.springwebapp6.spring6restmvc.Service.CustomerService;
import com.springwebapp6.spring6restmvc.Service.CustomerServiceImpl;
import com.springwebapp6.spring6restmvc.model.Customer;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer")
    public List<Customer> listAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer/{customerId}")
    public Customer getCustomerById( @PathVariable("customerId") UUID uuid){
        return customerService.getCustomerId(uuid);
    }
}
