package workingThreads;

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
import utils.MyXMLReader;
import utils.Notification;
import utils.PersonalInformation;

/**
 * Classe para lidar com os pedidos da plataforma Facebook
 * 
 * @author Grupo100_LEI-PL
 *
 */
public class FacebookWorkingThread extends Thread {

	/**
	 * Identificação do Grupo do ISCTE no Facebook
	 */
	private String idGrupoIscte = "";
	
	/**
	 * Objeto para implementar mecanismo de cadeado nas várias threads
	 */
	private Object locker;
	
	/**
	 * Canal de output
	 */
	private ObjectOutputStream oos;

	
	/**
	 * Construtor
	 * @param locker
	 * @param oos
	 */
	public FacebookWorkingThread(Object locker, ObjectOutputStream oos) {
		this.locker=locker;
		this.oos=oos;
	}
	
	
	/**
	 * Método para ir buscar todos os posts no GRUPO ISCTE ao facebook do cliente
	 */
	public void start() {
		MyXMLReader reader = new MyXMLReader();
		String token = new String();
		try {
			PersonalInformation pi = reader.XMLtoPersonInf();
			token = pi.getFacebookToken();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		@SuppressWarnings("deprecation")
		FacebookClient fb = new DefaultFacebookClient(token);

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

		for (List<Post> postPages: posts) {
			for(Post post: postPages) {
				String message = post.getMessage();		
				if (message != null) {
					Notification notification = new Notification("FACEBOOK", post.getUpdatedTime(), message);
					notification.setID(post.getId());
					synchronized(locker) {
						try {
							this.oos.writeObject(notification);
						} catch (IOException e) {

						}
						System.out.println(post.getMessage());
					}
				}	
			}
		}	

	}
}
