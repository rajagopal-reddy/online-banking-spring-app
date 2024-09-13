package com.cashdo.service;

import java.util.List;

import com.cashdo.dto.TransactionDto;
import com.cashdo.model.Transaction;

public interface TransactionService {

	void saveTransaction (TransactionDto transactionDto);
	
	List<Transaction> getAll();
}
