package main.java.async.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javafx.concurrent.Task;

public class UpdateTask extends Task<Boolean> {

  private int _id;
  
  private int _major;
  private int _minor;
  
  private boolean _result;
  
  public UpdateTask(int id, int major, int minor)
  {
    _id = id;
    _major = major;
    _minor = minor;
  }
  
  @Override
  protected Boolean call() throws Exception {
    
    System.out.println(String.format("[UpdateTask (%d)] Starting", _id));
    
    try {
      URL url = new URL("https://gist.githubusercontent.com/projectgoav/58b6e2d5f1f317eefe4f/raw");
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

      String s = reader.readLine();
      String path = reader.readLine();

      reader.close();

      int major = Integer.parseInt(String.valueOf(s.charAt(0)));
      int minor = Integer.parseInt(String.valueOf(s.charAt(2)));

      
       //TODO FIX THIS TO NOT TO AN AUTO UPDATE, JUST PERHAPS SO AN UPDATE WINDOW
      if ((major > _major || (minor > _minor))) {
        
        _result = true;
        
        
        //MatrixAlerts.showUpdates(s);
        //MatrixAlerts.showUpdateWarning();
        //// TODO Add in check for if updater.jar isn't actually there :(
        //Process p = Runtime.getRuntime().exec("java -jar Updater.jar" + path);
        //if (p.isAlive()) {
        //  System.exit(0);
        //}
      }
    } catch (Exception e) {
      System.out.println(String.format("[UpdateTask (%d)] Failed to check updates\n> Message: %s", _id, e.getMessage()));
    }
      
    System.out.println(String.format("[UpdateTask (%d)] Finsihing with result (%s)", _id, (_result) ? "True" : "False"));
    
    return _result;
  }

}
