package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controllers.EditGuiController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class EditGuiControllerTest {

	private EditGuiController editGuiController;

	@Before
	public void setUp() throws Exception {
		editGuiController = new EditGuiController();

	}

	@Test
	public void test1() {
		try {
			editGuiController.setOldDataOnBoxes();
			fail();
		} catch (Exception e) {
			assertNull(e.getMessage());
		}
	}

	@Test
	public void test2() {
		try {
			editGuiController.handleSaveButton();
			fail();
		} catch (Exception e) {
			assertNull(e.getMessage());
		}
	}

	@Test
	public void test3() {
		try {
			editGuiController.handleSaveButton();
			fail();
		} catch (NullPointerException e) {
			assertNull(e.getMessage());
		}
	}
}
