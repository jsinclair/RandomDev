package villagesim.villagethreads;

import java.util.concurrent.Semaphore;

public abstract class AbstractVillage implements Runnable {
	// Tracks whether the village thread is running or not.
	private boolean running = false;
	
	// A Semaphore to tract whether the thread has started or not.
	private Semaphore startedSemaphore;
	
	public AbstractVillage(Semaphore startedSemaphore) {
		this.startedSemaphore = startedSemaphore;
	}
	
	/**
	 * User to query whether or not the thread is running.
	 * @return whether or not the village thread is running.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Tell the village thread to stop running. It will finish it's current iteration and proceed with cleanup.
	 */
	public void stopRunning() {
		running = false;
	}
	
	/**
	 * Setup method, called before the loop starts.
	 */
	abstract protected void preLoop();
	
	/**
	 * Put the logic to run on each loop in the implementation of this method.
	 */
	abstract protected void onLoop();
	
	/**
	 * Called after the last loop is run, put anything to save or clean up in here/
	 */
	abstract protected void onFinishedRunning();
	
	public void run() {
		startedSemaphore.release();
		
		if (!running) {
			running = true;
			
			preLoop();
			
			while (running) {
				onLoop();
			}
			
			onFinishedRunning();
		}
		else {
			System.out.println("This village thread was already running!");
		}
	}
}
