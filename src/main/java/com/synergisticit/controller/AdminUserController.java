package com.synergisticit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.synergisticit.service.UserService;
import com.synergisticit.utilities.BankUtilities;
import com.synergisticit.validation.AdminUserValidator;

@Controller
public class AdminUserController {

	private BankUtilities bankUtil;
	private UserService userService;
	private AdminUserValidator adminUserValid;
	
	@Autowired
	public AdminUserController(BankUtilities bankUtil, @Qualifier(value = "jpaUserService") UserService userService, AdminUserValidator adminUserValid) {
		this.bankUtil = bankUtil;
		this.userService = userService;
		this.adminUserValid = adminUserValid;
	}

	@InitBinder("user")
	public void initAdminUserValidatorBinder(WebDataBinder binder) {
		binder.addValidators(adminUserValid);
	}
	
	@PostMapping("/adminSaveUser")
	public String adminSaveUser(@Valid @ModelAttribute User user, BindingResult br, Model model) {
		
		if (!br.hasErrors()) {
			userService.save(user);
			model.addAttribute("user", new User());
		} else {
			model.addAttribute("user", user);
		}
		
		bankUtil.buildAllLists(model);
		return "admin";
	}
	
	@RequestMapping("/adminUpdateUser")
	public String adminUpdateUser(User user, @RequestParam long userId, Model model) {
		
		if (userService.existsById(userId)) {
			user = userService.findById(userId);
			model.addAttribute("selectedRoles", user.getRoles());
			model.addAttribute("user", user);
		} else {
			model.addAttribute("user", new User());
		}
		
		bankUtil.buildAllLists(model);
		return "admin";
	}
	
	@RequestMapping("/adminDeleteUser")
	public String adminDeleteUser(User user, @RequestParam long userId, Model model) {
		
		userService.deleteById(userId);
		model.addAttribute("user", new User());
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
