package com.nttdata.clients.service;

import com.nttdata.clients.entity.ExchangeRate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Client service interface.
 */
public interface ExchangeRateService {

  Mono<ExchangeRate> exchangeRate(ExchangeRate exchangeRate);

  Mono<ExchangeRate> extractExchange(String id);

  Flux<ExchangeRate> extractExchangeByProfile(String lastName);

  Mono<ExchangeRate> searchClientByDocument(String documentNumber);



}
