package com.nttdata.clients.service;

import com.nttdata.clients.entity.ExchangeRate;
import com.nttdata.clients.exceptions.customs.CustomNotFoundException;
import com.nttdata.clients.repository.ExchangeRateRepository;
import com.nttdata.clients.utilities.Validations;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Client service implementation.
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
  private static final Logger logger_consola = LoggerFactory.getLogger("consola");
  private static final Logger logger_file = LoggerFactory.getLogger("clients_log");

  @Autowired
  ExchangeRateRepository exchangeRateRepository;

  @Override
  public Mono<ExchangeRate> extractExchange(String id) {
    return exchangeRateRepository.findById(new ObjectId(id))
        .switchIfEmpty(Mono.error(new CustomNotFoundException("Client not found")));
  }

  @Override
  public Flux<ExchangeRate> extractExchangeByProfile(String lastName) {
    return exchangeRateRepository.findByLastName(lastName);
  }

  @Override
  public Mono<ExchangeRate> searchClientByDocument(String documentNumber) {
    return exchangeRateRepository.findExchangeSell(documentNumber)
        .switchIfEmpty(Mono.error(new CustomNotFoundException("Client not found")));
  }

  @Override
  public Mono<ExchangeRate> exchangeRate(ExchangeRate exchangeRate) {
    return Validations.validateExchangeRate(exchangeRate)
        .flatMap(c -> exchangeRateRepository.findExchangeSell(exchangeRate.getDocumentId())
            .switchIfEmpty(exchangeRateRepository.save(exchangeRate)
                .map(x -> {
                  logger_file.info("Se creo una  nuevo postura",
                       exchangeRate.getDocumentId());
                  logger_consola
                      .info("Se creo una  nuevo postura",
                           exchangeRate.getDocumentId());
                  return x;
                })
            ));
  }

}
