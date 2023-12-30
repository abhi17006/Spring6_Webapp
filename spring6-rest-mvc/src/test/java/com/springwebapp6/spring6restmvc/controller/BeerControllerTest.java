package com.springwebapp6.spring6restmvc.controller;

import com.springwebapp6.spring6restmvc.Service.BeerService;
import com.springwebapp6.spring6restmvc.Service.BeerServiceImpl;
import com.springwebapp6.spring6restmvc.model.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();//calling ServiceImpl class
    @Test
    void getBeerById(){
        try {

            //to return data
            Beer testBeer = beerServiceImpl.listBeers().get(0);

            given(beerService.getBeerById(any(UUID.class))).willReturn(testBeer);

            //perfoam OPearation on the API url, accept media type JSON and expect result "OK"
            mockMvc.perform(get("/api/v1/beer/"+UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}