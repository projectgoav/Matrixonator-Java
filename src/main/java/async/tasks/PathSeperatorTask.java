package main.java.async.tasks;

import javafx.concurrent.Task;

/**
 * Detects the OS and returns a Path seperator for matrixonator to use
 * @author BigE
 *
 */
public class PathSeperatorTask extends Task<Character> {

  private int _id;
  private char _result;
  
  public PathSeperatorTask(int id)
  {
    _id = id;
  }
  
  @Override
  protected Character call() throws Exception { 
    System.out.println(String.format("[PathSeperatorTask (%d)] Starting", _id));
    
    String osName = System.getProperty("os.name");
    if (osName.startsWith("Windows")) {
      _result = '\\';
    }
    else { _result = '/'; }
    
    System.out.println(String.format("[PathSeperatorTask (%d)] Finishing with result (%c)", _id, _result));
    updateValue(_result);
    succeeded();
    return _result;
  }

}
