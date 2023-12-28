package com.springwebapp6.spring6restmvc.Service;

import com.springwebapp6.spring6restmvc.model.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Get Beer Id is Service was called");
        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("Galaxy Cat")
                .upc("12345")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(125)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
