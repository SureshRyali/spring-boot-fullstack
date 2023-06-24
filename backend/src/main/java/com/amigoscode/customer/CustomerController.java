package com.amigoscode.customer;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amigoscode.exception.RequestValidationException;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	// @RequestMapping(path = "/api/v1/customers" , method = RequestMethod.GET )
	@GetMapping
	public List<Customer> getCustomers() {

		return customerService.getAllCustomers();
	}

	@GetMapping("{id}")
	public Customer getCustomer(@PathVariable("id") Integer id) {

		return customerService.getCustomerById(id);
	}
	@PostMapping
	public void registerCustomer(@RequestBody  CustomerRegistrationRequest request) {
		customerService.addCustomer(request);
	}
	@DeleteMapping("{id}")
	public void deleteCustomer (@PathVariable("id") Integer id) {
		customerService.deleteCustomerById(id);
	}
	
	@PutMapping("{id}")
	public void updateCustomer (@PathVariable("id") Integer id , @RequestBody CustomerUpdateRequest customerUpdateReqest ) throws RequestValidationException {
		
		customerService.updateCustomer(id , customerUpdateReqest);
	}
}


















