package Interface;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
	
	public Button search_button;
	public TextField text_box;
	
	public void handleSearchButton () {
		System.out.println("Searching" + " for "+getBoxText() );
	}
	
	private String getBoxText() {
		return text_box.getText();
	}
}
