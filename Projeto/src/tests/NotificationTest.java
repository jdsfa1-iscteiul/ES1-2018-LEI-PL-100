package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.Notification;

class NotificationTest {
	
	private Notification notificationTwitter;
	private Notification notificationFacebook;
	private Notification notificationEmail;
	private Notification notificationNoPlatform;


	@BeforeEach
	void setUp() throws Exception {
		notificationTwitter = new Notification("TWITTER",new Date(), "mensagem teste twitter");
		notificationFacebook = new Notification("FACEBOOK", new Date(), "mensagem teste facebook");
		notificationEmail = new Notification("EMAIL", new Date(), "mensagem teste email");
		notificationNoPlatform = new Notification("TESTE", new Date(), "mensagem teste, plataforma não reconhecida");
		
	}

	@Test
	void testToStringTwitter() {
		assertEquals("twitter: mensagem teste twitter", notificationTwitter.toString());
	}
	
	@Test
	void testToStringFacebook() {
		assertEquals("facebook: mensagem teste facebook", notificationFacebook.toString());
	}
	
	@Test
	void testToStringEmail() {
		assertEquals("email: mensagem teste email", notificationEmail.toString());
	}
	
	@Test
	void testToStringNoPlatform() {
		assertEquals("mensagem teste, plataforma não reconhecida", notificationNoPlatform);
	}

}
