package com.springwebapp6.spring6restmvc.mappers;

import com.springwebapp6.spring6restmvc.entities.Beer;
import com.springwebapp6.spring6restmvc.entities.Customer;
import com.springwebapp6.spring6restmvc.model.BeerDTO;
import com.springwebapp6.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    //CustomerDTO to Customer object
    Customer customerDtoToCustomer(CustomerDTO dto);

    //Customer to CustomerDto object
    CustomerDTO customerToCustomerDto(Customer customer);
}
