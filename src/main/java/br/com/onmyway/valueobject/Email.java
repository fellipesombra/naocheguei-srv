package br.com.onmyway.valueobject;

import java.util.List;

public class Email {

	private List<String> receivers;
	private String subject;
	private String message;
	
	public Email(List<String> receivers, String subject, String message) {
		super();
		this.receivers = receivers;
		this.subject = subject;
		this.message = message;
	}
	public List<String> getReceivers() {
		return receivers;
	}
	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
