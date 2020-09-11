package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.BankTransaction;

public interface BankTransactionService {

	List<BankTransaction> findAll();
	BankTransaction save(BankTransaction trx);
	
	List<BankTransaction> findByAcctId(long acctId);
}
