package Interface;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class EmailGuiController {
	
	@FXML
	public TextArea replying_email;
	
	public Notification notification;
	
	public void getNotificationFromGUI(Notification n) {
		this.notification = n;
	}
	public void setDestinationEmail(Notification notification) {
		replying_email.setText(notification.getAutor());
	}
}
