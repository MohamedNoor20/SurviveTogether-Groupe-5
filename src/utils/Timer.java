package utils;

public class Timer {
private long timerStarts;
public boolean running = false;
	public Timer() {
		running = false;
		
	}
	public void start() {
	    timerStarts = System.currentTimeMillis();
	    running = true;
	}
	public void stop() {
	    running = false;
	
}
	public int getSeconds() {
	    if (!running) return 0;
	    return (int)((System.currentTimeMillis() - timerStarts) / 1000);
	}

}
