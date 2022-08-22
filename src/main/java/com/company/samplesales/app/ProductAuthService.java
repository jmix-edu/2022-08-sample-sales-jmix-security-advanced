package com.company.samplesales.app;

import io.jmix.core.UnconstrainedDataManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authenticated/product")
public class ProductAuthService {

    private UnconstrainedDataManager unconstrainedDataManager;

    public ProductAuthService(UnconstrainedDataManager unconstrainedDataManager) {
        this.unconstrainedDataManager = unconstrainedDataManager;
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<String>> list() {
        List<String> productNames = unconstrainedDataManager.loadValues("select p.name from sales_Product p order by p.name")
                .property("name")
                .list()
                .stream().map(row -> (String) row.getValue("name"))
                .collect(Collectors.toList());
        return new ResponseEntity<>(productNames, HttpStatus.OK);
    }
}
