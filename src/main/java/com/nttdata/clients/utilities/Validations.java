package com.nttdata.clients.utilities;

import com.nttdata.clients.entity.ExchangeRate;
import com.nttdata.clients.exceptions.customs.CustomInformationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Validation class.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validations {
  /**
   * Validate fields of client.
   *
   * @param exchangeRate Account object
   */
  public static Mono<ExchangeRate> validateExchangeRate(ExchangeRate exchangeRate) {
    if (exchangeRate.getSell() != new Double(0)) {
        return Mono.error(new CustomInformationException("A exchange rate sell "
            + "cannot have zero value"));
    }
    if (exchangeRate.getBuy() != new Double(0)) {
      return Mono.error(new CustomInformationException("A exchange rate buy "
              + "cannot have zero value"));
    }

    return Mono.just(exchangeRate);
  }
}
