package Interface;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.List;

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
	
	public void handleHomeButton() {
		text_posts.setText("Ola, encontrámos estas mensagens nos teus grupos: \n");
		FacebookWorkingThread fbw = new FacebookWorkingThread(text_posts);
		fbw.start();
	}
	
	private String getBoxText() {
		return text_box.getText();
	}
}
