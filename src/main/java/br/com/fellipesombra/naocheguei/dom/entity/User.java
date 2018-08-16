package br.com.fellipesombra.naocheguei.dom.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import br.com.fellipesombra.naocheguei.util.CryptoUtil;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String password;
    private String name;
    private String salt;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSalt() {
	return salt;
    }

    public void setSalt(String salt) {
	this.salt = salt;
    }
    
    public byte[] getSaltBytes(){
	return CryptoUtil.stringToByte(salt);
    }
}
