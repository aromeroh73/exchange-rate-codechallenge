package com.nttdata.clients.repository;

import com.nttdata.clients.entity.ExchangeRate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Client repository.
 */
@Repository
public interface ExchangeRateRepository extends ReactiveMongoRepository<ExchangeRate, ObjectId> {

  Flux<ExchangeRate> findByLastName(String lastName);

  Mono<ExchangeRate> findExchangeSell(String document);

}
