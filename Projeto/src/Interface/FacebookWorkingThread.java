package Interface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;
import com.restfb.types.User;

import javafx.scene.control.TextArea;

public class FacebookWorkingThread extends Thread {
	
	private String idGrupoIscte = "";
	
	public void start() {
		String at = "EAAFBpVUIgNIBAFCvP5salaEZA2SdtFPkLxJykkiyHHzLSQgZCk3cl3fG4AzerwjM1mY6bXcnxVzxfavR8KhaKYZCkZB5aMRyXgSMAnelct7v7wzAdGcjVbC1CLZC9ZA3eZCUPUXm4PIPBAeZCbpKgmhbsPBmJLYphPdpm7kKzm2Y5EEfVsnkBNuy";
		@SuppressWarnings("deprecation")
		FacebookClient fb = new DefaultFacebookClient(at);

		User me = fb.fetchObject("me", User.class);
		System.out.println("Utilizador: \n" + me.getName() +"\n");

		Connection<Group> groups = fb.fetchConnection("me/groups", Group.class);

		for(List<Group> groupPages: groups) {
			for(Group g: groupPages) {
				if (g.getName().equals("LEI PL ES1")) {
					idGrupoIscte = g.getId();
					System.out.println("Grupo: " + g.getName() + "\n");
					
				}
			}
		}
		
		Connection<Post> posts = fb.fetchConnection(idGrupoIscte+"/feed", Post.class);
		
		try {
			FileOutputStream f = new FileOutputStream(new File(".\\database.ser"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			for (List<Post> postPages: posts) {
				for(Post post: postPages) {
					String message = post.getMessage();		
					if (message != null) {
						Notification n = new Notification("facebook", message);
						o.writeObject(n);		
					}	
				}
			}	
			
			o.close();
			f.close();
		} catch (IOException FileNotFoundException) {
			
		}
	}
}
