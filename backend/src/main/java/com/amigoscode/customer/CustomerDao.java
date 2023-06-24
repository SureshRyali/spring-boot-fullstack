package com.amigoscode.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
	List<Customer> selectAllCustomer();
	Optional<Customer> selectCustomerbyId(Integer id);
	void insertCustometr(Customer customer);
	boolean existsPersonWithEmail(String email);
	boolean existsPersonWithId(Integer id);
	void deleteCustomerById(Integer id);
	void updateCustomer(Customer update);
}
