package com.cashdo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cashdo.dto.TransactionDto;
import com.cashdo.model.Transaction;
import com.cashdo.repo.TransactionRepo;

@Component
public class TransactionServiceImp implements TransactionService {

	@Autowired
	private TransactionRepo transactionRepo;

	@Override
	public void saveTransaction(TransactionDto transactionDto) {
		Transaction transaction = Transaction.builder()
				.transactionType(transactionDto.getTransactionType())
				.accountNumber(transactionDto.getAccountNumber())
				.amount(transactionDto.getAmount())
				.status("SUCCESS")
				.build();
		
		transactionRepo.save(transaction);
		System.out.println("Transaction saved Successfully ! !");
		
	}

	@Override
	public List<Transaction> getAll() {
		// TODO Auto-generated method stub
		return transactionRepo.findAll();
	}
	
}
