package com.springwebapp6.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springwebapp6.spring6restmvc.entities.Beer;
import com.springwebapp6.spring6restmvc.mappers.BeerMapper;
import com.springwebapp6.spring6restmvc.model.BeerDTO;
import com.springwebapp6.spring6restmvc.model.BeerStyle;
import com.springwebapp6.spring6restmvc.repository.BeerRepository;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class   BeerControllerIntegrationTest {

    //test controller with Data layer
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext webApplicationContext;

    //setup method  for WebApplicationContext
    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        //add webApplicationContext
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                //add static springSecurity method into MockMvc
                .apply(springSecurity())
                .build();
    }

    //testing for expected exceptions
    @Test
    void testPatchBeerBadName() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Nameakjddjjfsfshfsh788555d67f6s4fs4fss4f5s");

         mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                         .with(httpBasic(BeerControllerTest.USERNAME,BeerControllerTest.PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest());


        //System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testBeerIdNotFound(){
        assertThrows(NotFoundException.class, ()->{
            beerController.getBeerById(UUID.randomUUID());
        });
    }
    @Test
    void testGetById(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerController.getBeerById(beer.getId());
        assertThat(beerDTO).isNotNull();
    }

    @Test
    void testListBeers(){
        Page<BeerDTO> dtos = beerController.listBeers(null,null, false, 1, 2413);
        assertThat(dtos.getContent().size()).isEqualTo(1000);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();

        Page<BeerDTO> dtos = beerController.listBeers(null,null, false, 1, 25);
        assertThat(dtos.getContent().size()).isEqualTo(0);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest(){
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Beer2024")
                .build();
        ResponseEntity responseEntity = beerController.handlePost(beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }


    @Rollback
    @Transactional
    @Test
    void updateExistingBeerTest(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    //updateBeerNot Found
    @Test
    void testUpdateBeerNotFound(){
        assertThrows(NotFoundException.class, ()->{
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }
    
    
    //query parameter Test

    //No Authentication testcase
    @Test
    void testNoAuth() {
        try {
            mockMvc.perform(get(BeerController.BEER_PATH)
                            .queryParam("beerName", "IPA")
                            .queryParam("pageSize", "800"))
                    .andExpect(status().isUnauthorized());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testListBeersByName() {
        try {
            mockMvc.perform(get(BeerController.BEER_PATH)
                            .with(httpBasic(BeerControllerTest.USERNAME,BeerControllerTest.PASSWORD))
                    .queryParam("beerName", "IPA")
                    .queryParam("pageSize", "800"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.size()", is(321)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testListBeersByStyle() {
        try {
            mockMvc.perform(get(BeerController.BEER_PATH)
                            .with(httpBasic(BeerControllerTest.USERNAME,BeerControllerTest.PASSWORD))
                            .queryParam("beerStyle", BeerStyle.IPA.name())
                            .queryParam("pageSize", "800"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.size()", is(547)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testListBeersByStyleAndName() {
        try {
            mockMvc.perform(get(BeerController.BEER_PATH)
                            .with(httpBasic(BeerControllerTest.USERNAME,BeerControllerTest.PASSWORD))
                            .queryParam("beerName", "IPA")
                            .queryParam("beerStyle", BeerStyle.IPA.name())
                            .queryParam("pageSize", "800"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.size()", is(297)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testListBeersByStyleAndNameShowInventoryFalse(){
        try {
            mockMvc.perform(get(BeerController.BEER_PATH)
                            .with(httpBasic(BeerControllerTest.USERNAME,BeerControllerTest.PASSWORD))
                            .queryParam("beerName", "IPA")
                            .queryParam("beerStyle", BeerStyle.IPA.name())
                            .queryParam("showInventory", "false")
                            .queryParam("pageSize", "800"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.size()", is(297)))
                    .andExpect(jsonPath("$.content[0].quantityOnHand").value(IsNull.nullValue()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testListBeersByStyleAndNameShowInventoryTrue(){
        try {
            mockMvc.perform(get(BeerController.BEER_PATH)
                            .with(httpBasic(BeerControllerTest.USERNAME,BeerControllerTest.PASSWORD))
                            .queryParam("beerName", "IPA")
                            .queryParam("beerStyle", BeerStyle.IPA.name())
                            .queryParam("showInventory", "true")
                            .queryParam("pageSize", "800"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.size()", is(297)))
                    .andExpect(jsonPath("$.content[0].quantityOnHand").value(IsNull.notNullValue()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //using pagination
    @Test
    void testListBeersByStyleAndNameShowInventoryTruePage2(){
        try {
            mockMvc.perform(get(BeerController.BEER_PATH)
                            .with(httpBasic(BeerControllerTest.USERNAME,BeerControllerTest.PASSWORD))
                            .queryParam("beerName", "IPA")
                            .queryParam("beerStyle", BeerStyle.IPA.name())
                            .queryParam("showInventory", "true")
                            .queryParam("pageNumber","2")
                            .queryParam("pageSize","50"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.size()", is(50)))
                    .andExpect(jsonPath("$.content[0].quantityOnHand").value(IsNull.notNullValue()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}