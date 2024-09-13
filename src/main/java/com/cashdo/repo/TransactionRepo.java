package com.cashdo.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashdo.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, String> {
	 List<Transaction> findByAccountNumberAndCreatedAtBetween(String accountNumber, LocalDateTime startDate, LocalDateTime endDate);
	}
