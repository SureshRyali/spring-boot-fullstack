package com.amigoscode.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.ResourceNotFound;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	private CustomerDao customerDao;
	private CustomerService underTest;

	@BeforeEach
	void setUp() {
		underTest = new CustomerService(customerDao);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getAllCustomers() {
		underTest.getAllCustomers();
		verify(customerDao).selectAllCustomer();
	}

	@Test
	void canGetCustomerById() {
		Integer id = 10;
		Customer customer = new Customer(id, "Alex", "alex@gmail.com", 19 ,  Gender.MALE);
		when(customerDao.selectCustomerbyId(id)).thenReturn(Optional.of(customer));
		Customer actual = underTest.getCustomerById(10);
		assertThat(actual).isEqualTo(customer);
	}

	@Test
	void willThrowWhenGetCustomerReturnEmptyOptional() {
		Integer id = 10;
		when(customerDao.selectCustomerbyId(id)).thenReturn(Optional.empty());
		assertThatThrownBy(() -> {
			underTest.getCustomerById(id);
		}).isInstanceOf(ResourceNotFound.class).hasMessage("Customer with id " + id + " not found");
	}

	@Test
	void addCustomer() {
		String email = " test@test.com";
		CustomerRegistrationRequest request = new CustomerRegistrationRequest("suresh", email, 39 , Gender.MALE);
		when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
		underTest.addCustomer(request);
		ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
		verify(customerDao).insertCustometr(customerArgumentCaptor.capture());
		Customer captredCustomer = customerArgumentCaptor.getValue();
		assertThat(captredCustomer.getId()).isNull();
		assertThat(captredCustomer.getName()).isEqualTo(request.name());
		assertThat(captredCustomer.getEmail()).isEqualTo(request.email());
		assertThat(captredCustomer.getAge()).isEqualTo(request.age());
	}

	@Test
	void willThrowWhenEmailExistsWhileAddingACustomer() {
		String email = " test@test.com";
		CustomerRegistrationRequest request = new CustomerRegistrationRequest("suresh", email, 39 , Gender.MALE);

		when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

		assertThatThrownBy(() -> underTest.addCustomer(request)).isInstanceOfAny(DuplicateResourceException.class)
				.hasMessage("Email Alredy Taken ");
		verify(customerDao, never()).insertCustometr(any());
	}

	@Test
	void deleteCustomerById() {
		int id = 6;
		when(customerDao.existsPersonWithId(id)).thenReturn(true);
		underTest.deleteCustomerById(id);
		verify(customerDao).deleteCustomerById(id);

	}

	@Test
	void willThrowDeleteCustomerByIdNotExists() {
		int id = 6;
		when(customerDao.existsPersonWithId(id)).thenReturn(false);
		assertThatThrownBy(() -> underTest.deleteCustomerById(id))
				.isInstanceOf(ResourceNotFound.class)
				.hasMessage("Customer with Id [%s] not found " .formatted(id));
	
		verify(customerDao , never()).deleteCustomerById(id);
	}
	
//	@Test
	void updateCustomer() {
		Integer id = 10;
		Customer customer = new Customer(id, "Alex", "alex@gmail.com", 19 , Gender.MALE);
		when(customerDao.selectCustomerbyId(id)).thenReturn(Optional.of(customer));
		
	}
    @Test
    void canUpdateAllCustomersProperties() {
        // Given
        int id = 10;
        Customer customer = new Customer(id, "Alex", "alex@gmail.com",  19, Gender.MALE);
        when(customerDao.selectCustomerbyId(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@amigoscode.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", newEmail, 23);

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }
  
    @Test
    void canUpdateOnlyCustomerName() {
        // Given
        int id = 10;
        Customer customer = new Customer(id, "Alex", "alex@gmail.com",  19, Gender.MALE);
        when(customerDao.selectCustomerbyId(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", null, null);

        // When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }
    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        // Given
        int id = 10;
        Customer customer = new Customer(id, "Alex", "alex@gmail.com", 19, Gender.MALE);
        when(customerDao.selectCustomerbyId(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@amigoscode.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail, null);

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest)).isInstanceOf(DuplicateResourceException.class).hasMessage("email already taken");

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }
    
    
    
    
    
}




















