package main.java.async;

/**
 * Defines an object who gathers reports for Tasks<T> run on the TaskPool
 * @author BigE
 *
 */
public interface IReportAggregator {
	
	/**
	 * Notifies observer with the given type TSource
	 * @param result
	 * @param owner
	 */
	<TSource> void notify(TSource result, int owner);
	
}
