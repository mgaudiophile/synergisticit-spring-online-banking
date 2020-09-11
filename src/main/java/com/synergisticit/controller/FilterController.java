package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Transaction;
import com.synergisticit.domain.User;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BankTransactionService;
import com.synergisticit.utilities.BankUtilities;

@Controller
public class FilterController {

	private BankUtilities bankUtil;
	private BankTransactionService trxService;
	private AccountService acctService;
	
	@Autowired
	public FilterController(BankUtilities bankUtil, BankTransactionService trxService, AccountService acctService) {
		this.bankUtil = bankUtil;
		this.trxService = trxService;
		this.acctService = acctService;
	}
	
	@RequestMapping("/filterTrxByAcct")
	public String filterTrxByAcctId(@RequestParam Long filterAcctId, @RequestParam String action, Model model) {
		
		bankUtil.buildAllLists(model);
		
		if (action.equals("filter") && filterAcctId != null && acctService.existsById(filterAcctId)) {
			model.addAttribute("listOfTransactions", trxService.findByAcctId(filterAcctId));
			model.addAttribute("filtAcct", filterAcctId);
			
		} else if (action.equals("clear")){
			bankUtil.buildAllLists(model);
			model.addAttribute("filtAcct", null);
		}
		
		return "admin";
	}
	
	
	
	@ModelAttribute
	public Transaction transaction() {
		return new Transaction();
	}
	
	@ModelAttribute
	public Customer customer() {
		return new Customer();
	}
	
	@ModelAttribute
	public Account account() {
		return new Account();
	}
	
	@ModelAttribute
	public Branch branch() {
		return new Branch();
	}
	
	@ModelAttribute
	public User user() {
		return new User();
	}
}
