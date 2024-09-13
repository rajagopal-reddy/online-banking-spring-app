package com.cashdo.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cashdo.model.Transaction;
import com.cashdo.service.BankStatement;
import com.cashdo.service.TransactionService;
import com.itextpdf.text.DocumentException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("bankStatement")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	private  BankStatement bankStatement;
	
	@GetMapping
	public List<Transaction> generateBankStatement(@RequestParam String accountNumber, @RequestParam String startDate, @RequestParam String endDate) throws FileNotFoundException, DocumentException{
		return bankStatement.generateStatement(accountNumber, startDate, endDate);
	}
	
	@GetMapping("/all")
	public List<Transaction> getAll(){
		return transactionService.getAll();
	}

}
