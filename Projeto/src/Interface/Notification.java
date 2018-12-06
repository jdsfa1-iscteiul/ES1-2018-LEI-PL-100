package Interface;

import java.io.Serializable;
import java.sql.Date;

public class Notification implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String platform;
	private String autor;
	private java.util.Date date;
	private String message;
	private String subject;
	private String idPost;
	
	public Notification(String platform, String autor, String subject, java.util.Date date, String message) {
		super();
		this.platform = platform;
		this.autor = autor;
		this.date = date;
		this.message = message;
		this.subject = subject;
	}
	
	public Notification(String platform, java.util.Date date, String message) {
		super();
		this.platform = platform;
		this.autor = "unknown";
		this.date = date;
		this.message = message;
	}

	public String getPlatform() {
		return platform;
	}

	public String getAutor() {
		return autor;
	}

	public java.util.Date getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}
	public String getSubject() {
		return subject;
	}
	public String getIDPost() {
		return idPost;
	}
	protected void setID(String idPost) {
		this.idPost = idPost;
	}

	@Override
	public String toString() {
		switch(this.platform) {
		case "FACEBOOK":
			return "facebook: "+this.message;
		case "EMAIL":
			return "email: " +this.subject;
		case "TWITTER":
			return "twitter: "+this.message;
		default:
			return this.message;
		}
	}
}