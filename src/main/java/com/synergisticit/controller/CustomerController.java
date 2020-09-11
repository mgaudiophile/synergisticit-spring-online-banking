package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Transaction;
import com.synergisticit.domain.User;
import com.synergisticit.utilities.BankUtilities;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	private BankUtilities bankUtil;

	@Autowired
	public CustomerController(BankUtilities bankUtil) {
		this.bankUtil = bankUtil;
	}

	@GetMapping
	public String admin(Model model) {

		bankUtil.buildCustomerModel(model);
		return "customer";
	}

	// ------------------------
	// --- MODEL ATTRIBUTES
	// ------------------------
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
	// ---
}
