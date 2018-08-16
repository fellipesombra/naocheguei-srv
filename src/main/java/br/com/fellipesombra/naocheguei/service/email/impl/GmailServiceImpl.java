package br.com.fellipesombra.naocheguei.service.email.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import br.com.fellipesombra.naocheguei.service.email.GmailService;

public final class GmailServiceImpl implements GmailService {

    private static final String APPLICATION_NAME = "NãoCheguei!";
    private static final String GMAIL_ACCOUNT = "naocheguei@gmail.com";

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final HttpTransport httpTransport;

    public GmailServiceImpl(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    @Override
    public boolean sendMessage(List<String>recipientAddress, String subject, String body) throws MessagingException,
            IOException {
        Message message = createMessageWithEmail(
                createEmail(recipientAddress, GMAIL_ACCOUNT, subject, body));

        return createGmail().users()
                .messages()
                .send(GMAIL_ACCOUNT, message)
                .execute()
                .getLabelIds().contains("SENT");
    }

    private Gmail createGmail() {
        Credential credential = authorize();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private MimeMessage createEmail(List<String>recipientAddress, String from, String subject, String bodyText) throws MessagingException {
        MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
        email.setFrom(new InternetAddress(from));
        for (String to : recipientAddress) {
        	email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		}
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    private Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);

        return new Message()
                .setRaw(Base64.encodeBase64URLSafeString(buffer.toByteArray()));
    }

    private Credential authorize() {
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(System.getenv("NAOCHEGUEI_GOOGLE_CLIENT_ID"), System.getenv("NAOCHEGUEI_GOOGLE_CLIENT_SECRET"))
                .build()
                .setAccessToken(System.getenv("NAOCHEGUEI_GOOGLE_ACCESS_TOKEN"))
                .setRefreshToken(System.getenv("NAOCHEGUEI_GOOGLE_REFRESH_TOKEN"));
    }
}