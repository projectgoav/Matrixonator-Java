package main.java.async.tasks;

import java.io.File;

import javafx.concurrent.Task;

public class WorkingDirectoryTask extends Task<String[]> {

  private int _id;
  private String[] _result;
  private char _sep;
  
  public WorkingDirectoryTask(int id, char seperator)
  {
     _id = id;
     _sep = seperator;
  }
  
  @Override
  protected String[] call() throws Exception {
    System.out.println(String.format("[WorkingDirectoryTask (%d)] Starting", _id)); 
    
    // Additional check to create proper path seperators per OS
    // WE MUST DO THIS FIRST. WONDER WHY THE HELL THIS EVEN WORKED ON LINUX BEFORE?!
    String tempLDir = "%Matrixonator";
    String tempMDir = "%Matrixonator%Matrix";
    
    String workingDir = System.getProperty("user.dir");
    String localDir = tempLDir.replace('%', _sep);
    String matrixDir = tempMDir.replace('%', _sep);
    String _success = "yes";


    // Checking for a working directory
    File BaseDirectory = new File( workingDir + localDir);
    if (!BaseDirectory.exists()) {

      try {
        if (!BaseDirectory.mkdir()) {
          System.out.println(String.format("[WorkingDirectoryTask (%d)] Failed to create LOCAL working directory", _id));
          _success = "no";
        }
      } catch (SecurityException e) {
          System.out.println(String.format("[WorkingDirectoryTask (%d)] Failed to create LOCAL working direct for security reasons.\nMessage:",_id, e.getMessage()));
          _success = "no";
      }
    }

    // Check for a directory to save matrix files to
    File MatrixDirectory = new File(workingDir + matrixDir);
    if (!MatrixDirectory.exists()) {

      try {
        if (!MatrixDirectory.mkdir()) {
          System.out.println(String.format("[WorkingDirectoryTask (%d)] Failed to create MATRIX working directory", _id));
          _success = "no";
        }
      } catch (SecurityException e) {
          System.out.println(String.format("[WorkingDirectoryTask (%d)] Failed to create MATRIX working direct for security reasons.\nMessage:",_id, e.getMessage()));
          _success = "no";
      }
    }
    
    _result = new String[] { localDir, matrixDir, _success};
    System.out.println(String.format("[WorkingDirectoryTask (%d)] Finishing with result (%s | %s | %s)", _id, _result[0], _result[1], _result[2])); 
    return _result;
  }

}
