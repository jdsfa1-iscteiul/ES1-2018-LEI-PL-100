package tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import controllers.FacebookGuiController;
import utils.Notification;

public class FacebookGuiControllerTest {
	
	private FacebookGuiController fgc;
	private Notification notification;

	@Before
	public void setUp() throws Exception {
		fgc = new FacebookGuiController();
		notification = new Notification("FACEBOOK", "", new Date(), "");
	}

	@Test
	public void setNotificationTest() {
		fgc.setNotificationFromGUI(notification);
		assertEquals(notification, fgc.getNotification());
	}

	@Test
	public void test1() {
		try {
			fgc.setPostText(notification);
			fail();
		} catch(Exception e) {
			assertNull(e.getMessage());
		}
	}
}
