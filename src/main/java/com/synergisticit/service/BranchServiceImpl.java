package com.synergisticit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Branch;
import com.synergisticit.repository.BranchRepository;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	BranchRepository branchRepo;
	
	@Override
	public Branch save(Branch branch) {
		
		return branchRepo.save(branch);
	}

	@Override
	public List<Branch> findAll() {
		
		return branchRepo.findAll();
	}

	@Override
	public Branch findById(long id) {
		
		Optional<Branch> opt = branchRepo.findById(id);
		if (opt.isPresent())
			return opt.get();
		
		return null;
	}
	
	@Override
	public boolean existsById(long id) {
		
		return branchRepo.existsById(id);
	}

	@Override
	public Branch update(Branch branch) {
		
		Branch bdb = findById(branch.getBranchId());
		if (bdb != null) {
			bdb.setBranchName(branch.getBranchName());
			bdb.setBranchAddress(branch.getBranchAddress());
		}
		
		return branchRepo.save(bdb);
	}

	@Override
	public Branch updateById(long id) {
		
		Optional<Branch> opt = branchRepo.findById(id);
		if (opt.isPresent())
			return opt.get();
		
		return null;
	}

	@Override
	public void delete(Branch branch) {
		
		branchRepo.delete(branch);
	}

	@Override
	public void deleteById(long id) {
		
		branchRepo.deleteById(id);
	}

}
