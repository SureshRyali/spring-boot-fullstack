package com.amigoscode.customer;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerJPADataAccessServiceTest {

	private CustomerJPADataAccessService underTest;
	@Mock
	private CustomerRepository customerRepository;
	private AutoCloseable autoCloseable;
	
	@BeforeEach
	void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		underTest = new CustomerJPADataAccessService(customerRepository);
	}

	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}
	@Test
	void selectAllCustomers() {
		underTest.selectAllCustomer();
		verify(customerRepository).findAll();
	}
	@Test
	void selectCustomerbyId(){
		int id = 1;
		underTest.selectCustomerbyId(id);
		verify(customerRepository).findById(id);
	}
	@Test
	void insertCustometr() {
		   Customer customer = new Customer(
	                1, "Ali", "ali@gmail.com" , 2 , Gender.MALE);

	        // When
	        underTest.insertCustometr(customer);

	        // Then
	        verify(customerRepository).save(customer);
	}
	@Test
	void existsPersonWithEmail() {
		  String email = "foo@gmail.com";
		  underTest.existsPersonWithEmail(email);
		  verify(customerRepository).existsCustomerByEmail(email);
		
	}
	@Test
	void existsPersonWithId() {
		 int id = 1;
		 underTest.existsPersonWithId(id);
		 verify(customerRepository).existsCustomerById(id);
	}
	@Test
	void deleteCustomerById() {
		 int id = 5;
		 underTest.deleteCustomerById(id);
		 verify(customerRepository).deleteById(id);
	}
	@Test
	void updateCustomer() {
		  Customer customer = new Customer(
	                1, "Ali", "ali@gmail.com",  2 , Gender.MALE);

	        // When
	        underTest.updateCustomer(customer);

	        // Then
	        verify(customerRepository).save(customer);	
	}
}
