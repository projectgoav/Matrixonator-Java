package main.java.async;

/**
 * Defines a Task running in Matrixonator
 * @author BigE
 */
public interface ITask<T> extends Runnable, IResult<T> {

	/**
	 * Gets a value indicating if the task is currently running or not.
	 * @return
	 */
	boolean isRunning();
	
}
