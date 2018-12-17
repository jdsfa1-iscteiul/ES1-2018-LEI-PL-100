package tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import utils.Notification;

public class NotificationTest {
	
	private Notification notificationTwitter;
	private Notification notificationFacebook;
	private Notification notificationEmail;
	private Notification notificationNoPlatform;


	@Before
	public void setUp() throws Exception {
		notificationTwitter = new Notification("TWITTER", "", new Date(), "mensagem teste twitter", 0);
		notificationFacebook = new Notification("FACEBOOK", new Date(), "mensagem teste facebook");
		notificationEmail = new Notification("EMAIL", "", "mensagem teste email", new Date(), "");
		notificationNoPlatform = new Notification("TESTE", "", new Date(), "mensagem teste, plataforma não reconhecida");
		
	}

	@Test
	public void testToStringTwitter() {
		assertEquals("twitter: mensagem teste twitter", notificationTwitter.toString());
	}
	
	@Test
	public void testToStringFacebook() {
		assertEquals("facebook: mensagem teste facebook", notificationFacebook.toString());
	}
	
	@Test
	public void testToStringEmail() {
		assertEquals("email: mensagem teste email", notificationEmail.toString());
	}
	
	@Test
	public void testToStringNoPlatform() {
		assertEquals("mensagem teste, plataforma não reconhecida", notificationNoPlatform.toString());
	}

}