package com.springwebapp6.spring6restmvc.controller;

import com.springwebapp6.spring6restmvc.Service.CustomerService;
import com.springwebapp6.spring6restmvc.model.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer")
    public List<CustomerDTO> listAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer/{customerId}")
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID uuid){
        return customerService.getCustomerId(uuid);
    }

    @PostMapping("/customer")
    public ResponseEntity addCustomer(@RequestBody CustomerDTO customer){
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);

        //adding headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/"+ savedCustomer.getId().toString());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @PutMapping("/customer/{customerId}")
    public ResponseEntity updateById(@PathVariable("customerId") UUID uuid , @RequestBody CustomerDTO customer){
        customerService.updateCustomerById(uuid, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity deleteById(@PathVariable("customerId") UUID uuid){
        customerService.deleteById(uuid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
