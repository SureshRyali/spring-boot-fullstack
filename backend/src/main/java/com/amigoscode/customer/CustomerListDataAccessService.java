package com.amigoscode.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository("list")
public  class CustomerListDataAccessService implements CustomerDao{

	
private static List<Customer> customers;
	
	static {
		customers = new ArrayList<>();
		Customer customer1 = new Customer(1, "suresh", "test@test.com", 32 , Gender.MALE);
		customers.add(customer1);
		Customer customer2 = new Customer(2, "baskar", "test2@test.com", 23 ,Gender.MALE);
		customers.add(customer2);
	}
	@Override
	public List<Customer> selectAllCustomer() {
		return customers;
		
	}
	@Override
	public Optional<Customer> selectCustomerbyId(Integer id) {
		return  customers
				.stream()
				.filter(customer -> customer.getId().equals(id)).findFirst();
		
	}
	@Override
	public void insertCustometr(Customer customer) {
		customers.add(customer);
		
	}
	@Override
	public boolean existsPersonWithEmail(String email) {
		
		return customers.stream()
				.anyMatch(customer -> customer.getEmail().equals(email));
	}
	@Override
	public boolean existsPersonWithId(Integer id) {
		return customers.stream()
				.anyMatch(customer -> customer.getEmail().equals(id));
	}
	@Override
	public void deleteCustomerById(Integer id) {

		 customers.stream()
				.filter(c -> c.getId().equals(id))
				.findFirst()
				.ifPresent(customers :: remove);;
	}
	@Override
	public void updateCustomer(Customer update) {
		
		customers.add(update);
	}

}
