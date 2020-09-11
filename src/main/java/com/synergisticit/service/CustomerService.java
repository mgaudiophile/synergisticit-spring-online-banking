package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Customer;

public interface CustomerService {

	Customer save(Customer customer);
	
	List<Customer> findAll();
	Customer findById(long id);
	
	boolean existsById(long id);
	
	Customer update(Customer customer);
	Customer updateById(long id);
	
	void delete(Customer customer);
	void deleteById(long id);
	
	Customer findByUserId(long userId);
}
