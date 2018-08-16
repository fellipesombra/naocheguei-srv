package br.com.fellipesombra.naocheguei.service.email;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 * Configurações para utilizar o serviço de email
 */
@Deprecated
public class EmailBaseConfig {

	private static final String EMAIL_NAOCHEGUEI = "naocheguei@gmail.com";

	private Properties mailServerProperties;
	protected Session getMailSession;

	protected EmailBaseConfig() {
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
	}

	/**
	 * Envia o email através da conta do email default
	 * 
	 * @param generateMailMessage
	 * @throws MessagingException
	 */
	protected void send(MimeMessage generateMailMessage) throws MessagingException {
		Transport transport = getMailSession.getTransport("smtp");
		transport.connect("smtp.gmail.com", EMAIL_NAOCHEGUEI, System.getenv("NAOCHEGUEI_EMAIL_PASSWORD"));
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}
