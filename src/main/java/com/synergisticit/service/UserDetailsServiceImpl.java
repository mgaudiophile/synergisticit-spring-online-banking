package com.synergisticit.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Role;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	@Qualifier(value = "jpaUserService")
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//System.out.println("username: " + username);
		
		com.synergisticit.domain.User userEntity = userService.findByName(username);
		Set<GrantedAuthority> authorities = new HashSet<>();
		
		for (Role role : userEntity.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
			//System.out.println("role: " + role.getName());
		}
		
		return new org.springframework.security.core.userdetails.User(userEntity.getName(), userEntity.getPassword(), authorities);
	}

}
