package com.springwebapp6.spring6restmvc.bootstrap;

import com.springwebapp6.spring6restmvc.Service.BeerCsvService;
import com.springwebapp6.spring6restmvc.Service.BeerCsvServiceImpl;
import com.springwebapp6.spring6restmvc.repository.BeerRepository;
import com.springwebapp6.spring6restmvc.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

//import CSVSERVICEImpl class
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService  beerCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository,customerRepository, beerCsvService);
    }


    @Test
    void Testrun() throws Exception {
        bootstrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}