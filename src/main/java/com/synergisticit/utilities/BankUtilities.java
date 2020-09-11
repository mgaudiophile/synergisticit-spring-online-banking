package com.synergisticit.utilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Address;
import com.synergisticit.domain.BankTransaction;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Transaction;
import com.synergisticit.domain.TransactionType;
import com.synergisticit.domain.User;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BankTransactionService;
import com.synergisticit.service.BranchService;
import com.synergisticit.service.CustomerService;
import com.synergisticit.service.RoleService;
import com.synergisticit.service.UserService;

@Component
public class BankUtilities {

	@Autowired
	BankTransactionService trxService;
	
	@Autowired
	CustomerService custService;
	
	@Autowired
	AccountService acctService;
	
	@Autowired
	BranchService branchService;
	
	@Autowired
	@Qualifier(value = "jpaUserService")
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	public void buildAllLists(Model model) {
		model.addAttribute("roleSet", new HashSet<>(roleService.findAll()));
		model.addAttribute("listOfTransactions", trxService.findAll());
		model.addAttribute("listOfCustomers", custService.findAll());
		model.addAttribute("listOfAccounts", acctService.findAll());
		model.addAttribute("listOfBranches", branchService.findAll());
		model.addAttribute("listOfUsers", userService.findAll());
	}
	
	public void updateFromAccount(long accId, double amount) {
		Account fromAcc = acctService.findById(accId);
		fromAcc.setAccountCurrentBalance(fromAcc.getAccountCurrentBalance() - amount);
		acctService.save(fromAcc);
	}

	public void updateToAccount(long accId, double amount) {
		Account toAcc = acctService.findById(accId);
		toAcc.setAccountCurrentBalance(toAcc.getAccountCurrentBalance() + amount);
		acctService.save(toAcc);
	}
	
	public BankTransaction buildBT(TransactionType type, Transaction trx) {

		BankTransaction bt = new BankTransaction();

		switch (type) {
		case WITHDRAW:
			bt.setTransactionType(TransactionType.WITHDRAW);
			bt.setTrxFromAccount(trx.getFromAccountId());
			break;
		case DEPOSIT:
			bt.setTransactionType(TransactionType.DEPOSIT);
			bt.setTrxToAccount(trx.getToAccountId());
			break;
		case TRANSFER:
			bt.setTransactionType(TransactionType.TRANSFER);
			bt.setTrxFromAccount(trx.getFromAccountId());
			bt.setTrxToAccount(trx.getToAccountId());
			break;
		default:
			break;
		}

		bt.setTrxAmount(trx.getTransactionAmount());
		bt.setTrxLocalDateTime(LocalDateTime.now());
		bt.setComments(trx.getComments());
		return bt;
	}
	
	public void buildCustomerModel(Model model) {
		model.addAttribute("listOfAccounts", getCustomerAccounts());
		model.addAttribute("listOfTransactions", getCustomerTransactions());
		model.addAttribute("listOfCustomers", getCurrentCustomerAsList());
		model.addAttribute("listOfBranches", branchService.findAll());
	}
	
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		return userService.findByName(currentPrincipalName);
	}
	
	public Customer getCurrentCustomer() {
		return custService.findByUserId(getCurrentUser().getUserId());
	}
	
	public List<Customer> getCurrentCustomerAsList() {
		List<Customer> cl = new ArrayList<>();
		cl.add(getCurrentCustomer());
		return cl;
	}
	
	public List<Account> getCustomerAccounts() {
		User u = getCurrentUser();
		Customer c = custService.findByUserId(u.getUserId());
		return acctService.findByCustomerId(c.getCustomerId());
	}
	
	public List<BankTransaction> getCustomerTransactions() {
		List<BankTransaction> btl = new ArrayList<>();
		for (Account acct : getCustomerAccounts())
			btl.addAll(trxService.findByAcctId(acct.getAccountId()));
		return btl;
	}
	
	// --- For any customerId: Get all transactions involving customerId
	public List<BankTransaction> getCustomerTransactions(long customerId) {
		List<Account> cl = acctService.findByCustomerId(customerId);
		List<BankTransaction> btl = new ArrayList<>();
		for (Account acct : cl)
			btl.addAll(trxService.findByAcctId(acct.getAccountId()));
		return btl;
	}
	
	public void updateCustomerProfile(Customer customer) {
		
		Customer curr = custService.findByUserId(getCurrentUser().getUserId());
	
		curr.setCustomerName(customer.getCustomerName());
		
		Address a = new Address();
		a.setAddressLine1(customer.getCustomerAddress().getAddressLine1());
		a.setAddressLine2(customer.getCustomerAddress().getAddressLine1());
		a.setCity(customer.getCustomerAddress().getCity());
		a.setState(customer.getCustomerAddress().getState());
		
		curr.setCustomerAddress(a);
		curr.setCustomerMobile(customer.getCustomerMobile());
		curr.setCustomerPhone(customer.getCustomerPhone());
		curr.setCustomerEmail(customer.getCustomerEmail());
		
		custService.save(curr);
	}
	
	public String getValidationErrors(BindingResult br) {
		
		StringBuilder sb = new StringBuilder();
		for (ObjectError err : br.getAllErrors()) {
			sb.append(err.getDefaultMessage() + "\n");
		}
		return sb.toString();
	}
}
