package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.MyXMLReader;
import utils.Notification;
import utils.PersonalInformation;
import workingThreads.EmailWorkingThread;

public class EmailGuiController {

	@FXML
	public TextArea replying_email;
	@FXML
	public TextField message_box;
	@FXML
	public TextField subject_box;
	@FXML
	public Button send_button;
	
	public Notification notification;

	public void setNotificationFromGUI(Notification n) {
		this.notification = n;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setDestinationEmail(Notification notification) {
		replying_email.setText(notification.getAutor());
	}
	
	/**
	 * Acionado pelo botão de enviar email
	 * Verifica se os campos de mensagem e de assunto não estão vazios
	 * Gera uma thread que irá enviar o email criado para o autor da mensagem selecionada
	 */
	public void handleEmailSendButton() {
		if(!message_box.getText().isEmpty() && !getNotification().getSubject().isEmpty()) {
			EmailWorkingThread sender_Thread = new EmailWorkingThread();
			MyXMLReader reader = new MyXMLReader();
			try {
				PersonalInformation pi = reader.XMLtoPersonInf();
				String email_sender = pi.getEmail();
				System.out.println(email_sender);
				sender_Thread.send(email_sender, replying_email.getText(), subject_box.getText(), message_box.getText() );
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			closeWindow();
		}
		else System.out.println("tente enviar outra vez");
	}
	
	public void closeWindow() {
	    Stage stage = (Stage) send_button.getScene().getWindow();
	    stage.close();
	}
}