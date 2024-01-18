package com.springwebapp6.spring6restmvc.controller;

import com.springwebapp6.spring6restmvc.entities.Customer;
import com.springwebapp6.spring6restmvc.model.CustomerDTO;
import com.springwebapp6.spring6restmvc.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
class CustomerControllerIntegrationTest {
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;


    @Test
    void testListAll(){
        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }
    @Rollback
    @Transactional
    @Test
    void testListAllEmptyList(){
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }
    //expected exception for NotNull, NotFound exception
    @Test
    void testGetByIdNotFound(){
        assertThrows(NotFoundException.class, () ->{
           customerController.getCustomerById(UUID.randomUUID());
        });
    }
    @Test
    void testGetById(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

}