package workingThreads;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controllers.MainController;
import javafx.application.Platform;

public class DistributorThread extends Thread {
		
		private MainController controller;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		public DistributorThread(MainController controller, ObjectOutputStream oos) {
			super();
			this.controller = controller;
			this.oos = oos;
		}	
		
	
		public void run() {
	
			Object locker = new Object();
			FacebookWorkingThread fbw = new FacebookWorkingThread(locker, this.oos);
			EmailWorkingThread ewt = new EmailWorkingThread(locker, this.oos);
			fbw.start();
			ewt.start();
			try {
				//fbw.join();
				ewt.join();	
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
