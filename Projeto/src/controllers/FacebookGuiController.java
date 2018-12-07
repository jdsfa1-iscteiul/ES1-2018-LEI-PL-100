package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import utils.Notification;

public class FacebookGuiController {

	@FXML
	public TextArea post_box;
	@FXML
	public TextArea reply_box;
	@FXML
	public Button send_button;
	
	private Notification notification;

	public void setNotificationFromGUI(Notification notification) {
		this.notification = notification;
	}
	public void setPostText(Notification n) {
		post_box.setText(n.getMessage());
	}

	@SuppressWarnings("unused")
	private String getBoxText() {
		return reply_box.getText();
	}

	public Notification getNotification() {
		return this.notification;
	}
	
	
	@SuppressWarnings("unused")
	public void handleFacebookSendButton() {
		//String idPost = getNotification().getIDPost();
		closeWindow();
	}
	
	public void closeWindow() {
	    Stage stage = (Stage) send_button.getScene().getWindow();
	    stage.close();
	}
}
