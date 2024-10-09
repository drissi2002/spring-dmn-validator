package com.wevioo.dmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "currencyClient", url = "http://localhost:8082/referential/")
public interface CurrencyClient {

    @GetMapping("/currencies")
    List<String> getAllCurrencies();
}