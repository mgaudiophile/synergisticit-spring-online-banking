package com.synergisticit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.domain.Address;
import com.synergisticit.domain.Branch;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.utilities.BankUtilities;
import com.synergisticit.validation.AdminBranchValidator;

@RestController
public class RestControllerBranch {

	private BankUtilities bankUtil;
	private BranchService branchService;
	private AccountService acctService;
	private AdminBranchValidator branchValid;
	
	@Autowired
	public RestControllerBranch(BankUtilities bankUtil, 
								BranchService branchService, 
								AccountService acctService, 
								AdminBranchValidator branchValid) 
	{
		this.bankUtil = bankUtil;
		this.branchService = branchService;
		this.acctService = acctService;
		this.branchValid = branchValid;
	}
	
	@InitBinder
	public void initValidBranchAdmin(WebDataBinder binder) {
		binder.addValidators(branchValid);
	}
	
	@GetMapping(value = "/api/branches")
	public ResponseEntity<?> getBranches() {
		
		List<Branch> listOfBranches = branchService.findAll();
		if (listOfBranches.isEmpty()) {
			return new ResponseEntity<String>("no branches found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Branch>>(listOfBranches, HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/branches/{id}")
	public ResponseEntity<?> getBranch(@PathVariable("id") long id) {
		
		if (branchService.existsById(id)) {
			return new ResponseEntity<Branch>(branchService.findById(id), HttpStatus.OK);
		}
		return new ResponseEntity<String>("branch with id: " + id + " not found", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PostMapping(value = "/api/branches", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveBranch(@Valid @RequestBody Branch branch, BindingResult br) {
		
		if (!br.hasErrors()) {
			if (!branchService.existsById(branch.getBranchId())) {
				
				return new ResponseEntity<Branch>(branchService.save(branch), HttpStatus.CREATED);
			}
			return new ResponseEntity<String>("branch with id: " + branch.getBranchId() + " already exists", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(bankUtil.getValidationErrors(br), HttpStatus.CONFLICT);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PutMapping(value = "/api/branches", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateBranch(@Valid @RequestBody Branch branch, BindingResult br) {
		
		if (!br.hasErrors()) {
			if (branchService.existsById(branch.getBranchId())) {
				
				Branch bDb = branchService.findById(branch.getBranchId());
				bDb.setBranchName(branch.getBranchName());
				
				Address addr = new Address(branch.getBranchAddress().getAddressLine1(),
											branch.getBranchAddress().getAddressLine2(),
											branch.getBranchAddress().getCity(),
											branch.getBranchAddress().getState());
				
				bDb.setBranchAddress(addr);
				bDb.setBranchAccounts(acctService.findByBranchId(branch.getBranchId()));
				
				return new ResponseEntity<Branch>(branchService.save(bDb), HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<String>("branch with id: " + branch.getBranchId() + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(bankUtil.getValidationErrors(br), HttpStatus.CONFLICT);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@DeleteMapping(value = "/api/branches/{id}")
	public ResponseEntity<?> deleteBranch(@PathVariable("id") long id) {
		
		if (branchService.existsById(id)) {
			branchService.deleteById(id);
			return new ResponseEntity<String>("branch with id: " + id + " was deleted", HttpStatus.OK);
		}
		return new ResponseEntity<String>("branch with id: " + id + " not found", HttpStatus.NOT_FOUND);
	}
}
