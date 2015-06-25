package main.java.async.tasks;

import javafx.concurrent.Task;

public class SimpleWaitTask extends Task<Void> {

	private int _id = 100;
	private int _sleep = 150;
	
	public SimpleWaitTask(int id, int sleepTime) {
		_sleep = sleepTime;
		_id = id;
	}

	@Override
	protected Void call() throws Exception {
		updateMessage("Running...");
		Thread.sleep(_sleep);
		updateMessage("Done!");
		System.out.println(String.format("[Task %d] Completed", _id));
		return null;	
	}


}
