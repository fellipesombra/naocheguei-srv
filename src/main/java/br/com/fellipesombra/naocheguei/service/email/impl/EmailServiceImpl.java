package br.com.fellipesombra.naocheguei.service.email.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import br.com.fellipesombra.naocheguei.service.email.EmailService;
import br.com.fellipesombra.naocheguei.service.email.GmailService;
import br.com.fellipesombra.naocheguei.valueobject.Email;


/**
 * Implementação da classe responsável pelos serviços de envio de email
 */
public class EmailServiceImpl implements EmailService{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendEmail(Email emailInfos) {
		try {
            GmailService gmailService = new GmailServiceImpl(GoogleNetHttpTransport.newTrustedTransport());

            gmailService.sendMessage(emailInfos.getReceivers(), emailInfos.getSubject(), emailInfos.getMessage());
        } catch (GeneralSecurityException | IOException | MessagingException e) {
            e.printStackTrace();
        }
	}

}
