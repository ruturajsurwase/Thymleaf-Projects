package com.construction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.construction.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>
 {
	 public User  findByEmail(String email);
}
