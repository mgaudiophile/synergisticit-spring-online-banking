package com.synergisticit.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;					//always use the standard javax.persistence annotations unless you need specific org.hibernate
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="role")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roleId;
	
	private String name;
	
	@JsonBackReference					// used to avoid cyclic reference
	@ManyToMany(mappedBy="roles")
	private Set<User> users = new HashSet<>();
	
	public Role() {
		
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
