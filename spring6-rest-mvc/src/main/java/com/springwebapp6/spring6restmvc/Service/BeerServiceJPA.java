package com.springwebapp6.spring6restmvc.Service;

import com.springwebapp6.spring6restmvc.entities.Beer;
import com.springwebapp6.spring6restmvc.mappers.BeerMapper;
import com.springwebapp6.spring6restmvc.model.BeerDTO;
import com.springwebapp6.spring6restmvc.model.BeerStyle;
import com.springwebapp6.spring6restmvc.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService{

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory) {

        List<Beer> beerList;
        //check  current string is BeerName and beerStyle is null
        if(StringUtils.hasText(beerName) && beerStyle == null){
            beerList = listBeersByName(beerName);
        }
        //check  current string is not BeerName and beerStyle is not null
        else if(!StringUtils.hasText(beerName) && beerStyle != null){
            beerList = listBeerStyle(beerStyle);
        }
        //check both value is present
        else if(StringUtils.hasText(beerName) && beerStyle != null){
            beerList = listBeersByNameAndStyle(beerName,beerStyle);
        }else{
            //if there is no Query Param then return all
            beerList = beerRepository.findAll();
        }

        //showInventory is not null
        if(showInventory != null && !showInventory){
            beerList.forEach(beer -> beer.setQuantityOnHand(null));
        }
        return beerList
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    private List<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%",beerStyle);
    }

    public List<Beer> listBeerStyle(BeerStyle beerStyle) {
        return beerRepository.findAllByBeerStyle(beerStyle);
    }

    public List<Beer> listBeersByName(String beerName) {
        //using SQL wildcard %beerName%
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%");
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        //using lambda fn with ifPresentOrElse
        beerRepository.findById(beerId).ifPresentOrElse(foundBeer ->{
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
        }, ()->{
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        if(beerRepository.existsById(beerId)){
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            if (StringUtils.hasText(beer.getBeerName())){
                foundBeer.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null){
                foundBeer.setBeerStyle(beer.getBeerStyle());
            }
            if (StringUtils.hasText(beer.getUpc())){
                foundBeer.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null){
                foundBeer.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null){
                foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

//    @Override
//    public List<BeerDTO> listBeersByName(String beerName) {
//        //using SQL wildcard %beerName%
//        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%")
//                .stream()
//                .map(beerMapper::beerToBeerDto)
//                .collect(Collectors.toList());
//    }
}
