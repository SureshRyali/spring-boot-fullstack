package com.amigoscode.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.amigoscode.AbstractTestcontainers;

public class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {
	private CustomerJDBCDataAccessService underTest;
	private CustomerRowMapper customerRowMapper = new CustomerRowMapper();

	@BeforeEach
	void setUp() {
		underTest = new CustomerJDBCDataAccessService(getJdbcTemplate(), customerRowMapper);
	}

	@Test
	void selectAllCustomer() {
		Customer customer = new Customer(FAKER.name().fullName(),
				FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(), 20);
		underTest.insertCustometr(customer);
		List<Customer> actual = underTest.selectAllCustomer();

		assertThat(actual).isNotEmpty();
	}

	void selectCustomerbyId() {
		Customer customer = new Customer(FAKER.name().fullName(),
				FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(), 20);
		underTest.insertCustometr(customer);
		Integer id = underTest.selectAllCustomer().stream().filter(c -> c.getEmail().equals(customer.getEmail()))
				.map(Customer::getId).findFirst().orElseThrow();
		Optional<Customer> actual = underTest.selectCustomerbyId(id);

		assertThat(actual).isPresent().hasValueSatisfying(c -> {
			assertThat(c.getId()).isEqualTo(id);
			assertThat(c.getName()).isEqualTo(customer.getName());
			assertThat(c.getEmail()).isEqualTo(customer.getEmail());
			assertThat(c.getAge()).isEqualTo(customer.getAge());
		});

	}

	@Test
	void willReturnEmptyWhenSelectCustomerById() {
		// Given
		int id = 0;

		// When
		var actual = underTest.selectCustomerbyId(id);

		// Then
		assertThat(actual).isEmpty();
	}

	void insertCustometr(Customer customer) {

	}
	@Test
	void existsPersonWithEmail() {
		String name = FAKER.name().fullName();
		String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
		Customer customer = new Customer(name,
				email, 20);
		underTest.insertCustometr(customer);
		boolean actual = underTest.existsPersonWithEmail(email);
		assertThat(actual).isTrue();
		
	}
	@Test
    void existsPersonWithIdWillReturnFalseWhenIdNotPresent() {
        // Given
        int id = -1;

        // When
        var actual = underTest.existsPersonWithId(id);

        // Then
        assertThat(actual).isFalse();
    }
	@Test
	void existsCustomerWithId() {
		  // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20);

        underTest.insertCustometr(customer);

        int id = underTest.selectAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        var actual = underTest.existsPersonWithId(id);

        // Then
        assertThat(actual).isTrue(); 
		
	}
	
	

	void deleteCustomerById(Integer id) {

	}

	void updateCustomer(Customer update) {

	}
}
