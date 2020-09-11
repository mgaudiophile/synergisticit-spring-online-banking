package com.synergisticit.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Transaction;
import com.synergisticit.service.AccountService;
import com.synergisticit.utilities.BankUtilities;

@Component
public class CustomerTransactionValidator implements Validator {

	@Autowired
	AccountService acctService;
	
	@Autowired
	BankUtilities bankUtil;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Transaction.class.equals(clazz);
	}
	
	//TODO: clean up this code
	@Override
	public void validate(Object target, Errors errors) {
		
		Transaction trx = (Transaction) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "transactionAmount", "transaction.transactionAmount.empty", "Amount is required.");
		if (trx.getTransactionAmount() <= 0) {
			errors.rejectValue("transactionAmount", "transaction.transactionAmount.empty", "Deposit must be greater than 0.");
		}
		
		List<Account> customerAcct = bankUtil.getCustomerAccounts();
		Set<Long> set = new HashSet<>();
		for (Account a : customerAcct) {
			set.add(a.getAccountId());
		}
		
		switch(trx.getTrxType()) {
		case WITHDRAW:
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromAccountId", "transaction.fromAccountId.empty", "Account Id is required.");
			if (trx.getFromAccountId() != null && !set.contains(trx.getFromAccountId())) {
				errors.rejectValue("fromAccountId", "transaction.fromAccountId.empty", "Account Id not recognized. Please double check your account number.");
			}
			if (trx.getFromAccountId() != null && acctService.existsById(trx.getFromAccountId()) && acctService.findById(trx.getFromAccountId()).getAccountCurrentBalance() - trx.getTransactionAmount() < 0) {
				errors.rejectValue("transactionAmount", "transaction.transactionAmount.empty", "Amount will result in negative balance.");
			}
			break;
		case DEPOSIT:
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toAccountId", "transaction.toAccountId.empty", "Account Id is required.");
			if (trx.getToAccountId() != null && !acctService.existsById(trx.getToAccountId() ) ) {
				errors.rejectValue("toAccountId", "transaction.toAccountId.empty", "Account Id does not exist.");
			}
			break;
		case TRANSFER:
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromAccountId", "transaction.fromAccountId.empty", "Account Id is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toAccountId", "transaction.toAccountId.empty", "Account Id is required.");
			if (trx.getFromAccountId() != null && !set.contains(trx.getFromAccountId())) {
				errors.rejectValue("fromAccountId", "transaction.fromAccountId.empty", "Account Id not recognized. Please double check your account number.");
			}
			if (trx.getToAccountId() != null && !acctService.existsById(trx.getToAccountId() ) ) {
				errors.rejectValue("toAccountId", "transaction.toAccountId.empty", "Account Id does not exist.");
			}
			if (trx.getFromAccountId() != null && acctService.existsById(trx.getFromAccountId()) && acctService.findById(trx.getFromAccountId()).getAccountCurrentBalance() - trx.getTransactionAmount() < 0) {
				errors.rejectValue("transactionAmount", "transaction.transactionAmount.empty", "Amount will result in negative balance.");
			}
			break;
			default:
				break;
		}
	}
}
