package br.com.fellipesombra.naocheguei.service.email;

import br.com.fellipesombra.naocheguei.valueobject.Email;

/**
 * Classe respons�vel pelos servi�os de envio de email
 */
public interface EmailService {

	/**
	 * Envia o {@link Email}
	 * @param emailInfo Informa��es do email que ser� enviado
	 */
	public void sendEmail(Email emailInfo);
}
