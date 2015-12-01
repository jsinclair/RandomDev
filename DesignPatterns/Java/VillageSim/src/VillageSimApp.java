import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import villagesim.villagethreads.AbstractVillage;
import villagesim.villagethreads.BasicVillage;

public class VillageSimApp {
	
	public static void main(String[] args) {
		System.out.println("Main village sim started.");
		
		Semaphore villageStartedSemaphore = new Semaphore(0);
		
		AbstractVillage villageThread = new BasicVillage(villageStartedSemaphore);
		
		Thread newThread = new Thread(villageThread);
		
		newThread.start();
		
		try {
			if (villageStartedSemaphore.tryAcquire(200, TimeUnit.MILLISECONDS)) {
				System.out.println("Village thread started!");
				
				while (villageThread.isRunning()) {
					
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Unable to run village thread: " + e.getMessage());
		}
		
		System.out.println("Main village sim finished.");
	}
}
