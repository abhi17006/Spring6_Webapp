package com.springwebapp6.spring6restmvc.repository;

import com.springwebapp6.spring6restmvc.entities.Beer;
import com.springwebapp6.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    //Database Testing giving VarChar size more the actual handling ConstrainViolation Exception
    @Test
    void testSaveBeerNameTooLong(){
        assertThrows(ConstraintViolationException.class, ()->{
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("AB_Beer 76465455534545646545gdgdrhdhehehrehehbehdhdhdhd")
                    .beerStyle(BeerStyle.ALE)
                    .upc("4585236")
                    .price(new BigDecimal("11.78"))
                    .build());

            //need to add flush() method while testing Validation
            beerRepository.flush();
        });

    }
}