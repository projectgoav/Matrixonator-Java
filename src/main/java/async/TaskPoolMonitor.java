package main.java.async;

import java.util.concurrent.ThreadPoolExecutor;

public class TaskPoolMonitor implements Runnable {

	private ThreadPoolExecutor _targetPool;
	
	private boolean _isRunning =false;
	private boolean _cancellationRequested = false;
	
	//Default update duration of 3seconds
	private int _updateDuration = 3000;
	
	private Object _lock;
	private int _activeThreadCount = 0;
	private long _completedTasks = 0;
	private int _poolSize = 0;
	private int _queueSize = 0;
	private long _totalTasks = 0;
	
	public TaskPoolMonitor(ThreadPoolExecutor t, int updateDuration)
	{
		_targetPool = t;
		_updateDuration = updateDuration;
		_lock = new Object();
	}
	
	public TaskPoolMonitor(ThreadPoolExecutor t)
	{
		this(t, 3000);
	}
	
	@Override
	public void run() {		
		_isRunning = true;	
		while(!_cancellationRequested)
		{
			synchronized(_lock)
			{
				_activeThreadCount = _targetPool.getActiveCount();
				_completedTasks = _targetPool.getCompletedTaskCount();
				_poolSize = _targetPool.getPoolSize();
				_queueSize = _targetPool.getQueue().size();
				_totalTasks = _targetPool.getTaskCount();
			}
			try {
				Thread.sleep(_updateDuration);
			} catch (InterruptedException e) {
				System.out.println("[TaskPoolMonitor] Thread Interupted");
				continue;
			}
		}	
		_isRunning = false;
	}
	
	public void Shutdown()
	{
		_cancellationRequested = true;
	}
	
	public void Shutdown(boolean block)
	{
		Shutdown();
		
		while(_isRunning)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("[TaskPoolMonitor] Shutdown (BLOCK) was interupted.");
			}
		}
	}
	
	public Object getLock() { synchronized(_lock) { return _lock; }}
	public int activeThreadCount() { synchronized(_lock) { return _activeThreadCount; } }
	public long completedTaskCount() { synchronized(_lock) { return _completedTasks; } }
	public long pooledTaskCount() { synchronized(_lock) { return _totalTasks; } }
	public int poolSize() { synchronized(_lock) { return _poolSize; } }
	public int queueSize() { synchronized(_lock) { return _queueSize; }	}	
}
