package com.synergisticit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Transaction;
import com.synergisticit.domain.TransactionType;
import com.synergisticit.domain.User;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BankTransactionService;
import com.synergisticit.utilities.BankUtilities;
import com.synergisticit.validation.AdminTransactionValidator;

@Controller
public class AdminTransactionController {

	private BankUtilities bankUtil;
	private BankTransactionService trxService;
	private AccountService acctService;
	private AdminTransactionValidator adminTrxValidator;
	
	@Autowired
	public AdminTransactionController(BankUtilities bankUtil, 
										BankTransactionService trxService,
										AccountService acctService,
										AdminTransactionValidator adminTrxValidator) {
		this.bankUtil = bankUtil;
		this.trxService = trxService;
		this.acctService = acctService;
		this.adminTrxValidator = adminTrxValidator;
	}
	
	@InitBinder("transaction")
	public void initAdminTrxValidBinder(WebDataBinder binder) {
		binder.addValidators(adminTrxValidator);
	}

	@PostMapping("/adminWithdraw")
	public String withdraw(@Valid @ModelAttribute Transaction transaction, BindingResult br, Model model) {
		
		if (!br.hasErrors()) {
			BankTransaction bt = bankUtil.buildBT(TransactionType.WITHDRAW, transaction);
			trxService.save(bt);
			bankUtil.updateFromAccount(transaction.getFromAccountId(), transaction.getTransactionAmount());
			
			model.addAttribute("withdraw", acctService.findById(transaction.getFromAccountId()));
			model.addAttribute("transaction", new Transaction());
			
		} else {
			model.addAttribute("transaction", transaction);
			model.addAttribute("withdraw", new Account());
		}
		
		bankUtil.buildAllLists(model);
		return "admin";
	}
	
	@RequestMapping("/adminDeposit")
	public String deposit(@Valid @ModelAttribute Transaction transaction, BindingResult br, Model model) {
		
		if (!br.hasErrors()) {
			BankTransaction bt = bankUtil.buildBT(TransactionType.DEPOSIT, transaction);
			trxService.save(bt);
			bankUtil.updateToAccount(transaction.getToAccountId(), transaction.getTransactionAmount());
			
			model.addAttribute("deposit", acctService.findById(transaction.getToAccountId()));
			model.addAttribute("transaction", new Transaction());
			
		} else {
			model.addAttribute("transaction", transaction);
			model.addAttribute("deposit", new Account());
		}
		
		bankUtil.buildAllLists(model);
		return "admin";
	}
	
	@RequestMapping("/adminTransfer")
	public String transfer(@Valid @ModelAttribute Transaction transaction, BindingResult br, Model model) {
		
		if (!br.hasErrors()) {
			BankTransaction bt = bankUtil.buildBT(TransactionType.TRANSFER, transaction);
			trxService.save(bt);
			bankUtil.updateFromAccount(transaction.getFromAccountId(), transaction.getTransactionAmount());
			bankUtil.updateToAccount(transaction.getToAccountId(), transaction.getTransactionAmount());
			
			model.addAttribute("transferfrom", acctService.findById(transaction.getFromAccountId()));
			model.addAttribute("transferto", acctService.findById(transaction.getToAccountId()));
			model.addAttribute("transaction", new Transaction());
		} else {
			model.addAttribute("transaction", transaction);
			model.addAttribute("transferfrom", new Account());
			model.addAttribute("transferto", new Account());
		}
		
		bankUtil.buildAllLists(model);
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
