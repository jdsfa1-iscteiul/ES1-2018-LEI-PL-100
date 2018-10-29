package Interface;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	
	public void handleSearchButton () {
		System.out.println("Searching" + " for "+getBoxText() );
	}
	
	public void handleHomeButton() throws InterruptedException {
		FacebookWorkingThread fbw = new FacebookWorkingThread(text_posts);
		fbw.start();
		fbw.join();
		text_posts.setText("Ola, encontrámos estas mensagens nos teus grupos: \n");
		readDataAndWriteOnGui();
	}
	
	private String getBoxText() {
		return text_box.getText();
	}
	
	 public void readDataAndWriteOnGui() {
	        try {
	        	Scanner file = new Scanner (new File("C:\\Users\\jonic\\OneDrive - ISCTE-IUL\\FACULDADE\\ANO_03_SEMESTRE_1\\ES1-Repositorio\\ES1-2018-LEI-PL-100\\Projeto\\src\\Interface\\posts_database.txt"));
	 
	            String line;
	 
	            while (file.hasNextLine()) {
	            	line = file.nextLine();
	            	text_posts.appendText("\n" + line+"\n");
	            }
	            file.close();
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
