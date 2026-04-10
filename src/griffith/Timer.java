package griffith;

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

}
