package com.iplay.component.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Mailer implements AbstractMailer {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendMail(String from, String receiver, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(receiver);
		message.setSubject(subject);
		message.setText(content);
		javaMailSender.send(message);
	}

}
