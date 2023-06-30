package com.amigoscode.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.RequestValidationException;
import com.amigoscode.exception.ResourceNotFound;

@Service
public class CustomerService {
	
	private CustomerDao customerDao;
	
	public CustomerService( @Qualifier("jdbc") CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public List<Customer> getAllCustomers(){
		return customerDao.selectAllCustomer();
		
	}
	public Customer getCustomerById(Integer id){
		return customerDao.selectCustomerbyId(id)
				.orElseThrow(() -> new ResourceNotFound("Customer with id " + id + " not found"));
	}
	
	public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

		if(customerDao.existsPersonWithEmail(customerRegistrationRequest.email())){
			throw new DuplicateResourceException("Email Alredy Taken ");
		}
		Customer customer = new Customer(customerRegistrationRequest.name(),
										 customerRegistrationRequest.email(),
										 customerRegistrationRequest.age(),
										 customerRegistrationRequest.gender());
		customerDao.insertCustometr(customer);
	}

	public void deleteCustomerById(Integer id) {
		
		if(!customerDao.existsPersonWithId(id)) { 
			throw new ResourceNotFound("Customer with Id [%s] not found " .formatted(id));
		}
		customerDao.deleteCustomerById(id);
	}

	public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest) {
		 // TODO: for JPA use .getReferenceById(customerId) as it does does not bring object into memory and instead a reference
        Customer customer = getCustomerById(customerId);
        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsPersonWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
           throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);
	}
}
