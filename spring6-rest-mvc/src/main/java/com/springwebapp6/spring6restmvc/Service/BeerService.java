package com.springwebapp6.spring6restmvc.Service;

import com.springwebapp6.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    Beer getBeerById(UUID id);
}
