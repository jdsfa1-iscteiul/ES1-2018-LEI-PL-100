package tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import controllers.EmailGuiController;
import utils.Notification;

public class EmailGuiControllerTest {

	private EmailGuiController emailGuiController;
	private Notification notification;
	
	@Before
	public void setUp() throws Exception {
		notification = new Notification("EMAIL", "", "", new Date(), "");
		emailGuiController = new EmailGuiController();
	}

	@Test
	public void test1() {
		try {
			emailGuiController.handleEmailSendButton();
			fail();
		} catch(Exception e) {
			assertNull(e.getMessage());
		}
	}

	@Test
	public void setNotificationTest() {
		emailGuiController.setNotificationFromGUI(notification);
		assertEquals(emailGuiController.getNotification(),notification);
	}
	
	@Test
	public void test2() {
		try {
			emailGuiController.setDestinationEmail(notification);
			fail();
		} catch( Exception e) {
			assertNull(e.getMessage());
		}
	}
}
