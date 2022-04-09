package com.nttdata.clients;

import com.nttdata.clients.controller.ExchageRateController;
import com.nttdata.clients.entity.ExchangeRate;
import com.nttdata.clients.service.ExchangeRateService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ExchageRateController.class)
class ExchangeRateApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ExchangeRateService exchangeRateService;

	@Test
	void testListByLastName() {
		ExchangeRate exchangeRate = new ExchangeRate();
		exchangeRate.setBuy(3.5);

		ExchangeRate exchangeRate1 = new ExchangeRate();
		exchangeRate1.setBuy(1.0);

		var fluxClient = Flux.just(exchangeRate, exchangeRate1);
		when(exchangeRateService.extractExchangeByProfile("Perez")).thenReturn(fluxClient);

		var responseBody = webTestClient
				.get()
				.uri("/clients/lname/Perez")
				.exchange()
				.expectStatus().isOk()
				.returnResult(ExchangeRate.class)
				.getResponseBody();

		StepVerifier
				.create(responseBody)
				.expectSubscription()
				.expectNext(exchangeRate)
				.expectNext(exchangeRate1)
				.verifyComplete();
	}

	@Test
	void testGetById() {
		ObjectId id = new ObjectId();

		ExchangeRate exchangeRate = new ExchangeRate();
		exchangeRate.setDocumentId("21313");
		exchangeRate.setBuy(3.7);
		exchangeRate.setSell(3.2);

		var monoClient = Mono.just(exchangeRate);
		when(exchangeRateService.extractExchange(id.toString())).thenReturn(monoClient);

		var responseBody = webTestClient
				.get()
				.uri("/clients/id/" + id)
				.exchange()
				.expectStatus().isOk()
				.returnResult(ExchangeRate.class)
				.getResponseBody();

		StepVerifier
				.create(responseBody)
				.expectSubscription()
				.verifyComplete();
	}

	@Test
	void testGetByClientByDocumentNumber() {
		ExchangeRate exchangeRate = new ExchangeRate();
		exchangeRate.setBuy(2.2);
		exchangeRate.setSell(2.3);

		var monoClient = Mono.just(exchangeRate);
		when(exchangeRateService.searchClientByDocument("00000001")).thenReturn(monoClient);

		var responseBody = webTestClient
				.get()
				.uri("/clients/document/00000001")
				.exchange()
				.expectStatus().isOk()
				.returnResult(ExchangeRate.class)
				.getResponseBody();

		StepVerifier
				.create(responseBody)
				.expectSubscription()
				.verifyComplete();
	}

	@Test
	void testCreate() {
		ExchangeRate exchangeRate = new ExchangeRate();
		exchangeRate.setBuy(2.9);
		exchangeRate.setSell(3.2);
		var monoAccount = Mono.just(exchangeRate);
		when(exchangeRateService.exchangeRate(exchangeRate)).thenReturn(monoAccount);

		var responseBody = webTestClient
				.post()
				.uri("/clients")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.body(Mono.just(exchangeRate), ExchangeRate.class)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void testCreateError() {
		ExchangeRate exchangeRate = new ExchangeRate();
		exchangeRate.setBuy(3.3);
		exchangeRate.setSell(1.2);

		var responseBody = webTestClient
				.post()
				.uri("/exchange")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.body(Mono.just(exchangeRate), ExchangeRate.class)
				.exchange()
				.expectStatus().isBadRequest();
	}


}
