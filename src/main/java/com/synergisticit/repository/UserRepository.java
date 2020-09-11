package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synergisticit.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByName(String name);
	
	@Query(value = "SELECT * FROM user u WHERE u.userId = :userId AND u.name = :name", nativeQuery=true)
	User findByIdAndName(@Param("userId") Long userId, @Param("name") String name);
	
	User findByName(String name);
}
