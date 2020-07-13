package test;

import java.util.Timer;
import java.util.TimerTask;

public class TimeRunFunction extends TimerTask {
	private static int i = 0;
	private static Timer timer = new Timer();

	@Override
	public void run() {
		i++;
		System.out.println(i);
	}
	public TimeRunFunction() {
		timer.schedule(this, 0, 1000);
	}

	public static void main(String[] args) {
		new TimeRunFunction();
	}
}
