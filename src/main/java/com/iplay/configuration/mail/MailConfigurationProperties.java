package com.iplay.configuration.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("feast-booking.mail")
public class MailConfigurationProperties {

    @Value("${spring.mail.username}")  
	private String sender;
    
	public RegistrationEmail registrationEmail = new RegistrationEmail();

	public class RegistrationEmail {
		private String subject = "FeastBooking 注冊郵件";
		private String content = "您的驗證碼為：{totp}  此驗證碼1分鐘内有效。";

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public RegistrationEmail getRegistrationEmail() {
		return registrationEmail;
	}

	public void setRegistrationEmail(RegistrationEmail registrationEmail) {
		this.registrationEmail = registrationEmail;
	}
}
