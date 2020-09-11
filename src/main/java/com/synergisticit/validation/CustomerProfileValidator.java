package com.synergisticit.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Customer;
import com.synergisticit.service.CustomerService;

@Component
public class CustomerProfileValidator implements Validator {

	@Autowired
	CustomerService custService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Customer.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		//Customer customer = (Customer) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "customer.customerName.empty", "Name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerAddress.addressLine1", "customer.customerAddress.addressLine1.empty", "Address Line 1 is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerAddress.addressLine2", "customer.customerAddress.addressLine2.empty", "Address Line 2 is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerAddress.city", "customer.customerAddress.city.empty", "City is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerAddress.state", "customer.customerAddress.state.empty", "State is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerMobile", "customer.customerMobile.empty", "Mobile is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPhone", "customer.customerPhone.empty", "Phone is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerEmail", "customer.customerEmail.empty", "Email is required.");
	}
}
