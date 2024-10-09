package com.wevioo.dmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "countryClient", url = "http://localhost:8082/referential/")
public interface CountryClient {

    @GetMapping("/countries")
    List<String> getAllCountries();
}