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
import com.synergisticit.validation.AdminCustomerValidator;

@Controller
public class AdminCustomerController {

	private BankUtilities bankUtil;
	private CustomerService custService;
	private AdminCustomerValidator custValid;
	
	@Autowired
	public AdminCustomerController(BankUtilities bankUtil, CustomerService custService, AdminCustomerValidator custValid) {
		this.bankUtil = bankUtil;
		this.custService = custService;
		this.custValid = custValid;
	}
	
	@InitBinder("customer")
	public void initAdminCustomerValidator(WebDataBinder binder) {
		binder.addValidators(custValid);
	}
	
	@PostMapping("/adminSaveCustomer")
	public String adminSaveCustomer(@Valid @ModelAttribute Customer customer, BindingResult br, Model model) {
		
		if (!br.hasErrors()) {
			custService.save(customer);
			model.addAttribute("customer", new Customer());
		} else {
			model.addAttribute("customer", customer);
		}
		
		bankUtil.buildAllLists(model);
		return "admin";
	}
	
	@RequestMapping("/adminUpdateCustomer")
	public String adminUpdateCustomer(Customer customer, @RequestParam long customerId, Model model) {
	
		if (custService.existsById(customerId)) {
			customer = custService.findById(customerId);
			model.addAttribute("customer", customer);
		} else {
			model.addAttribute("customer", new Customer());
		}
		
		bankUtil.buildAllLists(model);
		return "admin";
	}
	
	@RequestMapping("/adminDeleteCustomer")
	public String adminDeleteCustomer(Customer customer, @RequestParam long customerId, Model model) {
		
		custService.deleteById(customerId);
		model.addAttribute("customer", new Customer());
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
