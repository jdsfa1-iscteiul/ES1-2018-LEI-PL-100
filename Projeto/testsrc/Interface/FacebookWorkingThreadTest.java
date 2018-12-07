package Interface;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;
import com.restfb.types.User;

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
