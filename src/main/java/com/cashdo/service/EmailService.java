package com.cashdo.service;

import com.cashdo.dto.EmailDetails;

public interface EmailService {
	
	void sendEmailAlert(EmailDetails emailDetails);

	void sendEmailWithAttachment(EmailDetails emailDetails);
	
}
