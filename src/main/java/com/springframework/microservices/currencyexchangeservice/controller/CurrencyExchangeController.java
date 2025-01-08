package com.springframework.microservices.currencyexchangeservice.controller;

import com.springframework.microservices.currencyexchangeservice.entity.CurrencyExchange;
import com.springframework.microservices.currencyexchangeservice.repository.CurrencyExchangeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.pattern.PathPatternParser;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {
    @Autowired
    private CurrencyExchangeRespository respository;

    @Autowired
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange currencyExchange(@PathVariable String from, @PathVariable String to) {
        CurrencyExchange currencyExchange = respository.findByFromAndTo(from, to);
        if (currencyExchange == null) {
             throw new RuntimeException("Unable to find currency exchange from " + from + " to " + to);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        respository.findAll();
        return currencyExchange;
    }
}
