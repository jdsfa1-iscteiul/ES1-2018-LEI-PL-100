package tests;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

import controllers.MainController;
import workingThreads.DistributorThread;

public class DistributorThreadTest {
	
	private DistributorThread dT;

	@Before
	public void setUp() throws Exception {
		FileOutputStream fo = new FileOutputStream(".\\database.ser");
		dT = new DistributorThread(new MainController(), new ObjectOutputStream(fo));
	}

	@Test
	public void test() {
		try  {
		dT.run();
		fail();
		} catch(Exception e) {
			assertEquals("Toolkit not initialized", e.getMessage());
		}
	}
		

}
