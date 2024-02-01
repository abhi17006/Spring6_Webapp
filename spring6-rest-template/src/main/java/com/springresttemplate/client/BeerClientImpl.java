package com.springresttemplate.client;

import com.springresttemplate.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    //call RestTemplateBuilder class t
    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public Page<BeerDTO> listBeers() {
        //
        RestTemplate restTemplate = restTemplateBuilder.build();

        //using getFOrEntity method type of RepsonseEntity
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/v1/beer",String.class);
        System.out.println(responseEntity.getBody()); //print getBody data
        return null;
    }
}
