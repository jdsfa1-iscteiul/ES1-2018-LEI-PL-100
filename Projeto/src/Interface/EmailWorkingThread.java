package Interface;

import java.io.*;
import java.sql.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailWorkingThread extends Thread{
	
	private String host = "pop-mail.outlook.com";
	private String user = "projetoes1@outlook.pt";
	private String password = "Sportingcampeao";
	private Object locker;
	private ObjectOutputStream oos;
	
	public EmailWorkingThread(Object locker, ObjectOutputStream oos) {
		this.locker = locker;
		this.oos=oos;
	}
	
	public void run() {
		receive();
	}
	public void send(String from, String dst) {
			
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp-mail.outlook.com");
			props.put("mail.smtp.port", "587");
		
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			  });
		
			try {
		
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(dst));
				message.setSubject("Testing Subject");
				message.setText("Dear Mail Crawler,"
					+ "\n\n No spam to my email, please!");
		
				Transport.send(message);
		
				System.out.println("Done");
		
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
	}	
	
	public void receive() {
		 try {
		      //create properties field
		      Properties properties = new Properties();
		      
		      
		      properties.put("mail.pop3.host", host);
		      properties.put("mail.pop3.port", "995");
		      properties.put("mail.pop3.starttls.enable", "true");
		      Session emailSession = Session.getDefaultInstance(properties);
		  
		      //create the POP3 store object and connect with the pop server
		      Store store = emailSession.getStore("pop3s");

		      store.connect(host, user, password);

		      //create the folder object and open it
		      Folder emailFolder = store.getFolder("INBOX");
		      emailFolder.open(Folder.READ_ONLY);

		      // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		      
		      

		      for (int i = 0, n = messages.length; i < n; i++) {
		         Message message = messages[i];
		         Notification notification = new Notification("Email", message.getFrom()[0].toString(), (Date) message.getReceivedDate(),  message.getContent().toString());
		         synchronized(locker) {
		        	 this.oos.writeObject(notification);
		         }
		         
		         System.out.println("---------------------------------");
		         System.out.println("Email Number " + (i + 1));
		         System.out.println("Subject: " + message.getSubject());
		         System.out.println("From: " + message.getFrom()[0]);
		         System.out.println("Text: " + message.getContent().toString());

		      }

		      //close the store and folder objects
		      emailFolder.close(false);
		      store.close();

		      } catch (NoSuchProviderException e) {
		         e.printStackTrace();
		      } catch (MessagingException e) {
		         e.printStackTrace();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
	}
	
//	public static void main(String[] args) {
//		Object c = null;
//		EmailWorkingThread ewt = new EmailWorkingThread(c);
//		ewt.receive();
//	}
}
