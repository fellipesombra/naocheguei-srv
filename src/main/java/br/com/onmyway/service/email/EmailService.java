package br.com.onmyway.service.email;

import br.com.onmyway.valueobject.Email;

/**
 * Classe responsável pelos serviços de envio de email
 */
public interface EmailService {

	/**
	 * Envia o {@link Email}
	 * @param emailInfo Informações do email que será enviado
	 */
	public void sendEmail(Email emailInfo);
}
