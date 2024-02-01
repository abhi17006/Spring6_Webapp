package com.springresttemplate.client;

import com.springresttemplate.model.BeerDTO;
import org.springframework.data.domain.Page;

public interface BeerClient {

    Page<BeerDTO> listBeers();
}
