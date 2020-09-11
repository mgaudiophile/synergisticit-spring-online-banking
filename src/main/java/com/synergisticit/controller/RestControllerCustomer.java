package com.synergisticit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.domain.Address;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.User;
import com.synergisticit.service.CustomerService;
import com.synergisticit.service.UserService;
import com.synergisticit.utilities.BankUtilities;
import com.synergisticit.validation.AdminCustomerValidator;

@RestController
public class RestControllerCustomer {

	private BankUtilities bankUtil;
	private CustomerService custService;
	private UserService userService;
	private AdminCustomerValidator custValid;
	
	@Autowired
	public RestControllerCustomer(BankUtilities bankUtil, 
									CustomerService custService, 
									@Qualifier(value = "jpaUserService") UserService userService, 
									AdminCustomerValidator custValid) 
	{
		this.bankUtil = bankUtil;
		this.custService = custService;
		this.userService = userService;
		this.custValid = custValid;
	}
	
	@InitBinder
	public void initValidCustomerAdmin(WebDataBinder binder) {
		binder.addValidators(custValid);
	}
	
	@GetMapping(value = "/api/customers")
	public ResponseEntity<?> getCustomers() {
		
		List<Customer> listOfCustomers = custService.findAll();
		if (listOfCustomers.isEmpty()) {
			return new ResponseEntity<String>("no customers found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(listOfCustomers, HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/customers/{id}")
	public ResponseEntity<?> getCustomer(@PathVariable("id") long id) {
		
		if (custService.existsById(id)) {
			return new ResponseEntity<Customer>(custService.findById(id), HttpStatus.OK);
		}
		return new ResponseEntity<String>("customer with id: " + id + " not found", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PostMapping(value = "/api/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveCustomer(@Valid @RequestBody Customer customer, BindingResult br) {
		
		if (!br.hasErrors()) {
			if (!custService.existsById(customer.getCustomerId())) {
				
				return new ResponseEntity<Customer>(custService.save(customer), HttpStatus.CREATED);
			}
			return new ResponseEntity<String>("customer with id: " + customer.getCustomerId() + " already exists", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(bankUtil.getValidationErrors(br), HttpStatus.CONFLICT);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PutMapping(value = "/api/customers", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer customer, BindingResult br) {
		
		if (!br.hasErrors()) {
			if (custService.existsById(customer.getCustomerId())) {
				
				Customer cust = custService.findById(customer.getCustomerId());
				cust.setCustomerName(customer.getCustomerName());
				
				User uDb = userService.findById(customer.getUser().getUserId());
				
				cust.setUser(uDb);
				
				Address addr = new Address(customer.getCustomerAddress().getAddressLine1(),
											customer.getCustomerAddress().getAddressLine2(),
											customer.getCustomerAddress().getCity(),
											customer.getCustomerAddress().getState());
				
				cust.setCustomerAddress(addr);
				cust.setCustomerMobile(customer.getCustomerMobile());
				cust.setCustomerPhone(customer.getCustomerPhone());
				cust.setCustomerEmail(customer.getCustomerEmail());
				cust.setCustomerSSN(customer.getCustomerSSN());
				cust.setCustomerGender(customer.getCustomerGender());
				cust.setCustomerDob(customer.getCustomerDob());
				
				return new ResponseEntity<Customer>(custService.save(cust), HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<String>("customer with id: " + customer.getCustomerId() + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(bankUtil.getValidationErrors(br), HttpStatus.CONFLICT);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@DeleteMapping(value = "/api/customers/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
		
		if (custService.existsById(id)) {
			custService.deleteById(id);
			return new ResponseEntity<String>("customer with id: " + id + " was deleted", HttpStatus.OK);
		}
		return new ResponseEntity<String>("customer with id: " + id + " not found", HttpStatus.NOT_FOUND);
	}
}
