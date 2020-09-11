package com.synergisticit.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.service.CustomerService;
import com.synergisticit.utilities.BankUtilities;
import com.synergisticit.validation.AdminAccountValidator;

@RestController
public class RestControllerAccount {

	private BankUtilities bankUtil;
	private AccountService acctService;
	private BranchService branchService;
	private CustomerService custService;
	private AdminAccountValidator acctValid;
	
	@Autowired
	public RestControllerAccount(BankUtilities bankUtil, 
									AccountService acctService, 
									BranchService branchService, 
									CustomerService custService, 
									AdminAccountValidator acctValid) 
	{
		this.bankUtil = bankUtil;
		this.acctService = acctService;
		this.branchService = branchService;
		this.custService = custService;
		this.acctValid = acctValid;
	}
	
	@InitBinder
	public void initAccountValidator(WebDataBinder binder) {
		binder.addValidators(acctValid);
	}
	
	@GetMapping(value = "/api/accounts")
	public ResponseEntity<?> getAccounts() {
		
		List<Account> listOfAccounts = acctService.findAll();
		
		if (listOfAccounts.isEmpty()) {
			return new ResponseEntity<String>("no accounts found", HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Account>>(listOfAccounts, HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/accounts/{id}")
	public ResponseEntity<?> getAccountById(@PathVariable("id") long id) {
		
		if (acctService.existsById(id)) {
			return new ResponseEntity<Account>(acctService.findById(id), HttpStatus.OK);
		}
		return new ResponseEntity<String>("account with id: " + id + " not found", HttpStatus.NOT_FOUND); 
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PostMapping(value = "/api/accounts", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveAccount(@Valid @RequestBody Account account, BindingResult br) {
		
		if (!br.hasErrors()) {
			if (!acctService.existsById(account.getAccountId())) {
				
				return new ResponseEntity<Account>(acctService.save(account), HttpStatus.CREATED);
			}
			return new ResponseEntity<String>("account id: " + account.getAccountId() + " already exists", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(bankUtil.getValidationErrors(br), HttpStatus.NOT_ACCEPTABLE); 
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PutMapping(value = "/api/accounts", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateAccount(@Valid @RequestBody Account account, BindingResult br) {
		
		if (acctService.existsById(account.getAccountId())) {
			if (!br.hasErrors()) {
				
				Account acct = acctService.findById(account.getAccountId());
				acct.setAccountType(account.getAccountType());
				
				Branch bDb = branchService.findById(account.getAccountBranch().getBranchId());

				acct.setAccountBranch(bDb);
				
				Customer cDb = custService.findById(account.getAccountCustomer().getCustomerId());
				
				acct.setAccountCustomer(cDb);
				
				acct.setAccountHolder(account.getAccountHolder());
				acct.setAccountCurrentBalance(account.getAccountCurrentBalance());
				
				if (account.getAccountDateOpened() == null)
					acct.setAccountDateOpened(LocalDate.now());
				else
					acct.setAccountDateOpened(account.getAccountDateOpened());
				
				return new ResponseEntity<Account>(acctService.save(acct), HttpStatus.OK); 
			}
			return new ResponseEntity<String>(bankUtil.getValidationErrors(br), HttpStatus.NOT_ACCEPTABLE); 
		}
		return new ResponseEntity<String>("account with id: " + account.getAccountId() + " not found", HttpStatus.NOT_FOUND);  
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@DeleteMapping(value = "/api/accounts/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable("id") long id) {
		
		if (acctService.existsById(id)) {
			acctService.deleteById(id);
			return new ResponseEntity<String>("account with id: " + id + " was deleted", HttpStatus.OK);  
		}
		return new ResponseEntity<String>("account with id: " + id + " not found", HttpStatus.NOT_FOUND);   
	}
}
