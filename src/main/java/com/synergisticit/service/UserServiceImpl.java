package com.synergisticit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.User;
import com.synergisticit.repository.UserRepository;

@Service(value = "jpaUserService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;
	
	@Override
	public User save(User user) {
		user.setPassword(bCryptEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public List<User> findAll() {
		
		return userRepo.findAll();
	}

	@Override
	public User findById(long id) {
		
		Optional<User> opt = userRepo.findById(id);
		if (opt.isPresent())
			return opt.get();
		
		return null;
	}

	@Override
	public User update(User user) {
		
		User userDB = findById(user.getUserId());
		if (userDB != null) {
			userDB.setName(user.getName());
			userDB.setPassword(user.getPassword());
			userDB.setEmail(user.getEmail());
			
			userDB.setRoles(user.getRoles());
			
		}
		return userDB;
	}

	@Override
	public User updateById(long id) {
		
		Optional<User> opt = userRepo.findById(id);
		if (opt.isPresent())
			return opt.get();
		
		return null;
	}

	@Override
	public void delete(User user) {
		
		userRepo.delete(user);
	}

	@Override
	public void deleteById(long id) {
		
		userRepo.deleteById(id);
	}

	@Override
	public boolean existsByName(String name) {
		
		return userRepo.existsByName(name);
	}

	@Override
	public User findByIdAndName(Long userId, String name) {
		
		return userRepo.findByIdAndName(userId, name);
	}

	@Override
	public boolean existsById(long id) {
		
		return userRepo.existsById(id);
	}

	@Override
	public User findByName(String name) {
		
		return userRepo.findByName(name);
	}

}
