package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import utils.MyXMLReader;
import utils.PersonalInformation;

public class EditGuiController {
	
	@FXML
	public TextArea facebook_token_box;	
	@FXML
	public TextArea emai_box;	
	@FXML
	public TextArea email_password_box;	
	@FXML
	public TextArea twitter_token_box;	
	@FXML
	public Button save_button;

	
	public void setOldDataOnBoxes() throws Exception {
		MyXMLReader reader = new MyXMLReader();
		PersonalInformation pi = reader.XMLtoPersonInf();
		
		this.facebook_token_box.setText(pi.getFacebookToken());
		this.emai_box.setText(pi.getEmail());
		this.email_password_box.setText(pi.getEmaiPassword());
		this.twitter_token_box.setText(pi.getTwitterToken());
		
		
	}
	
	public void handleSaveButton () {
		MyXMLReader reader = new MyXMLReader();
		PersonalInformation pi = new PersonalInformation(facebook_token_box.getText(), emai_box.getText(), email_password_box.getText(), twitter_token_box.getText());
		try {
			reader.personalInfToXMLExample(pi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeWindow();
	}
	
	public void closeWindow() {
	    Stage stage = (Stage) save_button.getScene().getWindow();
	    stage.close();
	}
	

}
