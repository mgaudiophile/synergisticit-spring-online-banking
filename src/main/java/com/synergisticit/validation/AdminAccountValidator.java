package com.synergisticit.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Account;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.service.CustomerService;

@Component
public class AdminAccountValidator implements Validator {

	@Autowired
	AccountService acctService;
	
	@Autowired
	BranchService branchService;
	
	@Autowired
	CustomerService custService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Account.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		Account account = (Account) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountId", "account.accountId.empty", "Account Id is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountBranch.branchId", "account.accountBranch.branchId.empty", "Branch Id is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountCustomer.customerId", "account.accountCustomer.customerId.empty", "Customer Id is required.");
		
		if (account.getAccountBranch().getBranchId() != null && !branchService.existsById(account.getAccountBranch().getBranchId())) {
			errors.rejectValue("accountBranch.branchId", "accountBranchId", "Branch Id is not recognized. Please double check.");
		}
		
		if (account.getAccountCustomer().getCustomerId() != null && !custService.existsById(account.getAccountCustomer().getCustomerId())) {
			errors.rejectValue("accountCustomer.customerId", "accountCustomer", "Customer Id is not recognized. Please double check.");
		}
	}
}
