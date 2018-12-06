package Interface;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FacebookGuiController {

	@FXML
	public TextArea post_box;
	@FXML
	public TextArea reply_box;
	private Notification notification;

	public void setNotificationFromGUI(Notification notification) {
		this.notification = notification;
	}
	public void setPostText(Notification n) {
		post_box.setText(n.getMessage());
	}

	private String getBoxText() {
		return reply_box.getText();
	}

	public Notification getNotification() {
		return this.notification;
	}
	public void handleFacebookSendButton() {
		String idPost = getNotification().getIDPost();
	}
}
