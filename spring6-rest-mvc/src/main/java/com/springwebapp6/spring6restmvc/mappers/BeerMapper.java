package com.springwebapp6.spring6restmvc.mappers;

import com.springwebapp6.spring6restmvc.entities.Beer;
import com.springwebapp6.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    //beerDto to Beer object
    Beer beerDtoToBeer(BeerDTO dto);

    //convert from Beer to BeerDto object
    BeerDTO beerToBeerDto(Beer beer);
}
