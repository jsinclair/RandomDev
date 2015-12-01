package villagesim.villagethreads;

import java.util.concurrent.Semaphore;

public class BasicVillage extends AbstractVillage {

	public static final int MILLIS_IN_A_SECOND = 1000;
	
	// The number of milliseconds since the last loop.
	private long millisSinceLastLoop = 0;
	
	// The system time at which the loop last ran.
	private long lastLoopTimeInMillis = 0;
	
	// Counts down till a second has passed.
	private long secondCoundDown = 0;
	
	public BasicVillage(Semaphore startedSemaphore) {
		super(startedSemaphore);
	}
	
	@Override
	protected void preLoop() {
		lastLoopTimeInMillis = System.currentTimeMillis();
		
		secondCoundDown = MILLIS_IN_A_SECOND;
	}
	
	@Override
	protected void onLoop() {
		long currentTimeMillis = System.currentTimeMillis();
		
		millisSinceLastLoop = currentTimeMillis - lastLoopTimeInMillis;
		
		lastLoopTimeInMillis = currentTimeMillis;
		
		secondCoundDown -= millisSinceLastLoop;
		
		//System.out.println("secondCoundDown: " + secondCoundDown + " " + MILLIS_IN_A_SECOND + " " + millisSinceLastLoop);
		
		while (secondCoundDown <= 0) {
			System.out.println("A second has passed!");
			
			secondCoundDown += MILLIS_IN_A_SECOND;
		}
	}

	@Override
	protected void onFinishedRunning() {
		System.out.println("Finished running!");
	}

}
