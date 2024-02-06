package com.springresttemplate.client;

import com.springresttemplate.model.BeerDTO;
import com.springresttemplate.model.BeerDTOPageImpl;
import com.springresttemplate.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    //call RestTemplateBuilder class t
    private final RestTemplateBuilder restTemplateBuilder;

    private static final String GET_BEER_PATH = "/api/v1/beer";
    private static final String GET_BEER_BY_ID_PATH=GET_BEER_PATH+"/{beerId}";

    @Override
    public Page<BeerDTO> listBeers() {
        return this.listBeers(null, null, null, null, null);
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        //
        RestTemplate restTemplate = restTemplateBuilder.build();

//        //using getFOrEntity method type of RepsonseEntity
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL+ GET_BEER_PATH,String.class);
//
//        //map response, useful when you don't know the API response
//        ResponseEntity<Map> mapResponseEntity =
//                restTemplate.getForEntity(BASE_URL+GET_BEER_PATH, Map.class);
//
//        //work with Jackson library with JsonNode
//        ResponseEntity<JsonNode> jsonNodeResponseEntity =
//                restTemplate.getForEntity(BASE_URL+GET_BEER_PATH, JsonNode.class);
//
//        //accessing JSOn response content attributes from the path
//        jsonNodeResponseEntity.getBody().findPath("content")
//                        .elements().forEachRemaining(node -> {
//                    System.out.println(node.get("beerName").asText());
//                });
//
//        System.out.println(mapResponseEntity.getBody()); //print getBody data


        //Uri component builder get beer path, use uriComponentsBuilder.toUriString() to get path from UriComponentsBuilder
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);

        //check beerName is not null
        if (beerName != null) {
            uriComponentsBuilder.queryParam("beerName", beerName);
        }

        if (beerStyle != null) {
            uriComponentsBuilder.queryParam("beerStyle", beerStyle);
        }

        if (showInventory != null) {
            uriComponentsBuilder.queryParam("showInventory", beerStyle);
        }

        if (pageNumber != null) {
            uriComponentsBuilder.queryParam("pageNumber", beerStyle);
        }

        if (pageSize != null) {
            uriComponentsBuilder.queryParam("pageSize", beerStyle);
        }

        //using getFOrEntity method type of RepsonseEntity with jackson libray and Page
        ResponseEntity<BeerDTOPageImpl> responseEntity =
                restTemplate.getForEntity(uriComponentsBuilder.toUriString(), BeerDTOPageImpl.class);


        return responseEntity.getBody();
    }

    @Override //rest template for getById()
    public BeerDTO getBeerById(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //return get request data
        return restTemplate.getForObject(GET_BEER_BY_ID_PATH, BeerDTO.class, beerId);
    }
}
