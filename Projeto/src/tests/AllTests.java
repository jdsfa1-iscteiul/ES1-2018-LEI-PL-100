package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ NotificationTest.class, MainControllerTest.class, EditGuiControllerTest.class, 
	DistributorThreadTest.class, EmailGuiControllerTest.class, FacebookGuiControllerTest.class,
	TwitterGuiControllerTest.class})
public class AllTests {

}
