package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Account;

public interface AccountService {

	Account save(Account account);
	
	List<Account> findAll();
	Account findById(long id);
	
	boolean existsById(long id);
	
	Account update(Account account);
	Account updateById(long id);
	
	void delete(Account account);
	void deleteById(long id);
	
	
	List<Account> findByCustomerId(long customerId);
	List<Account> findByBranchId(long branchId);
	
}
