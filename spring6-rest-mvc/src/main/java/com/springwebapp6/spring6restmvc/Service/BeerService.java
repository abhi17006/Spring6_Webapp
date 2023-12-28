package com.springwebapp6.spring6restmvc.Service;

import com.springwebapp6.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);
}
