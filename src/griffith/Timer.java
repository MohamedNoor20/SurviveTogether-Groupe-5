package griffith;

public class Timer {
private long timerstarts;
public boolean isRunning = false;
	public Timer() {
		isRunning = false;
		
	}
	public void start() {
	    timerstarts = System.currentTimeMillis();
	    isRunning = true;
	}

}
