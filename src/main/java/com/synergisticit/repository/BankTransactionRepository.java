package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.BankTransaction;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

	@Query(value = "SELECT * FROM bankTransaction b WHERE b.trxFromAccount = :acctId OR b.trxToAccount = :acctId", nativeQuery=true)
	List<BankTransaction> findByAcctId(long acctId);
}
