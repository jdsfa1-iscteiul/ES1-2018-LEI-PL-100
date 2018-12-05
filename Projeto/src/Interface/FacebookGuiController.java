package Interface;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class FacebookGuiController {
	
	@FXML
	public TextArea post_box;
	
	private Notification notification;
	
	public void getNotificationFromGUI(Notification notification) {
		this.notification = notification;
	}
	public void setPostText(Notification n) {
		post_box.setText(n.getMessage());
	}

	
	
}
