package com.synergisticit.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Branch;
import com.synergisticit.service.BranchService;

@Controller
public class AdminBranchValidator implements Validator {

	@Autowired
	BranchService branchService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Branch.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//Branch branch = (Branch) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "branchId", "branch.branchId.empty", "Branch Id is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "branchName", "branch.branchName.empty", "Branch Name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "branchAddress.addressLine1", "branch.branchAddress.addressLine1.empty", "Branch Address Line 1 is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "branchAddress.addressLine2", "branch.branchAddress.addressLine2.empty", "Branch Address Line 2 is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "branchAddress.city", "branch.branchAddress.city.empty", "Branch City is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "branchAddress.state", "branch.branchAddress.state.empty", "Branch State is required.");
	}
}