package tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import controllers.TwitterGuiController;
import utils.Notification;

public class TwitterGuiControllerTest {
	
	private TwitterGuiController tgc;
	private Notification notification;
	
	@Before
	public void setUp() throws Exception {
		tgc = new TwitterGuiController();
		notification = new Notification("TWITTER", "", "", new Date(), "");
	}

	@Test
	public void setNotification() {
		tgc.setNotificationFromGUI(notification);
		assertEquals(notification, tgc.getNotification());
	}

}
