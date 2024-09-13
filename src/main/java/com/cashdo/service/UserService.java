package com.cashdo.service;

import java.util.List;

import com.cashdo.dto.BankResponse;
import com.cashdo.dto.CreditDebitRequest;
import com.cashdo.dto.EnquiryRequest;
import com.cashdo.dto.LoginDto;
import com.cashdo.dto.TransferRequest;
import com.cashdo.dto.UserRequest;
import com.cashdo.model.User;

public interface UserService {
	
	BankResponse createAccount(UserRequest userRequest);
	
	BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
	
	String nameEnquiry(EnquiryRequest enquiryRequest);

	BankResponse creditAccount(CreditDebitRequest creditDebitRequest);
	
	BankResponse debitAccount(CreditDebitRequest creditDebitRequest);
	
	BankResponse transferRequest(TransferRequest transferRequest);

	BankResponse login(LoginDto loginDto);
	
	List<User> getAllUsers();
}
