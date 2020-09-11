package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Branch;

public interface BranchService {

	Branch save(Branch branch);
	
	List<Branch> findAll();
	Branch findById(long id);
	
	boolean existsById(long id);
	
	Branch update(Branch branch);
	Branch updateById(long id);
	
	void delete(Branch branch);
	void deleteById(long id);
}
