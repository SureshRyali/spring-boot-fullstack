package com.amigoscode.journey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRegistrationRequest;
import com.amigoscode.customer.CustomerUpdateRequest;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerIT {

	@Autowired
	private WebTestClient webTestClient;
	private final static Random RANDOM = new Random();
	private static final String CUSTOMER_URI = "/api/v1/customers";

	@Test
	void canRegisterACustomer() {
		Faker faker = new Faker();
		Name fakerName = faker.name();
		String name = fakerName.fullName();
		String email = name + UUID.randomUUID() + "@amigoscode.com";
		int age = RANDOM.nextInt(15, 95);

		CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest(name, email, age);

		webTestClient.post()
			.uri(CUSTOMER_URI)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(registrationRequest), CustomerRegistrationRequest.class)
			.exchange()
			.expectStatus()
			.isOk();
		// get all customers
		List<Customer> allCustomers = webTestClient
				.get()
				.uri(CUSTOMER_URI)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(new ParameterizedTypeReference<Customer>() {})
				.returnResult()
				.getResponseBody();
	
		Customer expectedCustomer = new Customer(name, email, age);
		
		int id  = allCustomers.stream()
							  .filter(customer -> customer.getEmail().equals(email))
							  .map(Customer::getId)
							  .findFirst()
							  .orElseThrow(); 
	
		
		assertThat(allCustomers)
						.usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
						.contains(expectedCustomer);
		
		expectedCustomer.setId(id);
		webTestClient
			.get()
			.uri(CUSTOMER_URI+"/{id}" , id)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(new ParameterizedTypeReference<Customer>() {})
			.isEqualTo(expectedCustomer);
	}
	@Test
    void canDeleteCustomer() {

		Faker faker = new Faker();
		Name fakerName = faker.name();
		String name = fakerName.fullName();
		String email = name + UUID.randomUUID() + "@amigoscode.com";
		int age = RANDOM.nextInt(15, 95);

		CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest(name, email, age);

		webTestClient.post()
			.uri(CUSTOMER_URI)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(registrationRequest), CustomerRegistrationRequest.class)
			.exchange()
			.expectStatus()
			.isOk();
		// get all customers
		List<Customer> allCustomers = webTestClient
				.get()
				.uri(CUSTOMER_URI)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(new ParameterizedTypeReference<Customer>() {})
				.returnResult()
				.getResponseBody();
	
		
		int id  = allCustomers.stream()
							  .filter(customer -> customer.getEmail().equals(email))
							  .map(Customer::getId)
							  .findFirst()
							  .orElseThrow(); 
	
		
		webTestClient.delete()
					 .uri(CUSTOMER_URI+"/{id}" , id)
					 .accept(MediaType.APPLICATION_JSON)
					 .exchange()
					 .expectStatus()
					 .isOk();
		webTestClient
			.get()
			.uri(CUSTOMER_URI+"/{id}" , id)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.isNotFound();
	}
	@Test
    void canUpdateCustomer() {

		Faker faker = new Faker();
		Name fakerName = faker.name();
		String name = fakerName.fullName();
		String email = name + UUID.randomUUID() + "@amigoscode.com";
		int age = RANDOM.nextInt(15, 95);

		CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest(name, email, age);

		webTestClient.post()
			.uri(CUSTOMER_URI)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(registrationRequest), CustomerRegistrationRequest.class)
			.exchange()
			.expectStatus()
			.isOk();
		// get all customers
		List<Customer> allCustomers = webTestClient
				.get()
				.uri(CUSTOMER_URI)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(new ParameterizedTypeReference<Customer>() {})
				.returnResult()
				.getResponseBody();
	
		
		int id  = allCustomers.stream()
							  .filter(customer -> customer.getEmail().equals(email))
							  .map(Customer::getId)
							  .findFirst()
							  .orElseThrow(); 
	
		String someName = "some name";
		CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(someName , null, null);
		webTestClient.put()
					 .uri(CUSTOMER_URI+"/{id}" , id)
					 .accept(MediaType.APPLICATION_JSON)
					 .contentType(MediaType.APPLICATION_JSON)
					 .body(Mono.just(updateRequest) , CustomerUpdateRequest.class)
					 .exchange()
					 .expectStatus() 
					 .isOk();
		Customer updatedCustomer = webTestClient
					.get()
					.uri(CUSTOMER_URI+"/{id}" , id)
					.accept(MediaType.APPLICATION_JSON)
					.exchange()
					.expectStatus()
					.isOk()
					.expectBody(Customer.class)
					.returnResult()
					.getResponseBody();
		
		 Customer expected = new Customer(id, someName, email, age);
		assertThat(updatedCustomer).isEqualTo(expected );
			
	
	}
}
