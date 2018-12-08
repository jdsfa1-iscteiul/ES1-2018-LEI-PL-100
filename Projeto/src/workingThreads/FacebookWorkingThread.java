package workingThreads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;

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
	
	private String token;

	
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
		try {
			PersonalInformation pi = reader.XMLtoPersonInf();
			this.token = pi.getFacebookToken();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		@SuppressWarnings("deprecation")
		FacebookClient fb = new DefaultFacebookClient(this.token);

		Connection<Group> groups = fb.fetchConnection("me/groups", Group.class);

		for(List<Group> groupPages: groups) {
			for(Group g: groupPages) {
				if (g.getName().equals("LEI PL ES1")) {
					idGrupoIscte = g.getId();
				}
			}
		}

		Connection<Post> posts = fb.fetchConnection(idGrupoIscte+"/feed", Post.class);

		for (List<Post> postPages: posts) {
			for(Post post: postPages) {
				String message = post.getMessage();		
				if (message != null) {
					Notification notification = new Notification("FACEBOOK", post.getUpdatedTime(), message);
					synchronized(locker) {
						try {
							this.oos.writeObject(notification);
						} catch (IOException e) {

						}
					}
				}	
			}
		}	

	}
}
