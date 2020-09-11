package com.synergisticit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.synergisticit.domain.User;
import com.synergisticit.service.UserService;
import com.synergisticit.utilities.BankUtilities;
import com.synergisticit.validation.AdminUserValidator;

@RestController
public class RestControllerUser {

	private BankUtilities bankUtil;
	private UserService userService;
	private AdminUserValidator userValid;
	
	@Autowired
	public RestControllerUser(BankUtilities bankUtil, @Qualifier(value = "hibernateUserService") UserService userService, AdminUserValidator userValid) {
		this.bankUtil = bankUtil;
		this.userService = userService;
		this.userValid = userValid;
	}
	
	@InitBinder
	public void initAdminUserValidator(WebDataBinder binder) {
		binder.addValidators(userValid);
	}
	
	@GetMapping(value = "/api/users")
	public ResponseEntity<?> getUsers() {
		
		List<User> listOfUsers = userService.findAll();
		if (listOfUsers.isEmpty()) {
			return new ResponseEntity<String>("no users found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(listOfUsers, HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
		
		if (userService.existsById(id)) {
			return new ResponseEntity<User>(userService.findById(id), HttpStatus.OK);
		}
		return new ResponseEntity<String>("user with id: " + id + " not found", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PostMapping(value = "/api/users", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult br) {
		
		if (!br.hasErrors()) {
			if (!userService.existsById(user.getUserId())) {
				
				return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);	
			}
			return new ResponseEntity<String>("user id: " + user.getUserId() + " already exists", HttpStatus.CONFLICT);
		} 
		return new ResponseEntity<String>(bankUtil.getValidationErrors(br), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@PutMapping(value = "/api/users", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult br) {
		
		if (!br.hasErrors()) {
			if (userService.existsById(user.getUserId())) {
				
				User uDb = userService.findById(user.getUserId());
				uDb.setName(user.getName());
				uDb.setPassword(user.getPassword());
				uDb.setEmail(user.getEmail());
				uDb.setRoles(user.getRoles());
				
				return new ResponseEntity<User>(userService.save(uDb), HttpStatus.ACCEPTED); 
			}
			return new ResponseEntity<String>("user with id: " + user.getUserId() + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(bankUtil.getValidationErrors(br), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@PreAuthorize("hasRole('ROLE_Admin')")
	@DeleteMapping(value = "/api/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		
		if (userService.existsById(id)) {
			userService.deleteById(id);
			return new ResponseEntity<String>("user with id: " + id + " has been deleted", HttpStatus.OK);
		}
		return new ResponseEntity<String>("user with id: " + id + " not found", HttpStatus.NOT_FOUND);
	}
}
