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
import com.synergisticit.service.BankTransactionService;
import com.synergisticit.utilities.BankUtilities;
import com.synergisticit.validation.CustomerTransactionValidator;

@Controller
public class CustomerTransactionController {

	private BankUtilities bankUtil;
	private BankTransactionService trxService;
	private CustomerTransactionValidator custTrxValidator;

	@Autowired
	public CustomerTransactionController(BankUtilities bankUtil, BankTransactionService trxService,
			CustomerTransactionValidator custTrxValidator) {
		this.bankUtil = bankUtil;
		this.trxService = trxService;
		this.custTrxValidator = custTrxValidator;
	}

	@InitBinder("transaction")
	public void initCustTrxValidBinder(WebDataBinder binder) {
		binder.addValidators(custTrxValidator);
	}

	@PostMapping("/customerWithdraw")
	public String withdraw(@Valid @ModelAttribute Transaction transaction, BindingResult br, Model model) {

		if (!br.hasErrors() && transaction.getFromAccountId() != null) {
			BankTransaction bt = bankUtil.buildBT(TransactionType.WITHDRAW, transaction);
			trxService.save(bt);
			bankUtil.updateFromAccount(transaction.getFromAccountId(), transaction.getTransactionAmount());
			model.addAttribute("transaction", new Transaction());
		} else {
			model.addAttribute("transaction", transaction);
		}

		bankUtil.buildCustomerModel(model);
		return "customer";
	}

	@RequestMapping("/customerDeposit")
	public String customerDeposit(@Valid @ModelAttribute Transaction transaction, BindingResult br, Model model) {

		if (!br.hasErrors() && transaction.getToAccountId() != null) {
			BankTransaction bt = bankUtil.buildBT(TransactionType.DEPOSIT, transaction);
			trxService.save(bt);
			bankUtil.updateToAccount(transaction.getToAccountId(), transaction.getTransactionAmount());
			model.addAttribute("transaction", new Transaction());
		} else {
			model.addAttribute("transaction", transaction);
		}

		bankUtil.buildCustomerModel(model);
		return "customer";
	}

	@RequestMapping("/customerTransfer")
	public String customerTransfer(@Valid @ModelAttribute Transaction transaction, BindingResult br, Model model) {

		if (!br.hasErrors() && transaction.getFromAccountId() != null && transaction.getToAccountId() != null) {
			BankTransaction bt = bankUtil.buildBT(TransactionType.TRANSFER, transaction);
			trxService.save(bt);
			bankUtil.updateFromAccount(transaction.getFromAccountId(), transaction.getTransactionAmount());
			bankUtil.updateToAccount(transaction.getToAccountId(), transaction.getTransactionAmount());
			model.addAttribute("transaction", new Transaction());
		} else {
			model.addAttribute("transaction", transaction);
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
