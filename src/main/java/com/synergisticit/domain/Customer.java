package com.synergisticit.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	private String customerName;
	
	private String customerGender;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate customerDob;
	
	private String customerMobile;
	
	private String customerPhone;
	
	private String customerEmail;
	
	@Embedded
	private Address customerAddress;
	
	private String customerSSN;
	
	@OneToMany(mappedBy="accountCustomer")
	//@JoinColumn(name = "accountId")
	@JsonManagedReference("customer-account")
	private List<Account> customerAccounts = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;
	
	public Customer() {
		
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerGender() {
		return customerGender;
	}

	public void setCustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}

	public LocalDate getCustomerDob() {
		return customerDob;
	}

	public void setCustomerDob(LocalDate customerDob) {
		this.customerDob = customerDob;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public Address getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(Address customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerSSN() {
		return customerSSN;
	}

	public void setCustomerSSN(String customerSSN) {
		this.customerSSN = customerSSN;
	}

	public List<Account> getCustomerAccounts() {
		return customerAccounts;
	}

	public void setCustomerAccounts(List<Account> customerAccounts) {
		this.customerAccounts = customerAccounts;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerGender="
				+ customerGender + ", customerDob=" + customerDob + ", customerMobile=" + customerMobile
				+ ", customerPhone=" + customerPhone + ", customerEmail=" + customerEmail + ", customerAddress="
				+ customerAddress + ", customerSSN=" + customerSSN + ", customerAccounts=" + customerAccounts
				+ ", user=" + user + "]";
	}
	
	
}
