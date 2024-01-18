package com.springwebapp6.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springwebapp6.spring6restmvc.Service.BeerService;
import com.springwebapp6.spring6restmvc.Service.BeerServiceImpl;
import com.springwebapp6.spring6restmvc.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.UUID;


import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {


    //JUNit-test
//    @Autowired
//    BeerController beerController;
//    @Test
//    void getBeerById() {
//        System.out.println(beerController.getBeerById(UUID.randomUUID()));
//    }

    //Mockito Test
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;
    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();//calling ServiceImpl class

    @Test
    void getBeerByIdNotFound() {

        try {
            given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());
            mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getBeerById(){
        try {

            //to return data
            BeerDTO testBeer = beerServiceImpl.listBeers().get(0);

            given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.of(testBeer));

            //perfoam OPearation on the API url, accept media type JSON and expect result "OK"
            //mockMvc.perform(get("/api/v1/beer/"+UUID.randomUUID()) //insead use defined variables
            mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeer.getId())
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    //using jsonPath matchers  to match result using Is.is() method
                    .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                    .andExpect((jsonPath("$.beerName", is(testBeer.getBeerName()))));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //test create BeerNull Beer Name
    @Test
    void testCreateBeerNullBeerName(){
        BeerDTO beerDTO = BeerDTO.builder().build();
        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

        //mockMvc test
        try {
          MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(beerDTO)))
                  .andExpect(status().isBadRequest())
                  .andExpect(jsonPath("$.length()", is(2)))
                  .andReturn();

            System.out.println(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}