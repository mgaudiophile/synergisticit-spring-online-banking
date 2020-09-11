package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query(value = "SELECT * FROM account a WHERE a.customerId = :customerId", nativeQuery=true)
	List<Account> findByCustomerId(long customerId);
	
	@Query(value = "SELECT * FROM account a WHERE a.branchId = :branchId", nativeQuery=true)
	List<Account> findByBranchId(long branchId);
	
}
