package com.cashdo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashdo.dto.AccountInfo;
import com.cashdo.dto.BankResponse;
import com.cashdo.dto.CreditDebitRequest;
import com.cashdo.dto.EmailDetails;
import com.cashdo.dto.EnquiryRequest;
import com.cashdo.dto.LoginDto;
import com.cashdo.dto.TransactionDto;
import com.cashdo.dto.TransferRequest;
import com.cashdo.dto.UserRequest;
import com.cashdo.model.Role;
import com.cashdo.model.User;
import com.cashdo.repo.UserRepo;
import com.cashdo.utils.AccountUtils;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	TransactionService transactionService;
	


	@Override
	public BankResponse createAccount(UserRequest userRequest) {
		
		if(repo.existsByEmail(userRequest.getEmail())) {
			BankResponse resopnse= BankResponse.builder()
					.responseCode(AccountUtils.accountExintCode)
					.responseMessage(AccountUtils.accountExintMessage)
					.accountinfo(null)
					.build();
			return resopnse;
		}
		User newUser=User.builder()
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.gender(userRequest.getGender())
				.address(userRequest.getAddress())
				.stateOfOrigin(userRequest.getStateOfOrigin())
				.accountNumber(AccountUtils.generateAccountNumber())
				.accountBalance(BigDecimal.ZERO)
				.email(userRequest.getEmail())
				.password(userRequest.getPassword())
				.phoneNumber(userRequest.getPhoneNumber())
				.status("ACTIVE")
				.role(Role.valueOf("ROLE_ADMIN")) 
				.build();
		User saveUser=repo.save(newUser);
		
		EmailDetails emailDetails= EmailDetails.builder()
				.recipient(saveUser.getEmail())
				.subject("Account Created ! !")
				.messageBody("Cogratulation !, Your Account have Been Successfully Created ! !. \n Your Account Details: \n Account Name:"+ saveUser.getFirstName()+" "+ saveUser.getLastName()+"\n Account Number:"+ saveUser.getAccountNumber())
				.build();
		
		emailService.sendEmailAlert(emailDetails);
		
		
		return BankResponse.builder()
				.responseCode(AccountUtils.accountCreationCode)
				.responseMessage(AccountUtils.accountCreationMessage)
				.accountinfo(AccountInfo.builder()
						.accountBalance(saveUser.getAccountBalance())
						.accountNumber(saveUser.getAccountNumber())
						.accountName(saveUser.getFirstName()+" "+saveUser.getLastName())
						.build())
				.build();
	}
	
	public BankResponse login (LoginDto loginDto) {
		
		
		EmailDetails loginAlert = EmailDetails.builder()
				.subject("You are logged in ! !")
				.recipient(loginDto.getEmail())
				.messageBody("You logged into your account. If you did not initiate this request, please contact your bank ! !")
				.build();
				
		emailService.sendEmailAlert(loginAlert);
		return BankResponse.builder()
				.responseCode("LOGIN_SUCCESS")
				.responseMessage("You Have succssfully Logged into your account ! !")
				.build();
	}

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
		boolean isAccountExist = repo.existsByAccountNumber(enquiryRequest.getAccountNumber());
		if(!isAccountExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.accountNotExistCode)
					.responseMessage(AccountUtils.accountNotExistMessage)
					.accountinfo(null)
					.build();
		}
		User foundUser = repo.findByAccountNumber(enquiryRequest.getAccountNumber());
		return BankResponse.builder()
				.responseCode(AccountUtils.accountFoundCode)
				.responseMessage(AccountUtils.accountFoundMessage)
				.accountinfo(AccountInfo.builder()
						.accountBalance(foundUser.getAccountBalance())
						.accountNumber(foundUser.getAccountNumber())
						.accountName(foundUser.getFirstName()+" "+foundUser.getLastName())
						.build())
				.build();
	}

	@Override
	public String nameEnquiry(EnquiryRequest enquiryRequest) {
		boolean isAccountExist = repo.existsByAccountNumber(enquiryRequest.getAccountNumber());
		if(!isAccountExist) {
			return AccountUtils.accountNotExistMessage;
		}
		User foundUser = repo.findByAccountNumber(enquiryRequest.getAccountNumber());
		return foundUser.getFirstName()+" "+foundUser.getLastName();
	}

	@Override
	public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
		boolean isAccountExist = repo.existsByAccountNumber(creditDebitRequest.getAccountNumber());
		if(!isAccountExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.accountNotExistCode)
					.responseMessage(AccountUtils.accountNotExistMessage)
					.accountinfo(null)
					.build();
		}
		User userToCredit = repo.findByAccountNumber(creditDebitRequest.getAccountNumber());
		userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitRequest.getAmount()));
		repo.save(userToCredit);
		
		TransactionDto transactionDto = TransactionDto.builder()
				.accountNumber(userToCredit.getAccountNumber())
				.transactionType("CREDIT")
				.amount(creditDebitRequest.getAmount())
				.build();
		
		transactionService.saveTransaction(transactionDto);
		
		return BankResponse.builder()
				.responseCode(AccountUtils.accountCreditSuccessCode)
				.responseMessage(AccountUtils.accountCreditSuccessMessage)
				.accountinfo(AccountInfo.builder()
						.accountName(userToCredit.getFirstName()+" "+userToCredit.getLastName())
						.accountBalance(userToCredit.getAccountBalance())
						.accountNumber(creditDebitRequest.getAccountNumber())
						.build())
				.build();
		
	}

	@Override
	public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {

	    boolean isAccountExist = repo.existsByAccountNumber(creditDebitRequest.getAccountNumber());
	    if (!isAccountExist) {
	        return BankResponse.builder()
	                .responseCode(AccountUtils.accountNotExistCode)
	                .responseMessage(AccountUtils.accountNotExistMessage)
	                .accountinfo(null)
	                .build();
	    }

	    User userToDebit = repo.findByAccountNumber(creditDebitRequest.getAccountNumber());
	    BigDecimal availableBalance = userToDebit.getAccountBalance();
	    BigDecimal debitBalance = creditDebitRequest.getAmount();

	    if (availableBalance.compareTo(debitBalance) < 0) {
	        return BankResponse.builder()
	                .responseCode(AccountUtils.insufficientBalanceCode)
	                .responseMessage(AccountUtils.insufficientBalanceMessage)
	                .accountinfo(null)
	                .build();
	    }
	    else {
	        userToDebit.setAccountBalance(availableBalance.subtract(debitBalance));
	        repo.save(userToDebit);
	        
	        TransactionDto transactionDto = TransactionDto.builder()
					.accountNumber(userToDebit.getAccountNumber())
					.transactionType("CREDIT")
					.amount(creditDebitRequest.getAmount())
					.build();
			
			transactionService.saveTransaction(transactionDto);
	        
	        return BankResponse.builder()
	                .responseCode(AccountUtils.accountDebitedSuccessCode)
	                .responseMessage(AccountUtils.accountDebitedSuccessMessage)
	                .accountinfo(AccountInfo.builder() 
	                        .accountNumber(creditDebitRequest.getAccountNumber())
	                        .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
	                        .accountBalance(userToDebit.getAccountBalance())
	                        .build())
	                .build();
	    }
	}

	@Override
	public BankResponse transferRequest(TransferRequest transferRequest) {
		 boolean isAccountExist = repo.existsByAccountNumber(transferRequest.getAccountNumber());
		 if (!isAccountExist) {
		        return BankResponse.builder()
		                .responseCode(AccountUtils.accountNotExistCode)
		                .responseMessage(AccountUtils.accountNotExistMessage)
		                .accountinfo(null)
		                .build();
		 }
		 boolean isDestinationAccountExist = repo.existsByAccountNumber(transferRequest.getDestinationAccountNumber());
		 if(!isDestinationAccountExist) {
			 return BankResponse.builder()
		                .responseCode(AccountUtils.destinationAccountNotExistCode)
		                .responseMessage(AccountUtils.destinationAccountNotExistMessage)
		                .accountinfo(null)
		                .build();
		 }
		 User sourceAcountUser =repo.findByAccountNumber(transferRequest.getAccountNumber());
		 if(sourceAcountUser.getAccountBalance().compareTo(transferRequest.getAmount()) < 0) {
			 return BankResponse.builder()
					 .responseCode(AccountUtils.insufficientBalanceCode)
					 .responseMessage(AccountUtils.insufficientBalanceMessage)
					 .accountinfo(null)
					 .build();
		 }
		 
		 sourceAcountUser.setAccountBalance(sourceAcountUser.getAccountBalance().subtract(transferRequest.getAmount()));
		 String sourceUserName= sourceAcountUser.getFirstName()+" "+sourceAcountUser.getLastName();
		 repo.save(sourceAcountUser);
		 
		 EmailDetails debitAlert = EmailDetails.builder()
				 .subject("Debit Alert")
				 .recipient(sourceAcountUser.getEmail())
				 .messageBody(sourceUserName+" A/c: "+sourceAcountUser.getAccountNumber()+" Debited for Rs: "+transferRequest.getAmount()+"Transfered to A/c: "+transferRequest.getDestinationAccountNumber() +" Avilable Balance Rs: "+sourceAcountUser.getAccountBalance()+" - CASHDO Bank ! !")
				 .build();
		 emailService.sendEmailAlert(debitAlert);
		
		 User destinationAccountUser = repo.findByAccountNumber(transferRequest.getDestinationAccountNumber());
		 destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transferRequest.getAmount()));
		 String destinationUserName= destinationAccountUser.getFirstName()+" "+destinationAccountUser.getLastName();

		 repo.save(destinationAccountUser);
		 
		 EmailDetails creditAlert = EmailDetails.builder()
				 .subject("Debit Alert")
				 .recipient(destinationAccountUser.getEmail())
				 .messageBody(destinationUserName+" A/c: "+destinationAccountUser.getAccountNumber()+" Credited for Rs: "+transferRequest.getAmount()+"Recived From A/c: "+transferRequest.getAccountNumber() +" Avilable Balance Rs: "+destinationAccountUser.getAccountBalance()+" - CASHDO Bank ! !")
				 .build();
		 emailService.sendEmailAlert(creditAlert);
		 
		 TransactionDto transactionDto = TransactionDto.builder()
					.accountNumber(destinationAccountUser.getAccountNumber())
					.transactionType("CREDIT")
					.amount(transferRequest.getAmount())
					.build();
			
			transactionService.saveTransaction(transactionDto);
		 
		return BankResponse.builder()
				.responseCode(AccountUtils.accountTransferSuccessCode)
				.responseMessage(AccountUtils.accountTransferSuccessMessage)
				.accountinfo(null)
				.build();
		   
	}

	@Override
	public List<User> getAllUsers() {
		
		return repo.findAll();
	}

	
	
	
}
