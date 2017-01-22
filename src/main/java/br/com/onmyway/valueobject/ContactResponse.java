package br.com.onmyway.valueobject;

import br.com.onmyway.dom.entity.Contact;

public class ContactResponse {

    private int status;
    private Contact contact;

    public ContactResponse(int status, Contact contact) {
	super();
	this.status = status;
	this.contact = contact;
    }

    public ContactResponse(int status) {
	super();
	this.status = status;
    }

    public ContactResponse() {
	// TODO Auto-generated constructor stub
    }

    public int getStatus() {
	return status;
    }

    public void setStatus(int status) {
	this.status = status;
    }

    public Contact getContact() {
	return contact;
    }

    public void setContact(Contact contact) {
	this.contact = contact;
    }

}
