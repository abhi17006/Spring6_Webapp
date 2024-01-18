package com.springwebapp6.spring6restmvc.repository;

import com.springwebapp6.spring6restmvc.entities.Beer;
import com.springwebapp6.spring6restmvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest //DataBase Testing
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("AB_Beer")
                .beerStyle(BeerStyle.ALE)
                .upc("4585236")
                .price(new BigDecimal("11.78"))
                .build());

        //need to add flush() method while testing Validation
        beerRepository.flush();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}