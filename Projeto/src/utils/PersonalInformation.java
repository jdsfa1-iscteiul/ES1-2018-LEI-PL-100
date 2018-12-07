package utils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PersonalInformation")
public class PersonalInformation {
	
	private String facebookToken;
	private String email;
	private String emaiPassword;
	private String twitterToken;
	
	
	public PersonalInformation() {
		this.facebookToken = null;
		this.email = null;
		this.emaiPassword = null;
		this.twitterToken = null;
	}
	
	public PersonalInformation(String facebookToken, String email, String emaiPassword, String twitterToken) {
		super();
		this.facebookToken = facebookToken;
		this.email = email;
		this.emaiPassword = emaiPassword;
		this.twitterToken = twitterToken;
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

	public String getTwitterToken() {
		return twitterToken;
	}
	
	@XmlElement(name = "twitterToken")
	public void setTwitterToken(String twitterToken) {
		this.twitterToken = twitterToken;
	}
	
	
	

}

