package Interface;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

import java.awt.Checkbox;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;
import com.restfb.types.User;

import javafx.scene.control.TextField;

public class Controller {

	@FXML
	private CheckBox twitter_checkbox ;
	@FXML
	private CheckBox facebook_checkbox ;
	@FXML
	private CheckBox email_checkbox ;
	@FXML
	private Button confirm_button ;
	private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
	private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

	private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

	private final int maxNumSelected =  2; 

	private Button home_button ;
	@FXML
	public Button search_button;
	@FXML
	public TextField text_box;
	@FXML
	public TextArea text_posts;	

	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public void handleSearchButton () {
		System.out.println("Searching" + " for "+getBoxText() );

	}

	public void handleHomeButton() throws InterruptedException, ClassNotFoundException, IOException {
		openIStream();
		openOStream();
		Object locker = new Object();
		FacebookWorkingThread fbw = new FacebookWorkingThread(locker, this.oos);
		EmailWorkingThread ewt = new EmailWorkingThread(locker, this.oos);
		fbw.start();
		ewt.start();
		fbw.join();
		ewt.join();		

		text_posts.setText("Ola, encontrámos estas mensagens nos teus grupos: \n");
		writeDataOnGui();
	}
	
	public void handleConfirmButton() throws  InterruptedException, ClassNotFoundException, IOException{
		if(twitter_checkbox.isSelected()) {
			
		}
		if(facebook_checkbox.isSelected()) {
			
		}
		if(email_checkbox.isSelected()) {
			
		}
	}
	private void openOStream() throws IOException {
		FileOutputStream fo = new FileOutputStream(new File(".\\database.ser"));
		this.oos=new ObjectOutputStream(fo); 	
	}

	private void openIStream() throws IOException {
		FileInputStream fi = new FileInputStream(new File(".\\database.ser"));
		this.ois=new ObjectInputStream(fi);	
	}


	private String getBoxText() {
		return text_box.getText();
	}

	private ArrayList<Notification> loadNotifications() throws IOException, ClassNotFoundException{
		ArrayList<Notification> nots = new ArrayList<Notification>();
		while(true){
			try{
				nots.add((Notification) this.ois.readObject());
			} catch (EOFException e){
				return nots;
			}
		}
	}
	private void writeDataOnGui() throws IOException, ClassNotFoundException {
		ArrayList<Notification> nots = loadNotifications();
		for(Notification n:nots) {
			text_posts.appendText("\n" + "PLATAFORMA: " + n.getPlatform() + "\n");
			text_posts.appendText("MENSAGEM: " + n.getMessage() + "\n");
		}
	}


	/**
	 * Método para inicializar as checkboxs
	 */
	public void initialize() {
		configureCheckBox(twitter_checkbox);
		configureCheckBox(facebook_checkbox);
		configureCheckBox(email_checkbox);

		confirm_button.setDisable(true);

		numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
			System.out.println((newSelectedCount.intValue()));
			if (newSelectedCount.intValue() >= 1) {
				selectedCheckBoxes.forEach(cb -> cb.setDisable(false));
				confirm_button.setDisable(false);
			} else {
				selectedCheckBoxes.forEach(cb -> cb.setDisable(true));
				confirm_button.setDisable(true);
			}
		});
	}


	/**
	 * Método para configurar as checkboxs
	 */
	private void configureCheckBox(CheckBox checkBox) {

		if (checkBox.isSelected()) {
			selectedCheckBoxes.add(checkBox);
		} else {
			unselectedCheckBoxes.add(checkBox);
		}

		checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
			if (isNowSelected) {
				unselectedCheckBoxes.remove(checkBox);
				selectedCheckBoxes.add(checkBox);
			} else {
				selectedCheckBoxes.remove(checkBox);
				unselectedCheckBoxes.add(checkBox);
			}
			
		});
	}


}
