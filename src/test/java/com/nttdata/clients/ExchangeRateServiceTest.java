package com.nttdata.clients;

import com.nttdata.clients.entity.ExchangeRate;
import com.nttdata.clients.repository.ExchangeRateRepository;
import com.nttdata.clients.service.ExchangeRateServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ExchangeRateServiceTest {
  @Mock
  private ExchangeRateRepository exchangeRateRepository;

  @InjectMocks
  private ExchangeRateServiceImpl clientService;

  @Test
  void testGetExchangeRate() {
    ExchangeRate exchangeRate = new ExchangeRate();
    exchangeRate.setBuy(2.8);

    var monoClient = Mono.just(exchangeRate);
    when(exchangeRateRepository.findExchangeSell("00000001")).thenReturn(monoClient);

    var data = clientService.searchClientByDocument("00000001");
    StepVerifier
        .create(data)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("Juan", ac.getBuy());
          Assertions.assertEquals("Perez", ac.getDocumentId());
        })
        .verifyComplete();
  }

  @Test
  void testCreateExchange() {
    ObjectId id = new ObjectId();
    ExchangeRate exchangeRate = new ExchangeRate("", 2.7,
        5.7, new Date(), true);

    var monoClient = Mono.just(exchangeRate);
    when(exchangeRateRepository.findExchangeSell("71489282")).thenReturn(Mono.empty());
    when(exchangeRateRepository.save(exchangeRate)).thenReturn(monoClient);

    var result = clientService.exchangeRate(exchangeRate);
    StepVerifier
        .create(result)
        .expectSubscription()
        .consumeNextWith(ac -> {
          Assertions.assertNotNull(ac);
          Assertions.assertEquals("71489282", ac.getDocumentId());
        })
        .verifyComplete();
  }
}
