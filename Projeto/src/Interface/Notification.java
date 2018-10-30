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
	private Date date;
	private String Message;
	
	public Notification(String platform, String autor, Date date, String message) {
		super();
		this.platform = platform;
		this.autor = autor;
		this.date = date;
		this.Message = message;
	}
	
	public Notification(String platform, Date date, String message) {
		super();
		this.platform = platform;
		this.autor = "unknown";
		this.date = date;
		this.Message = message;
	}

	public Notification(String platform, String message) {
		this.platform = platform;
		this.Message = message;
	}	

	public String getPlatform() {
		return platform;
	}

	public String getAutor() {
		return autor;
	}

	public Date getDate() {
		return date;
	}

	public String getMessage() {
		return Message;
	}
	
	
	
}
