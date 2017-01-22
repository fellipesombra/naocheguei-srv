package br.com.onmyway.valueobject;

public class ContatoDTO {

    private String userId;
    private String email;
    private String cellphone;

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getCellphone() {
	return cellphone;
    }

    public void setCellphone(String cellphone) {
	this.cellphone = cellphone;
    }

}
