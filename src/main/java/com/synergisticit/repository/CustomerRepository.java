package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query(value = "SELECT * FROM customer c WHERE c.userId = :userId", nativeQuery=true)
	Customer findByUserId(long userId);
	
	// Use @Modifying for other DML
}
