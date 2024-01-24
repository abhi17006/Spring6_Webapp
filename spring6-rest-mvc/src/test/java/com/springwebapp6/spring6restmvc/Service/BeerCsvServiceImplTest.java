package com.springwebapp6.spring6restmvc.Service;

import com.springwebapp6.spring6restmvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BeerCsvServiceImplTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    void convertCSV() {
        try {
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
            List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);
            System.out.println(recs.size());
            assertThat(recs.size()).isGreaterThan(0);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}