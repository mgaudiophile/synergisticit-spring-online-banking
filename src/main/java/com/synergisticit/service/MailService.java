package com.synergisticit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	JavaMailSender mailSender;
	
	public SimpleMailMessage sendEmail(String email, String subject, String msg) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("fremontsession@gmail.com");
		message.setSubject(subject);
		message.setText(msg);
		
		mailSender.send(message);
		
		return message;
	}
	
	public SimpleMailMessage sendConfirmationEmail(String email, String subject) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setCc("fremontsession@gmail.com");
		message.setSubject("Confirmation of Receipt for: " + subject);
		message.setText("Hi, \n\nWe have received your message and will get back to you as soon as possible.\n\n");
		
		mailSender.send(message);
		
		return message;
	}
}
