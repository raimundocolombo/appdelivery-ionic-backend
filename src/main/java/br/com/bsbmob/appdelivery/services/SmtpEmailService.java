package br.com.bsbmob.appdelivery.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOGGER.info("Enviando de e-mail...");
		mailSender.send(msg);
		LOGGER.info("E-mail enviado com sucesso!");
	}

}
