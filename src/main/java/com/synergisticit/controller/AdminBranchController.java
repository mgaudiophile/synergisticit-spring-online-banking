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
import com.synergisticit.service.BranchService;
import com.synergisticit.utilities.BankUtilities;
import com.synergisticit.validation.AdminBranchValidator;

@Controller
public class AdminBranchController {

	private BankUtilities bankUtil;
	private BranchService branchService;
	private AdminBranchValidator adminBranchValid;
	
	@Autowired
	public AdminBranchController(BankUtilities bankUtil, BranchService branchService, AdminBranchValidator adminBranchValid) {
		this.bankUtil = bankUtil;
		this.branchService = branchService;
		this.adminBranchValid = adminBranchValid;
	}

	@InitBinder("branch")
	public void initAdminBranchValidatorBinder(WebDataBinder binder) {
		binder.addValidators(adminBranchValid);
	}
	
	@PostMapping("/adminSaveBranch")
	public String adminSaveBranch(@Valid @ModelAttribute Branch branch, BindingResult br, Model model) {
		
		if (!br.hasErrors()) {
			branchService.save(branch);
			model.addAttribute("branch", new Branch());
		} else {
			model.addAttribute("branch", branch);
		}
		
		bankUtil.buildAllLists(model);
		return "admin";
	}
	
	@RequestMapping("/adminUpdateBranch")
	public String adminUpdateBranch(Branch branch, @RequestParam int branchId, Model model) {
	
		if (branchService.existsById(branchId)) {
			branch = branchService.findById(branchId);
			model.addAttribute("branch", branch);
		} else {
			model.addAttribute("branch", new Branch());
		}
		
		bankUtil.buildAllLists(model);
		return "admin";
	}
	
	@RequestMapping("/adminDeleteBranch")
	public String adminDeleteBranch(Branch branch, @RequestParam int branchId, Model model) {
		
		branchService.deleteById(branchId);
		model.addAttribute("branch", new Branch());
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
