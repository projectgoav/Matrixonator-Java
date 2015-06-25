package main.java.async;

/**
 * Defines and object which returns a result
 * @author BigE
 *
 * @param <T> Type of Result
 */
public interface IResult<T> {
	void report(T result);	
}
