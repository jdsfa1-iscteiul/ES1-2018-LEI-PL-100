package workingThreads;

import java.io.IOException;
import java.io.ObjectOutputStream;

import controllers.MainController;
import javafx.application.Platform;

public class DistributorThread extends Thread {
		
		private MainController controller;
		private ObjectOutputStream oos;
		
		public DistributorThread(MainController controller, ObjectOutputStream oos) {
			super();
			this.controller = controller;
			this.oos = oos;
		}	
		
	
		public void run() {
			try {
				oos.reset();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Object locker = new Object();
			FacebookWorkingThread fbw = new FacebookWorkingThread(locker, this.oos);
			EmailWorkingThread ewt = new EmailWorkingThread(locker, this.oos);
			TwitterWorkingThread twt = new TwitterWorkingThread(locker, oos);
			fbw.start();
			ewt.start();
			twt.start();
			try {
				fbw.join();
				ewt.join();
				twt.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	try {
						controller.writeDataOnGui();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			});
		}
	
		
}
