package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.BankTransaction;
import com.synergisticit.repository.BankTransactionRepository;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {

	@Autowired
	BankTransactionRepository trxRepo;
	
	@Override
	public List<BankTransaction> findAll() {
		
		return trxRepo.findAll();
	}

	@Override
	public BankTransaction save(BankTransaction trx) {
		
		return trxRepo.save(trx);
	}

	@Override
	public List<BankTransaction> findByAcctId(long acctId) {
		
		return trxRepo.findByAcctId(acctId);
	}
}
