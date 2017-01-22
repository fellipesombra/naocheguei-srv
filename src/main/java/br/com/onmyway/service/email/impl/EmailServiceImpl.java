package br.com.onmyway.service.email.impl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.onmyway.service.email.EmailBaseConfig;
import br.com.onmyway.service.email.EmailService;
import br.com.onmyway.valueobject.Email;


/**
 * Implementação da classe responsável pelos serviços de envio de email
 */
public class EmailServiceImpl extends EmailBaseConfig implements EmailService{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendEmail(Email emailInfos) {
		MimeMessage	email = new MimeMessage(getMailSession);
		try {
			for (String receiver : emailInfos.getReceivers()) {
				email.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			}
			email.setSubject(emailInfos.getSubject());
			email.setContent(emailInfos.getMessage(), "text/html");
			send(email);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
