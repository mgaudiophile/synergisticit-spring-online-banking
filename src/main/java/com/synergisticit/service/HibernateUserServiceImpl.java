package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.dao.UserDao;
import com.synergisticit.domain.User;

@Service(value = "hibernateUserService")
public class HibernateUserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public User save(User user) {
		
		return userDao.save(user);
	}

	@Override
	public List<User> findAll() {
		System.out.println("HibernateUserServiceImpl.findAll()....");
		return userDao.findAll();
	}

	@Override
	public User findById(long id) {
		System.out.println("HibernateUserServiceImpl.findById()....");
		return userDao.findById(id);
	}

	@Override
	public User update(User user) {
		
		return userDao.update(user);
	}

	@Override
	public User updateById(long id) {
		
		return userDao.updateById(id);
	}

	@Override
	public void delete(User user) {
		
		userDao.delete(user);
	}

	@Override
	public void deleteById(long id) {
		
		userDao.deleteById(id);
	}

	@Override
	public boolean existsById(long id) {
		
		return userDao.existsById(id);
	}

	@Override
	public boolean existsByName(String name) {
		
		return userDao.existsByName(name);
	}

	@Override
	public User findByIdAndName(Long userId, String name) {
		
		return userDao.findByIdAndName(userId, name);
	}

	@Override
	public User findByName(String name) {
		
		return userDao.findByName(name);
	}

}
