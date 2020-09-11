package com.synergisticit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.Transaction;
import com.synergisticit.domain.TransactionType;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BankTransactionService;
import com.synergisticit.service.CustomerService;
import com.synergisticit.utilities.BankUtilities;

@RestController
public class RestControllerTransaction {

	private BankUtilities bankUtil;
	private BankTransactionService trxService;
	private CustomerService custService;
	private AccountService acctService;
	
	public RestControllerTransaction(BankUtilities bankUtil, 
										BankTransactionService trxService,
										CustomerService custService,
										AccountService acctService) 
	{
		this.bankUtil = bankUtil;
		this.trxService = trxService;
		this.custService = custService;
		this.acctService = acctService;
	}
	
	@GetMapping(value = "/api/transactions")
	public ResponseEntity<?> getTransactions() {
		
		List<BankTransaction> listOfBankTransactions = trxService.findAll();
		if (listOfBankTransactions.isEmpty()) {
			return new ResponseEntity<String>("no transactions found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BankTransaction>>(listOfBankTransactions, HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/transactions/{id}")
	public ResponseEntity<?> getTransactionsByAcct(@PathVariable("id") long id) {
		
		if (custService.existsById(id)) {
			List<BankTransaction> listOfCustomerTransactions = bankUtil.getCustomerTransactions(id);
			if (!listOfCustomerTransactions.isEmpty()) {
				return new ResponseEntity<List<BankTransaction>>(listOfCustomerTransactions, HttpStatus.OK);
			}
			return new ResponseEntity<String>("no transactions found", HttpStatus.NO_CONTENT); 
		}
		return new ResponseEntity<String>("customer id: " + id + " not found", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PostMapping(value = "/api/transactions/withdraw/{id}/{amount}/{comment}")
	public ResponseEntity<?> transactionWithdraw(@PathVariable("id") long id,
													@PathVariable("amount") double amount,
													@PathVariable("comment") String comment) 
	{
		if (acctService.existsById(id)) {
			if (amount > 0) {
				if (acctService.findById(id).getAccountCurrentBalance() - amount > 0) {
					Transaction transaction = new Transaction();
					transaction.setFromAccountId(id);
					transaction.setTransactionAmount(amount);
					transaction.setTrxType(TransactionType.WITHDRAW);
					transaction.setComments(comment);
					
					BankTransaction bt = bankUtil.buildBT(TransactionType.WITHDRAW, transaction);
					
					bankUtil.updateFromAccount(id, amount);
					
					return new ResponseEntity<BankTransaction>(trxService.save(bt), HttpStatus.ACCEPTED);
				}
				return new ResponseEntity<String>("amount requested will result in negative balance", HttpStatus.CONFLICT);
			}
			return new ResponseEntity<String>("amount must be greater than 0", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("account with id: " + id + " not found", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PostMapping(value = "/api/transactions/deposit/{id}/{amount}/{comment}")
	public ResponseEntity<?> transactionDeposit(@PathVariable("id") long id,
													@PathVariable("amount") double amount,
													@PathVariable("comment") String comment) 
	{
		if (acctService.existsById(id)) {
			if (amount > 0) {
				Transaction transaction = new Transaction();
				transaction.setFromAccountId(id);
				transaction.setTransactionAmount(amount);
				transaction.setTrxType(TransactionType.DEPOSIT);
				transaction.setComments(comment);
				
				BankTransaction bt = bankUtil.buildBT(TransactionType.DEPOSIT, transaction);
				
				bankUtil.updateToAccount(id, amount);
				
				return new ResponseEntity<BankTransaction>(trxService.save(bt), HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<String>("amount must be greater than 0", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("account with id: " + id + " not found", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PostMapping(value = "/api/transactions/transfer/{from}/{to}/{amount}/{comment}")
	public ResponseEntity<?> transactionDeposit(@PathVariable("from") long from,
													@PathVariable("to") long to,
													@PathVariable("amount") double amount,
													@PathVariable("comment") String comment) 
	{
		if (acctService.existsById(from)) {
			if (acctService.existsById(to)) {
				if (amount > 0) {
					if (acctService.findById(from).getAccountCurrentBalance() - amount > 0) {
						Transaction transaction = new Transaction();
						transaction.setFromAccountId(from);
						transaction.setToAccountId(to);
						transaction.setTransactionAmount(amount);
						transaction.setTrxType(TransactionType.TRANSFER);
						transaction.setComments(comment);
						
						BankTransaction bt = bankUtil.buildBT(TransactionType.TRANSFER, transaction);
						bankUtil.updateFromAccount(from, amount);
						bankUtil.updateToAccount(to, amount);
						
						return new ResponseEntity<BankTransaction>(trxService.save(bt), HttpStatus.ACCEPTED);
					}
					return new ResponseEntity<String>("amount requested will result in negative balance", HttpStatus.CONFLICT);
				}
				return new ResponseEntity<String>("amount must be greater than 0", HttpStatus.CONFLICT);
			}
			return new ResponseEntity<String>("account with id: " + to + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("account with id: " + from + " not found", HttpStatus.NOT_FOUND);
	}
}
