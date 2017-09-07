package com.iplay.component.mailer;

public interface AbstractMailer {
	
	void sendMail(String from, String receiver, String subject, String content);

}
