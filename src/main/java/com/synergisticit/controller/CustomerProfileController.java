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
import com.synergisticit.service.CustomerService;
import com.synergisticit.utilities.BankUtilities;
import com.synergisticit.validation.CustomerProfileValidator;

@Controller
public class CustomerProfileController {

	private BankUtilities bankUtil;
	private CustomerService custService;
	private CustomerProfileValidator custProfValid;

	@Autowired
	public CustomerProfileController(BankUtilities bankUtil, CustomerService custService,
			CustomerProfileValidator custProfValid) {
		this.bankUtil = bankUtil;
		this.custService = custService;
		this.custProfValid = custProfValid;
	}

	@InitBinder("customer")
	public void initCustProfileValidBinder(WebDataBinder binder) {
		binder.addValidators(custProfValid);
	}

	@PostMapping("/customerSaveProfile")
	public String customerSaveProfile(@Valid @ModelAttribute Customer customer, BindingResult br, Model model) {

		if (!br.hasErrors()) {
			bankUtil.updateCustomerProfile(customer);
			model.addAttribute("customer", new Customer());
		} else {
			model.addAttribute("customer", customer);
		}

		bankUtil.buildCustomerModel(model);
		return "customer";
	}

	@RequestMapping("/customerUpdateProfile")
	public String customerUpdateProfile(Customer customer, @RequestParam long customerId, Model model) {

		if (custService.existsById(customerId)) {
			customer = custService.findById(customerId);
			model.addAttribute("customer", customer);
		} else {
			model.addAttribute("customer", new Customer());
		}

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
