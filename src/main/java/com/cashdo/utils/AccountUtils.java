package com.cashdo.utils;

import java.time.Year;

public class AccountUtils {

	public static final String accountExintCode="001";
	public static final String accountExintMessage="Account Already Exists ! !";
	
	public static final String accountCreationCode="002";
	public static final String accountCreationMessage="Account Created Successfully ! !";
	
	public static final String accountNotExistCode="003";
	public static final String accountNotExistMessage="Account Don't Exists ! !";
	
	public static final String accountFoundCode="004";
	public static final String accountFoundMessage="Account Found ! !";
	
	public static final String accountCreditSuccessCode="005";
	public static final String accountCreditSuccessMessage="Amount Credited Succesfully ! !";
	
	public static final String insufficientBalanceCode="006";
	public static final String insufficientBalanceMessage="Insufficient Balance ! !";
	
	public static final String accountDebitedSuccessCode="007";
	public static final String accountDebitedSuccessMessage="Amount Debited Succesfully ! !";
	
	public static final String destinationAccountNotExistCode="008";
	public static final String destinationAccountNotExistMessage="Source Account Don't Exists ! !";
	
	public static final String accountTransferSuccessCode="009";
	public static final String accountTransferSuccessMessage="Amount Transfer Succesfully ! !";
	
	public static String generateAccountNumber() {
		Year currentyear=Year.now();
		
		int min=100000;
		int max=999999;
		
		int randNumber=(int)Math.floor(Math.random()*(max-min+1)+min);
		
		String year =String.valueOf(currentyear);
		String randomNumber=String.valueOf(randNumber);
		StringBuilder accountNum =new StringBuilder();
		
		return accountNum.append(year).append(randomNumber).toString();
	}
}
