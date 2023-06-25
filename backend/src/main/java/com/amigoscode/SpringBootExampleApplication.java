package com.amigoscode;

import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

@SpringBootApplication
public class SpringBootExampleApplication {
	
	public static void main(String[] args) {
		 SpringApplication.run(SpringBootExampleApplication.class, args);
	}
	
	
	@Bean
	CommandLineRunner runner(CustomerRepository repository) {
		
		return atgs -> {
			Random random = new Random();
		
			Faker faker = new Faker();
			Name name = faker.name();
			String firstName = name.firstName();
			String lastName = name.lastName();
			
			Customer customer = new Customer( firstName + " " + lastName,
					 firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com" ,
					random.nextInt(16, 85));
			
			
			
		//	repository.save(customer);
		};
	}
}

//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
