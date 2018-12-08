package utils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "PersonalInformation")
public class PersonalInformation {
	
	private String facebookToken;
	private String email;
	private String emaiPassword;
	private String consumerkey;
	private String consumersecret;
	private String acesstoken;
	private String acesstokensecret;
	
	
	public PersonalInformation() {
		this.facebookToken = null;
		this.email = null;
		this.emaiPassword = null;
		this.consumerkey = null;
		this.consumersecret = null;
		this.acesstoken = null;
		this.acesstokensecret = null;
	}
	
	public PersonalInformation(String facebookToken, String email, String emaiPassword, String consumerKey, String consumerSecret, String acesstoken, String acesstokensecret) {
		super();
		this.facebookToken = facebookToken;
		this.email = email;
		this.emaiPassword = emaiPassword;
		this.consumerkey = consumerKey;
		this.consumersecret = consumerSecret;
		this.acesstoken = acesstoken;
		this.acesstokensecret = acesstokensecret;
	}

	public String getFacebookToken() {
		return facebookToken;
	}
	
	@XmlElement(name = "facebookToken")
	public void setFacebookToken(String facebookToken) {
		this.facebookToken = facebookToken;
	}

	public String getEmail() {
		return email;
	}
	
	@XmlElement(name = "email")
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmaiPassword() {
		return emaiPassword;
	}
	
	@XmlElement(name = "emailPassword")
	public void setEmaiPassword(String emaiPassword) {
		this.emaiPassword = emaiPassword;
	}

	public String getConsumerkey() {
		return consumerkey;
	}

	public void setConsumerkey(String consumerkey) {
		this.consumerkey = consumerkey;
	}

	public String getConsumersecret() {
		return consumersecret;
	}

	public void setConsumersecret(String consumersecret) {
		this.consumersecret = consumersecret;
	}

	public String getAcesstoken() {
		return acesstoken;
	}

	public void setAcesstoken(String acesstoken) {
		this.acesstoken = acesstoken;
	}

	public String getAcesstokensecret() {
		return acesstokensecret;
	}

	public void setAcesstokensecret(String acesstokensecret) {
		this.acesstokensecret = acesstokensecret;
	}


	
	
	

}

