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

import javax.swing.JOptionPane;

import javafx.scene.control.TextArea;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import utils.Notification;

/**
 * Classe para lidar com os pedidos da plataforma Facebook
 * 
 * @author Grupo100_LEI-PL
 *
 */
public class TwitterWorkingThread extends Thread {

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
	public TwitterWorkingThread(Object locker, ObjectOutputStream oos) {
		this.locker=locker;
		this.oos=oos;
	}


	/**
	 * Método para ir buscar todos os posts no GRUPO ISCTE ao facebook do cliente
	 */
	public void start() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(false)
		.setOAuthConsumerKey("l30rxNB7Mgp26PqEcT07dpVkX")
		.setOAuthConsumerSecret("ehX5YAR5mPoZ6Tn4X5xk8s0TPfQ4y9UTJ1A4KvzzCb1bn3RSzB")
		.setOAuthAccessToken("1069679989111615491-DBWPNWnVWIpGyEn6ggfQLrCWffjvR9")
		.setOAuthAccessTokenSecret("dvbl54FXg9HNY4uYqNH6zjRDgLLVK3Ac6OvwqtxtLJPDo");

		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter4j.Twitter twitter = tf.getInstance();

		List<Status> status;
		try {
			status = twitter.getHomeTimeline();
			for(Status stat: status) {
				System.out.println("usuário: @" + stat.getUser().getScreenName() + " - " + stat.getText());
				Notification notification = new Notification("TWITTER", stat.getSource(), stat.getCreatedAt(), stat.getText());
				synchronized(locker) {
					this.oos.writeObject(notification);
				}
			}
		}
		catch (TwitterException | IOException e) {
			e.printStackTrace();
		}




	}

}
