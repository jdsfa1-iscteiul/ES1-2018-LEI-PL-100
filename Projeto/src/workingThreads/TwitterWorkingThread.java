package workingThreads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;


import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import utils.MyXMLReader;
import utils.Notification;
import utils.PersonalInformation;

/**
 * Classe para lidar com os pedidos da plataforma Facebook
 * 
 * @author Grupo100_LEI-PL
 *
 */
public class TwitterWorkingThread extends Thread {


	/**
	 * Objeto para implementar mecanismo de cadeado nas várias threads
	 */
	private Object locker;

	/**
	 * Canal de output
	 */
	private ObjectOutputStream oos;
	private String consumerKey;
	private String consumerSecret;
	private String acessToken;
	private String acessTokenSecret;


	/**
	 * Construtor
	 * @param locker
	 * @param oos
	 */
	public TwitterWorkingThread(Object locker, ObjectOutputStream oos) {
		this.locker=locker;
		this.oos=oos;
	}


	public TwitterWorkingThread() {
	}


	/**
	 * Método para ir buscar todos os posts no GRUPO ISCTE ao facebook do cliente
	 */
	public void start() {
		receive();
	}	


	private void receive() {
		MyXMLReader reader = new MyXMLReader();
		try {
			PersonalInformation pi = reader.XMLtoPersonInf();
			this.consumerKey = pi.getConsumerkey();
			this.consumerSecret = pi.getConsumersecret();
			this.acessToken = pi.getAcesstoken();
			this.acessTokenSecret = pi.getAcesstokensecret();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(false)
		.setOAuthConsumerKey(this.consumerKey)
		.setOAuthConsumerSecret(this.consumerSecret)
		.setOAuthAccessToken(this.acessToken)
		.setOAuthAccessTokenSecret(this.acessTokenSecret);

		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter4j.Twitter twitter = tf.getInstance();

		List<Status> status;
		try {
			status = twitter.getHomeTimeline();
			for(Status stat: status) {
				Notification notification = new Notification("TWITTER", stat.getUser().getScreenName(), stat.getCreatedAt(), stat.getText(), stat.getId());
				synchronized(locker) {
					this.oos.writeObject(notification);
				}
			}
		}
		catch (TwitterException | IOException e) {
			e.printStackTrace();
		}
	}

	public void send_tweet(Notification tweet_to_reply, String answer) {
		
		MyXMLReader reader = new MyXMLReader();
		try {
			PersonalInformation pi = reader.XMLtoPersonInf();
			this.consumerKey = pi.getConsumerkey();
			this.consumerSecret = pi.getConsumersecret();
			this.acessToken = pi.getAcesstoken();
			this.acessTokenSecret = pi.getAcesstokensecret();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(false)
		.setOAuthConsumerKey(this.consumerKey)
		.setOAuthConsumerSecret(this.consumerSecret)
		.setOAuthAccessToken(this.acessToken)
		.setOAuthAccessTokenSecret(this.acessTokenSecret);
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter4j.Twitter twitter = tf.getInstance();

		StatusUpdate status_answer =  new StatusUpdate(answer);
		status_answer.setInReplyToStatusId(tweet_to_reply.getIDPost());
		try {
			twitter.updateStatus(status_answer);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}



}

