package com.amigoscode.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao{
	
	private final JdbcTemplate jdbcTemplate;
	private final CustomerRowMapper customerRowMapper;
	
	
	public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate , CustomerRowMapper customerRowMapper) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.customerRowMapper = customerRowMapper;
	}

	@Override
	public List<Customer> selectAllCustomer() {
		var sql = "SELECT ID , NAME , EMAIL , AGE FROM CUSTOMER";
		return jdbcTemplate.query(sql , customerRowMapper);
	}

	@Override
	public Optional<Customer> selectCustomerbyId(Integer id) {
		var sql = "SELECT ID , NAME , EMAIL , AGE FROM CUSTOMER WHERE ID = ?";
		return jdbcTemplate.query(sql , customerRowMapper , id).stream().findFirst();
	}

	@Override
	public void insertCustometr(Customer customer) {
		String sql = "INSERT INTO customer (name , email , age) VALUES (? , ? , ?)";
		int result = jdbcTemplate.update(sql , customer.getName() , customer.getEmail() , customer.getAge());
		System.out.println("jdbcTemplate.update() = " + result);
	}

	@Override
	public boolean existsPersonWithEmail(String email) {
		 var sql = "SELECT count(id) FROM customer  WHERE email = ?";
		 Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
		
		 return count != null && count > 0;
		 
	}

	@Override
	public boolean existsPersonWithId(Integer id) {
		
		var sql = "SELECT count(id) FROM customer  WHERE id = ?";
		 Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
		
		 return count != null && count > 0;
	}

	@Override
	public void deleteCustomerById(Integer id) {
		var sql = "DELETE FROM customer WHERE id = ?";
		int result = jdbcTemplate.update(sql, id);
		System.out.println("deleteCustomerById() ----- > " + result);
	}

	@Override
	public void updateCustomer(Customer update) {
		  if (update.getName() != null) {
	            String sql = "UPDATE customer SET name = ? WHERE id = ?";
	            int result = jdbcTemplate.update(
	                    sql,
	                    update.getName(),
	                    update.getId()
	            );
	            System.out.println("update customer name result = " + result);
	        }
	        if (update.getAge() != null) {
	            String sql = "UPDATE customer SET age = ? WHERE id = ?";
	            int result = jdbcTemplate.update(
	                    sql,
	                    update.getAge(),
	                    update.getId()
	            );
	            System.out.println("update customer age result = " + result);
	        }
	        if (update.getEmail() != null) {
	            String sql = "UPDATE customer SET email = ? WHERE id = ?";
	            int result = jdbcTemplate.update(
	                    sql,
	                    update.getEmail(),
	                    update.getId());
	            System.out.println("update customer email result = " + result);
	        }	
	}

}
