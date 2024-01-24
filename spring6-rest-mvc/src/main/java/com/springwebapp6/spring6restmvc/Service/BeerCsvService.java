package com.springwebapp6.spring6restmvc.Service;

import com.springwebapp6.spring6restmvc.model.BeerCSVRecord;


import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile);
}
