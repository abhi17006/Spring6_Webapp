package com.springwebapp6.spring6restmvc.bootstrap;

import com.springwebapp6.spring6restmvc.Service.BeerCsvService;
import com.springwebapp6.spring6restmvc.entities.Beer;
import com.springwebapp6.spring6restmvc.entities.Customer;
import com.springwebapp6.spring6restmvc.model.BeerCSVRecord;
import com.springwebapp6.spring6restmvc.model.BeerDTO;
import com.springwebapp6.spring6restmvc.model.BeerStyle;
import com.springwebapp6.spring6restmvc.model.CustomerDTO;
import com.springwebapp6.spring6restmvc.repository.BeerRepository;
import com.springwebapp6.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//classes to create H2 database (memory DB) store records
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCsvData();
        loadCustomerData();

    }

    private void loadCsvData() {
        if (beerRepository.count()<10){

            try {
                File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

                List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);

                recs.forEach(beerCSVRecord -> {
                    BeerStyle beerStyle = switch (beerCSVRecord.getStyle()){
                        case "American Pale Lager" -> BeerStyle.LAGER;
                        case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                                BeerStyle.ALE;
                        case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                        case "American Porter" -> BeerStyle.PORTER;
                        case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                        case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                        case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                        case "English Pale Ale" -> BeerStyle.PALE_ALE;
                        default -> BeerStyle.PILSNER;
                    };

                    beerRepository.save(Beer.builder()
                                .beerName(StringUtils.abbreviate( beerCSVRecord.getBeer(),  25))
                                .beerStyle(beerStyle)
                                .price(BigDecimal.TEN)
                                .upc(beerCSVRecord.getRow().toString())
                                .quantityOnHand(beerCSVRecord.getCount())
                            .build());
                });
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }

    private void loadBeerData(){

        if(beerRepository.count() == 0){
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12345")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(125)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("1234455")
                    .price(new BigDecimal("10.99"))
                    .quantityOnHand(255)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.ALE)
                    .upc("12345546")
                    .price(new BigDecimal("14.99"))
                    .quantityOnHand(155)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }

    }

    private void loadCustomerData(){
        if(customerRepository.count()==0){
            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer1")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer2")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Customer3")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1,customer2,customer3));
        }

    }
}
