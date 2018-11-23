package Interface;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

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
	
	public Button search_button;
	public TextField text_box;
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
}
