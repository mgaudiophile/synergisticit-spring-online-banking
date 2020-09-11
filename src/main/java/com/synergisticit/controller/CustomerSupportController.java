package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Transaction;
import com.synergisticit.domain.User;
import com.synergisticit.service.MailService;
import com.synergisticit.utilities.BankUtilities;

@Controller
public class CustomerSupportController {

	private BankUtilities bankUtil;
	private MailService mailService;

	@Autowired
	public CustomerSupportController(BankUtilities bankUtil, MailService mailService) {
		this.bankUtil = bankUtil;
		this.mailService = mailService;
	}
	
	@PostMapping("/support")
	public String support(@RequestParam("supportEmail") String email, 
							@RequestParam("supportSubject") String subject,
							@RequestParam("supportMessage") String message,
							Model model) {
		
		System.out.println(email + subject + message);
		mailService.sendEmail(email, subject, message);
		mailService.sendConfirmationEmail(email, subject);
		
		bankUtil.buildCustomerModel(model);
		return "customer";
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
