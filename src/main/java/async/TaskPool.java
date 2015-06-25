package main.java.async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Task pool to run Abstract Tasks from. Can specify the degree of parallelism that 
 * this pool supports via constructor
 * @author BigE
 *
 */
public class TaskPool {

	private ThreadPoolExecutor _pool;
	
	private final static int _defaultPoolSize = 2;
	private final static int _defaultMaxPoolSize = 4;
	private final static int _defaultQueueSize = 50;
	
	/**
	 * Create a Task pool with default size, max size and queue size
	 */
	public TaskPool()
	{
		this(_defaultPoolSize, _defaultMaxPoolSize, _defaultQueueSize);
	}
	
	/**
	 * Create a Task pool with custom sizes
	 * @param poolSize - Initial number of Task workers
	 * @param maximumPoolSize - Maximum number of Task workers that operate at once
	 * @param queueSize - Maximum number of Tasks we can store in the queue
	 */
	public TaskPool(int poolSize, int maximumPoolSize, int queueSize)
	{
		_pool = new ThreadPoolExecutor(poolSize, maximumPoolSize, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
	}
	
	/**
	 * Adds a Task to be run in the Pool
	 * returns false if the Task is unable to be queued
	 */
	public boolean pool(Runnable r)
	{
		try
		{
			_pool.execute(r);
			return true;
		} 
		catch (RejectedExecutionException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Waits for the Task Pool to empty, timing out after 10seconds
	 * @param Block - Set to true if this method will block the calling thread
	 */
	public void Shutdown(boolean Block)
	{
		Shutdown();
		if(Block)
		{
			try {
				_pool.awaitTermination(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				System.out.println("[TaskPool] Awaiting Termination was interupted");
			}
		}
	}
	
	/**
	 * Default shutdown of Task Pool.
	 */
	public void Shutdown()
	{
		_pool.shutdown();	
	}
	
	/**
	 * Blindly shut the Task pool with no regard for remaining tasks or currently executing tasks.
	 */
	public void ShutdownwithForce()
	{
		//TODO add in collection of list
		_pool.shutdownNow();
	}
	
	/**
	 * Return pointer to instance of ThreadPool this wraps around.s
	 * @return
	 */
	public ThreadPoolExecutor getPool() { return _pool; }
}
