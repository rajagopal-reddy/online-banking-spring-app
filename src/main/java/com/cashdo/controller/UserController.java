package com.cashdo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cashdo.dto.BankResponse;
import com.cashdo.dto.CreditDebitRequest;
import com.cashdo.dto.EnquiryRequest;
import com.cashdo.dto.LoginDto;
import com.cashdo.dto.TransferRequest;
import com.cashdo.dto.UserRequest;
import com.cashdo.model.User;
import com.cashdo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@Tag(name = "User Account Management APIs")
public class UserController {
	@Autowired
	private UserService service;
	
	@Operation(
			summary = "Create New User Account",
			description = "Creating a new User and Assigning an ID "
			)
	@ApiResponse(
			responseCode = "201",
			description = "HTTP Status 201 Account CREATED"
			
			)
	
	@PostMapping()
	public BankResponse createAcount(@RequestBody UserRequest userRequest) {
		
		return service.createAccount(userRequest);
	}
	
	@PostMapping("/login")
	public BankResponse login(@RequestBody LoginDto loginDto) {
		return service.login(loginDto);
	}
	
	@Operation(
			summary = "Account Balance Enquiry",
			description = "Giving an Account Number to check Account Balance"
			)
	@ApiResponse(
			responseCode = "202",
			description = "HTTP Status 202 Account BALABCE"
			
			)
	
	@GetMapping("balance")
	public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
		return service.balanceEnquiry(enquiryRequest);
	}
	
	@Operation(
			summary = "Account Name",
			description = "Giving an Account Number to check Account Name "
			)
	@ApiResponse(
			responseCode = "203",
			description = "HTTP Status 203 Account NAME"
			
			)
	
	@GetMapping("name")
	public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
		return service.nameEnquiry(enquiryRequest);
	}
	
	@Operation(
			summary = "Account Credit",
			description = "Giving an Account Number to do Credit Operation "
			)
	@ApiResponse(
			responseCode = "204",
			description = "HTTP Status 204 Account CREDIT"
			
			)
	
	@PostMapping("credit")
	public BankResponse creditaccount(@RequestBody CreditDebitRequest creditDebitRequest) {
		
		return service.creditAccount(creditDebitRequest);
	}
	
	@Operation(
			summary = "Account Debit",
			description = "Giving an Account Number to do Debit Operation "
			)
	@ApiResponse(
			responseCode = "205",
			description = "HTTP Status 205 Account DEBIT"
			
			)
	
	@PostMapping("debit")
	public BankResponse debitAccount(@RequestBody CreditDebitRequest creditDebitRequest) {
		
		return service.debitAccount(creditDebitRequest);
	}
	
	@Operation(
			summary = "Money Transfer",
			description = "Giving User Account Number and Destination Account Number to Transfer Money "
			)
	@ApiResponse(
			responseCode = "206",
			description = "HTTP Status 206 Money TRANSFERED"
			
			)
	
	@PostMapping("transfer")
	public BankResponse transferAccount(@RequestBody TransferRequest transferRequest) {
		return service.transferRequest(transferRequest);
	}
	

	@GetMapping
	public List<User> getAllUsers(){
		return service.getAllUsers();
	}
}
