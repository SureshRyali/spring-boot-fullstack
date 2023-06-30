package com.amigoscode.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import com.amigoscode.AbstractTestcontainers;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CustomerRepositoryTest  extends AbstractTestcontainers{
	
	@Autowired
	private CustomerRepository underTest;
	@Autowired
	private ApplicationContext applicationContext;
	
	@BeforeEach
	void setUp() {
		System.out.println("hi");
	}

	  @Test
	    void existsCustomerByEmail() {
	        // Given
	        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
	        Customer customer = new Customer(
	                FAKER.name().fullName(),
	                email,
	                20 );
	        customer.setGender(Gender.FEMALE);
	        underTest.save(customer);

	        // When
	        var actual = underTest.existsCustomerByEmail(email);

	        // Then
	        assertThat(actual).isTrue();
	    }

	    @Test
	    void existsCustomerByEmailFailsWhenEmailNotPresent() {
	        // Given
	        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

	        // When
	        var actual = underTest.existsCustomerByEmail(email);

	        // Then
	        assertThat(actual).isFalse();
	    }
}
