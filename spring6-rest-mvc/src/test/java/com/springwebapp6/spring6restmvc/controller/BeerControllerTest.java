package com.springwebapp6.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springwebapp6.spring6restmvc.Service.BeerService;
import com.springwebapp6.spring6restmvc.Service.BeerServiceImpl;
import com.springwebapp6.spring6restmvc.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

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

    @Test //for Get List of Beers
    void testListBeers(){

        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        try {
            mockMvc.perform(get(BeerController.BEER_PATH)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(3)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateNewBeer(){
        BeerDTO beerDTO = beerServiceImpl.listBeers().get(0);
        beerDTO.setVersion(null);
        beerDTO.setId(null);

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

        try {
            mockMvc.perform(post(BeerController.BEER_PATH)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(beerDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testUpdateBeer(){
        BeerDTO beerDTO = beerServiceImpl.listBeers().get(0);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beerDTO));

        try {
            mockMvc.perform(put(BeerController.BEER_PATH_ID, beerDTO.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(beerDTO)))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verify(beerService).updateBeerById(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        given(beerService.deleteById(any())).willReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

    //test create BeerNull Beer Name for Data Validation
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
                  .andExpect(jsonPath("$.length()", is(6))) //check number validation given
                  .andReturn();

            System.out.println(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test //failed Test need to check
    void testUpdateBeerBlankName() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);
        beer.setBeerName("");
        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)));

    }


}