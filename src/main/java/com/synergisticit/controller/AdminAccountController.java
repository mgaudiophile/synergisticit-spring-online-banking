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
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Transaction;
import com.synergisticit.domain.User;
import com.synergisticit.service.AccountService;
import com.synergisticit.utilities.BankUtilities;
import com.synergisticit.validation.AdminAccountValidator;

@Controller
public class AdminAccountController {

	private BankUtilities bankUtil;
	private AccountService acctService;
	private AdminAccountValidator acctValid;
	
	@Autowired
	public AdminAccountController(BankUtilities bankUtil, AccountService acctService, AdminAccountValidator acctValid) {
		this.bankUtil = bankUtil;
		this.acctService = acctService;
		this.acctValid = acctValid;
	}
	
	@InitBinder("account")
	public void initAdminAccountValidator(WebDataBinder binder) {
		binder.addValidators(acctValid);
	}
	
	@PostMapping("/adminSaveAccount")
	public String adminSaveAccount(@Valid @ModelAttribute Account account, BindingResult br, Model model) {
		
		if (!br.hasErrors()) {
			acctService.save(account);
			model.addAttribute("account", new Account());
		} else {
			model.addAttribute("account", account);
		}
		
		bankUtil.buildAllLists(model);
		return "admin";
	}
	
	@RequestMapping("/adminUpdateAccount")
	public String adminUpdateAccount(Account account, @RequestParam long accountId, Model model) {
		
		if (acctService.existsById(accountId)) {
			account = acctService.findById(accountId);
			model.addAttribute("account", account);
		} else {
			model.addAttribute("account", new Account());
		}
		
		bankUtil.buildAllLists(model);
		return "admin";
	}
	
	@RequestMapping("/adminDeleteAccount")
	public String adminDeleteAccount(Account account, @RequestParam long accountId, Model model) {
		
		acctService.deleteById(accountId);
		model.addAttribute("account", new Account());
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
