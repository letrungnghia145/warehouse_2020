package model;

public class Timer {
	public static long getExecuteTime(Functional functional) {
		long start = System.currentTimeMillis();
		functional.runScript();
		long end = System.currentTimeMillis();
		return (end - start);
	}
}
