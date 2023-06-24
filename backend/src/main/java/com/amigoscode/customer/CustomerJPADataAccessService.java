package com.amigoscode.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDao{
	private final CustomerRepository customerRepository;
	
	
	public CustomerJPADataAccessService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public List<Customer> selectAllCustomer() {
		return (List<Customer>) customerRepository.findAll();
	}

	
	@Override
	public Optional<Customer> selectCustomerbyId(Integer id) {
		return customerRepository.findById(id);
	}

	@Override
	public void insertCustometr(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public boolean existsPersonWithEmail(String email) {
		return customerRepository.existsCustomerByEmail(email);
	}

	@Override
	public boolean existsPersonWithId(Integer id) {
		// TODO Auto-generated method stub
		return customerRepository.existsCustomerById(id);
	}

	@Override
	public void deleteCustomerById(Integer id) {
		customerRepository.deleteById(id);
	}

	@Override
	public void updateCustomer(Customer update) {
		customerRepository.save(update);
		
	}

}
