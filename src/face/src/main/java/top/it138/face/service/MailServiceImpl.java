package top.it138.face.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JavaMailSenderImpl  mailSender;
	
	
	@Async
	@Override
	public void sendText(String email, String subject, String text) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		try {
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setSentDate(new Date());
			helper.setText(text, true);
			helper.setFrom(mailSender.getUsername());
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return;
		}
		
		mailSender.send(mimeMessage);
	}
	
}
