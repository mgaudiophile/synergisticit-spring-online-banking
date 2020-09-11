package com.synergisticit.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.synergisticit.domain.User;

public interface UserService {

	User save(User user);
	
	List<User> findAll();
	User findById(long id);
	
	User update(User user);
	User updateById(long id);
	
	void delete(User user);
	void deleteById(long id);
	
	boolean existsById(long id);
	boolean existsByName(String name);
	User findByIdAndName(@Param("userId") Long userId, @Param("name") String name);
	
	User findByName(String name);
}
