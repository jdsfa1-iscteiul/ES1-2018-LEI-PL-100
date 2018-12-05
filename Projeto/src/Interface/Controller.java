package Interface;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

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
	
	@FXML
	private ObservableList<Notification> list = FXCollections.observableArrayList();

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
	


	/**
	 * Método iniciado automaticamente 
	 * Método para inicializar as checkboxs
	 * Confirma se existe pelo menos um filtro seleciona
	 * Caso contrário desativa o confirm
	 */
	public void initialize() {
		
		try {
			openIStream();
			openOStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
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
	 * 	Abrir os canais de escrita na base de dados
	 */
	private void openOStream() throws IOException {
		FileOutputStream fo = new FileOutputStream(new File(".\\database.ser"));
		this.oos=new ObjectOutputStream(fo); 	
	}
	
	/**
	 * 	Abrir os canais de leitura na base de dados
	 */
	private void openIStream() throws IOException {
		FileInputStream fi = new FileInputStream(new File(".\\database.ser"));
		this.ois=new ObjectInputStream(fi);	
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
			else if(facebook_checkbox.isSelected() && list_to_filter.get(i).getPlatform().equals("facebook")) {
				filtered_list.add(list.get(i));
			}
			else if(email_checkbox.isSelected() && list_to_filter.get(i).getPlatform().equals("EMAIL")) {
				filtered_list.add(list.get(i));
			}
		}
		notifications_list.getItems().clear();
		notifications_list.getItems().addAll(filtered_list);
	}

	
	/**
	 *  Função temporária que serve para:
	 *  	1) chama as funçoes para abrir os canais de escrita/leitura na base de dados
	 *  	2) criar o cadeado que permite a sincronização entre as threads
	 *  	3) criar e lançar as threads que fazem a busca dos posts
	 *  	4) chama a função que escreve na interface as informações 
	 */

	public void handleHomeButton() throws InterruptedException, ClassNotFoundException, IOException {
		DistributorThread dist = new DistributorThread(this, this.oos);
		dist.start();
	}

	/**
	 * 	1) Chama a função que lê da base de dados
	 * 	2) Adiciona à interface a lista de notificações
	 */

	public void writeDataOnGui() throws IOException, ClassNotFoundException {
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
			String enterKey = System.getProperty("line.separator");
			notifications_text_area.setText(notification.getMessage() + enterKey + enterKey + "Date: " + notification.getDate());
		}

	}

	public void handleReplyButton() {
		Notification notification = this.notifications_list.getSelectionModel().getSelectedItem();
		if (notification == null) {
			notifications_text_area.setText("Nenhuma notificação selecionada. Selecione uma primeiro.");
		}
		else if (notification.getPlatform().equals("EMAIL")) {
			try {
				Parent root= FXMLLoader.load(getClass().getResource("email_reply.fxml"));
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Email Reply");
				stage.show();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if (notification.getPlatform().equals("TWITTER")) {
			try {
				Parent root= FXMLLoader.load(getClass().getResource("twitter_reply.fxml"));
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Twitter Reply");
				stage.show();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if (notification.getPlatform().equals("FACEBOOK")) {
			try {
			
				Parent root= FXMLLoader.load(getClass().getResource("facebook_reply.fxml"));
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Facebook Reply");
				stage.show();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			
		}
		
		
	}

	/**
	 *  Obtem a palavra que o cliente quer procurar nas notificações 
	 */


	private String getBoxText() {
		return text_box.getText();
	}
	
	public void handleSearchButton () {
		System.out.println("Searching" + " for "+getBoxText() );

	}

	


}
