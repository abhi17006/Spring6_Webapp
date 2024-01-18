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

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH +"/{customerId}";
    private final CustomerService customerService;

    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> listAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID uuid){
        return customerService.getCustomerId(uuid).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity addCustomer(@RequestBody CustomerDTO customer){
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);

        //adding headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/"+ savedCustomer.getId().toString());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("customerId") UUID uuid , @RequestBody CustomerDTO customer){
        customerService.updateCustomerById(uuid, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("customerId") UUID uuid){
        customerService.deleteById(uuid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID customerId,
                                            @RequestBody CustomerDTO customer){

        customerService.patchCustomerById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
