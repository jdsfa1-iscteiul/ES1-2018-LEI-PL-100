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
	public Button save_button;
	@FXML
	public TextArea consumerkey_box;
	@FXML
	public TextArea consumersecret_box;
	@FXML
	public TextArea acesstoken_box;
	@FXML
	public TextArea acesstokensecret_box;
	
	public void setOldDataOnBoxes() throws Exception {
		MyXMLReader reader = new MyXMLReader();
		PersonalInformation pi = reader.XMLtoPersonInf();
		
		this.facebook_token_box.setText(pi.getFacebookToken());
		this.emai_box.setText(pi.getEmail());
		this.email_password_box.setText(pi.getEmaiPassword());
		this.consumerkey_box.setText(pi.getConsumerkey());
		this.consumersecret_box.setText(pi.getConsumersecret());
		this.acesstoken_box.setText(pi.getAcesstoken());
		this.acesstokensecret_box.setText(pi.getAcesstokensecret());
		
		
	}
	
	public void handleSaveButton () {
		MyXMLReader reader = new MyXMLReader();
		PersonalInformation pi = new PersonalInformation(this.facebook_token_box.getText(),
														this.emai_box.getText(), 
														this.email_password_box.getText(),
														this.consumerkey_box.getText(),
														this.consumersecret_box.getText(),
														this.acesstoken_box.getText(),
														this.acesstokensecret_box.getText());
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
