package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import utils.Notification;
import workingThreads.TwitterWorkingThread;

public class TwitterGuiController {
	
	@FXML
	public TextArea tweet_box;
	@FXML
	public TextArea reply_box;
	@FXML
	public Button send_button;
	
	private Notification notification;

	public void setNotificationFromGUI(Notification notification) {
		this.notification = notification;
	}
	public void setPostText(Notification n) {
		this.tweet_box.setText(n.getMessage());
	}	
	
	public void handleSendButton() {
		String answer = this.reply_box.getText();
		TwitterWorkingThread twt = new TwitterWorkingThread();
		
		twt.send_tweet(this.notification, answer);
		
		closeWindow();
			
	}
	
	public void closeWindow() {
	    Stage stage = (Stage) send_button.getScene().getWindow();
	    stage.close();
	}
}
