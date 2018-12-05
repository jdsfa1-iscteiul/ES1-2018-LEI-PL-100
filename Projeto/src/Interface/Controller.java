package Interface;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
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
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;
import com.restfb.types.User;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller{

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

	ObservableList<Notification> list = FXCollections.observableArrayList();

	private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

	@FXML
	private Button home_button ;
	@FXML
	public Button search_button;
	@FXML
	public TextField text_box;
	@FXML
	public TextArea notifications_text_area;	

	@FXML
	private ListView<Notification> notifications_list;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	public void handleSearchButton () {
		System.out.println("Searching" + " for "+getBoxText() );

	}
	/**
	 *  Função temporária que serve para:
	 *  	1) chama as funçoes para abrir os canais de escrita/leitura na base de dados
	 *  	2) criar o cadeado que permite a sincronização entre as threads
	 *  	3) criar e lançar as threads que fazem a busca dos posts
	 *  	4) chama a função que escreve na interface as informações 
	 */

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
		
		writeDataOnGui();
	}

	/**
	 * Método para acionar os filtros de notificações
	 */
	public void handleConfirmButton() throws  InterruptedException, ClassNotFoundException, IOException{
		if(list.isEmpty()) {
			notifications_text_area.setText("Nenhuma mensagem para filtrar");
		}else {
			filter_list(list);
		}
	}
	/**
	 * Método que acede à lista de notificações e a filtra com base nos checkboxs selecionados
	 */
	public void filter_list(ObservableList<Notification> list_to_filter) {
		ObservableList<Notification> filtered_list = FXCollections.observableArrayList(); 
		for(int i=0; i< list_to_filter.size(); i++) {
			if(twitter_checkbox.isSelected() && list_to_filter.get(i).getPlatform().equals("TWITTER")) {
				filtered_list.add(list.get(i));
			}
			if(facebook_checkbox.isSelected() && list_to_filter.get(i).getPlatform().equals("facebook")) {
				filtered_list.add(list.get(i));
			}
			if(email_checkbox.isSelected() && list_to_filter.get(i).getPlatform().equals("EMAIL")) {
				filtered_list.add(list.get(i));
			}
		}
		notifications_list.getItems().clear();
		notifications_list.getItems().addAll(filtered_list);
	}
	/**
	 * 	Abrir os canais de escrita/leitura na base de dados
	 */
	private void openOStream() throws IOException {
		FileOutputStream fo = new FileOutputStream(new File(".\\database.ser"));
		this.oos=new ObjectOutputStream(fo); 	
	}

	private void openIStream() throws IOException {
		FileInputStream fi = new FileInputStream(new File(".\\database.ser"));
		this.ois=new ObjectInputStream(fi);	
	}

	/**
	 *  Obtem a palavra que o cliente quer procurar nas notificações 
	 */


	private String getBoxText() {
		return text_box.getText();
	}

	/**
	 * 	1) Chama a função que lê da base de dados
	 * 	2) Adiciona à interface a lista de notificações
	 */

	private void writeDataOnGui() throws IOException, ClassNotFoundException {
		loadNotifications();
		notifications_list.getItems().clear();
		notifications_list.getItems().addAll(list);
	}

	/**
	 *  Função que lê da bd
	 */

	private void loadNotifications() throws IOException, ClassNotFoundException{
		list.clear();
		while(true){
			try{
				list.add((Notification) this.ois.readObject());
			} catch (EOFException e){
				return;
			}
		}
	}
	@FXML
	private void displaySelected(MouseEvent event) {
		Notification notification = this.notifications_list.getSelectionModel().getSelectedItem();
		if (notification==null) {
			notifications_text_area.setText("Nenhuma notificação selecionada");
		} else {
			notifications_text_area.setText(notification.getMessage());
		}

	}

	private void replyButtonPressed() {

	}


	/**
	 * Método para inicializar as checkboxs
	 * Confirma se existe pelo menos um filtro seleciona
	 * Caso contrário desativa o confirm
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
