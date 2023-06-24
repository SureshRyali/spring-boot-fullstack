package com.amigoscode.customer;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
	boolean existsCustomerByEmail(String email);

	boolean existsCustomerById(Integer id);
}
