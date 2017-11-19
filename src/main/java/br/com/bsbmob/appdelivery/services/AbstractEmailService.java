package br.com.bsbmob.appdelivery.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.bsbmob.appdelivery.domain.Cliente;
import br.com.bsbmob.appdelivery.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("default.sender")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage msg = prepareSimpleMailMassageFromPedido(pedido);
		sendEmail(msg);
	}

	protected SimpleMailMessage prepareSimpleMailMassageFromPedido(Pedido pedido) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(pedido.getCliente().getEmail());
		msg.setFrom(sender);
		msg.setSubject("Pedido confirmado! Número do pedido: " + pedido.getId());
		msg.setSentDate(new Date(System.currentTimeMillis()));
		msg.setText(pedido.toString());
		
		return msg;
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage msg = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(msg);
	}

	private SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(cliente.getEmail());
		msg.setFrom(sender);
		msg.setSubject("Solicitação de nova senha!");
		msg.setSentDate(new Date(System.currentTimeMillis()));
		msg.setText("Nova senha: " + newPass);
		
		return msg;
	}
	
}
