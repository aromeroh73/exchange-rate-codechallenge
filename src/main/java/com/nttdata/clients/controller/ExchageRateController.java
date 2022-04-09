package com.nttdata.clients.controller;

import com.nttdata.clients.dto.request.ExchangeRateRequest;
import com.nttdata.clients.entity.ExchangeRate;
import com.nttdata.clients.service.ExchangeRateService;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * RestController for client service.
 */
@RestController
@RequestMapping("/exchange")
public class ExchageRateController {
  @Autowired
  private ExchangeRateService exchangeRateService;

  @Value("${spring.cloud.config.profile}")
  private String profile;

  @GetMapping("/test")
  public String test() {
    return profile;
  }

  @PostMapping
  public Mono<ExchangeRate> createClient(@Valid @RequestBody ExchangeRateRequest request) {
    ExchangeRate exchangeRate = new ExchangeRate(request);
    return exchangeRateService.exchangeRate(exchangeRate);
  }

  @GetMapping("/realtime /")
  public Mono<ExchangeRate> searchExchangeRealTime(@RequestParam("id") String id) {
    return exchangeRateService.extractExchange(id);
  }

  @GetMapping("/exchange/{profile}?date={date}")
  public Flux<ExchangeRate> searchClientByLastName(@PathVariable("profile") String profile) {
    return exchangeRateService.extractExchangeByProfile(profile);
  }


}
