package com.synergisticit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Account;
import com.synergisticit.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepo;

	@Override
	public Account save(Account account) {
		
		return accountRepo.save(account);
	}

	@Override
	public List<Account> findAll() {
		
		return accountRepo.findAll();
	}

	@Override
	public Account findById(long id) {
		
		Optional<Account> opt = accountRepo.findById(id);
		if (opt.isPresent()) 
			return opt.get();
		
		return null;
	}
	
	@Override
	public boolean existsById(long id) {
		
		return accountRepo.existsById(id);
	}

	@Override
	public Account update(Account account) {
		
		Account accDB = findById(account.getAccountId());
		if (accDB != null) {
			//
		}
		return null;
	}

	@Override
	public Account updateById(long id) {
		
		Optional<Account> opt = accountRepo.findById(id);
		if (opt.isPresent()) 
			return opt.get();
		
		return null;
	}

	@Override
	public void delete(Account account) {
		
		accountRepo.delete(account);
	}

	@Override
	public void deleteById(long id) {
		
		accountRepo.deleteById(id);
	}

	@Override
	public List<Account> findByCustomerId(long customerId) {
		
		return accountRepo.findByCustomerId(customerId);
	}

	@Override
	public List<Account> findByBranchId(long branchId) {
		
		return accountRepo.findByBranchId(branchId);
	}

}
