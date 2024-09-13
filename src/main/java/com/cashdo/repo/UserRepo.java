package com.cashdo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cashdo.model.User;
import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
	
	boolean existsByAccountNumber(String accountNumber);

	User findByAccountNumber(String accountNumber);
	
	
	
}
