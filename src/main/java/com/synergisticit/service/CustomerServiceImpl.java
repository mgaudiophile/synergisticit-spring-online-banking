package com.synergisticit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Customer;
import com.synergisticit.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository custRepo;
	
	@Override
	public Customer save(Customer customer) {
		
		return custRepo.save(customer);
	}

	@Override
	public List<Customer> findAll() {
		
		return custRepo.findAll();
	}

	@Override
	public Customer findById(long id) {
		
		Optional<Customer> opt = custRepo.findById(id);
		if (opt.isPresent())
			return opt.get();
		
		return null;
	}
	
	@Override
	public boolean existsById(long id) {
		
		return custRepo.existsById(id);
	}

	@Override
	public Customer update(Customer customer) {
		
		Customer custDB = findById(customer.getCustomerId());
		if (custDB != null) {
			custDB.setCustomerName(customer.getCustomerName());
			custDB.setCustomerGender(customer.getCustomerGender());
			custDB.setCustomerDob(customer.getCustomerDob());
			custDB.setCustomerMobile(customer.getCustomerMobile());
			custDB.setCustomerPhone(customer.getCustomerPhone());
			custDB.setCustomerEmail(customer.getCustomerEmail());
			custDB.setCustomerAddress(customer.getCustomerAddress());
			custDB.setCustomerSSN(customer.getCustomerSSN());
		}
		return custDB;
	}

	@Override
	public Customer updateById(long id) {
		
		Optional<Customer> opt = custRepo.findById(id);
		if (opt.isPresent())
			return opt.get();
		
		return null;
	}

	@Override
	public void delete(Customer customer) {
		
		custRepo.delete(customer);
	}

	@Override
	public void deleteById(long id) {
		
		custRepo.deleteById(id);
	}

	@Override
	public Customer findByUserId(long userId) {
		
		return custRepo.findByUserId(userId);
	}

}
