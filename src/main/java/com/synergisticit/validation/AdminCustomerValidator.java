package com.synergisticit.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Customer;
import com.synergisticit.service.CustomerService;
import com.synergisticit.service.UserService;

@Component
public class AdminCustomerValidator implements Validator {

	@Autowired
	CustomerService custService;
	
	@Autowired
	@Qualifier(value = "jpaUserService")
	UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Customer.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		Customer customer = (Customer) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerId", "customer.customerId.empty", "Customer Id is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "customer.customerName.empty", "Customer Name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.userId", "customer.user.userId.empty", "User Id is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerAddress.addressLine1", "customer.customerAddress.addressLine1.empty", "Address Line 1 is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerAddress.city", "customer.customerAddress.city.empty", "City is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerAddress.state", "customer.customerAddress.state.empty", "State is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerMobile", "customer.customerMobile.empty", "Mobile is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerEmail", "customer.customerEmail.empty", "Email is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerSSN", "customer.customerSSN.empty", "SSN is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerDob", "customer.customerDob.empty", "Dob is required.");
		
		if (customer.getUser().getUserId() != null && !userService.existsById(customer.getUser().getUserId())) {
			errors.rejectValue("user.userId", "does not exits", "User Id is not recognized. Please double check.");
		}
	}
}