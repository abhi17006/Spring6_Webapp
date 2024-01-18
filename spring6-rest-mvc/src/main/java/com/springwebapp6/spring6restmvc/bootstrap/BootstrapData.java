package com.springwebapp6.spring6restmvc.bootstrap;

import com.springwebapp6.spring6restmvc.entities.Beer;
import com.springwebapp6.spring6restmvc.entities.Customer;
import com.springwebapp6.spring6restmvc.model.BeerDTO;
import com.springwebapp6.spring6restmvc.model.BeerStyle;
import com.springwebapp6.spring6restmvc.model.CustomerDTO;
import com.springwebapp6.spring6restmvc.repository.BeerRepository;
import com.springwebapp6.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

//classes to create H2 database (memory DB) store records
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData(){

        if(beerRepository.count() == 0){
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12345")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(125)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("1234455")
                    .price(new BigDecimal("10.99"))
                    .quantityOnHand(255)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.ALE)
                    .upc("12345546")
                    .price(new BigDecimal("14.99"))
                    .quantityOnHand(155)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }

    }

    private void loadCustomerData(){
        if(customerRepository.count()==0){
            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer1")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer2")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer3")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1,customer2,customer3));
        }

    }
}
