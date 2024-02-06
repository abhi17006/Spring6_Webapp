package com.springresttemplate.client;

import com.springresttemplate.model.BeerDTO;
import com.springresttemplate.model.BeerStyle;
import org.springframework.data.domain.Page;

public interface BeerClient {

    Page<BeerDTO> listBeers();
    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);
}
