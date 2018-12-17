package Interface;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.jupiter.api.Test;



import workingThreads.FacebookWorkingThread;

class FacebookWorkingThreadTest {
	
	private FacebookWorkingThread classUnderTest;
	
	@Before
	public void setUP() throws Exception{
		Object locker = new Object();
		FileOutputStream fo = new FileOutputStream(new File(".\\database.ser"));
		ObjectOutputStream oos = new ObjectOutputStream(fo);
		classUnderTest = new FacebookWorkingThread(locker, oos);
	}
	@Test
	void testStart() {
			
	}

	@Test
	void testFacebookWorkingThread() {
		fail("Not yet implemented");
	}

}
