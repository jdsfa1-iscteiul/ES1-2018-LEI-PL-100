package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import controllers.MainController;

public class MainControllerTest {
	private MainController mainController;

	@Before
	public void setUp() throws Exception {
		mainController = new MainController();
		
		
	}

	@Test
	public void test() throws ClassNotFoundException, InterruptedException, IOException {
		mainController.initialize();
		
	}

}
