package main.java;

import main.java.async.TaskPool;
/**
 * Contains many properties and methods that are used globally throughout Matrixonator
 * 
 * @author Ewan
 *
 */
public class Global {
	
  public final int _vMajor = 1;
  public final int _vMinor = 0;
  
  private char _pathSep = ' ';
  private boolean _saveFlag = true;
  
  private String _localDir;
  private String _matrixDir;
  
  private TaskPool _pool;
 
  public Global()
  {
    _pool = new TaskPool(16,32,100);
  }
  

  public TaskPool getTaskPool() { return _pool; }

  public void setPathSeperator(char sep)
  {
    if(_pathSep == ' ') { _pathSep = sep; }
  }

  public char getPathSeperator()
  {
    return _pathSep;
  }
  
  public void setLocalDir(String path)
  {
    if(_localDir == null) { _localDir = path; }
  }
  
  public void setMatrixDir(String path)
  {
    if(_matrixDir == null) { _localDir = path; }
  }
  
  public String getLocalDir()
  {
    return _localDir;
  }
  
  public String getMatrixDir()
  {
    return _matrixDir;
  }
  
  public boolean getSaveFlag() { return _saveFlag; }
  
  public void setSaveFlag() { _saveFlag = false; }
  
  /**
   * Saves all matrices stored within the application
   * 
   * @return True if all files are saved successfully.
   */
  //public static boolean saveAllMatrices() {
//
  //  boolean saveResult = true;
//
  //  for (Matrix m : matrixData) {
  //    // AND all results together, so if we get at least one false, it'll say false.
  //    saveResult = saveResult && MatrixIO.save(m);
  //  }
   // return saveResult;
/// }

}
