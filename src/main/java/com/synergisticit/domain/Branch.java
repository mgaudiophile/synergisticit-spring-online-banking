package com.synergisticit.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "branch")
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long branchId;
	
	@Column(name = "branchName")
	private String branchName;
	
	@Embedded
	private Address branchAddress;
	
	@OneToMany(mappedBy = "accountBranch")
	@JsonManagedReference("branch-account")
	private List<Account> branchAccounts = new ArrayList<>();
	
	public Branch() {
		
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Address getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(Address branchAddress) {
		this.branchAddress = branchAddress;
	}

	public List<Account> getBranchAccounts() {
		return branchAccounts;
	}

	public void setBranchAccounts(List<Account> branchAccounts) {
		this.branchAccounts = branchAccounts;
	}

	@Override
	public String toString() {
		return "Branch [branchId=" + branchId + ", branchName=" + branchName + ", branchAddress=" + branchAddress
				+ ", branchAccounts=" + branchAccounts + "]";
	}
	
	
}
