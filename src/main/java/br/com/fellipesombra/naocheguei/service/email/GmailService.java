package br.com.fellipesombra.naocheguei.service.email;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

public interface GmailService {

    boolean sendMessage(List<String> recipientAddress, String subject, String body) throws MessagingException, IOException;
}