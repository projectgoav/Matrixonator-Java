package main.java.async;

/**
 * Abstract Task that all other Tasks should inherit off.
 * Allows a task to return a generic result to the associated observer.
 * @author BigE
 *
 * @param <T> Return Type of Task
 */
public abstract class AbstractTask<T> implements ITask<T> {

	private IReportAggregator _observer;
	boolean _isRunning;
	private T _result;
	private int _id;
	
	public AbstractTask(IReportAggregator o, int id)
	{
		_observer = o;
		_isRunning = false;
		_result = null;
		_id = id;
	}
	
	@Override
	public void run()
	{
		_isRunning = true;
		execute();
		report(_result);
		_isRunning = false;
	}
	
	public abstract void execute();
	
	protected void setResult(T result) { _result = result; }

	@Override
	public void report(T result) {
	   _observer.notify(result, _id);
	}

	@Override
	public boolean isRunning() {
		return _isRunning;
	}

}
