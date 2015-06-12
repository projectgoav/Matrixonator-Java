package main.java.view;

import java.io.File;
import java.net.MalformedURLException;

import main.java.Global;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.concurrent.Worker;

/**
 * Matrixonator HelpViewer. Displays the help Content to the User Serves the HTML help pages from
 * Matrixonator GitHub pages
 * 
 * @author BigE
 */
public class HelpController {

  @FXML
  // Lewis the WebBrowser Object :)
  private WebView lewis;

  @FXML
  private WebEngine ewan;

  @FXML
  private Stage viewer;


  @FXML
  /**
   * Setups the HelpView window and added a changed event to the engine,
   * this allowed listening for "special" urls where we want to open a
   * default browser window with more information to the user
   */
  public void initialize() {
    ewan = lewis.getEngine();

    ewan.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

      @SuppressWarnings("rawtypes")
      // Might need to fix this someday
      @Override
      public void changed(ObservableValue ov, State oldState, State newState) {

        if (newState == Worker.State.SUCCEEDED) {
          System.out.println("Loaded> " + ewan.getLocation());
          // TODO check if the location is a special external URL then open it in the default
          // browser
        }
      }
    });

    ewan.load(generateURL("Index.html"));
  }

  /**
   * Set to True is HelpView is already open
   */
  private static boolean isOpen = false;

  /**
   * Run method shows the Help Viewer to the user. Viewer can run side-by-side
   */
  public void run() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpViewer.fxml"));
      Parent helpPage = (Parent) fxmlLoader.load();
      viewer = new Stage();
      viewer.setTitle("Matrixonator - HelpViewer");
      viewer.setScene(new Scene(helpPage));
      //viewer.setWidth(505.0);
      //viewer.setHeight(500.0);
      viewer.setResizable(false);
      
      viewer.setOnCloseRequest(new EventHandler<WindowEvent>() {public void handle( WindowEvent we) { isOpen = false; }});
      
      isOpen = true;
      viewer.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets if Viewer is open
   * 
   * @return True is view is open
   */
  public boolean isOpen() {
    return isOpen;
  }

  @FXML
  /**
   * Closes the HelpViewer
   */
  public void handleClose() {
    isOpen = false;
    Stage s = (Stage) lewis.getScene().getWindow();
    s.close();
  }

  @FXML
  /**
   * Calls the Web Engine to refresh the current document
   */
  public void handleRefresh() {
    lewis.getEngine().reload();
  }

  @FXML
  /**
   * Gets the next item in the WebEngine history
   */
  public void handleForward() {
    try {
      ewan.getHistory().go(1);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Nothing to go forward to in Web History. Moving on...");
    }
  }

  @FXML
  /**
   * Gets the last item in the WebEngine history
   */
  public void handleBack() {
    try {
      ewan.getHistory().go(-1);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Nothing to go back to in Web History. Moving on...");
    }
  }

  @FXML
  /**
   * Return back to Index of help
   */
  public void handleHome() {
    ewan.load(generateURL("Index.html"));
  }

  @FXML
  /**
   * Navigates to the About Help page
   */
  public void handleAbout() {
    ewan.load(generateURL("About.html"));
  }


  /**
   * Turns the given HTML filename into a usable URL for WebEngine load()
   * 
   * @param webpage (ending in .html)
   * @return URL in String format of file. Returns null if invalid URL
   */
  private String generateURL(String webpage) {
    File newPage = new File ("help" + Global.PATH_SEP + webpage);
    try {
      return newPage.toURI().toURL().toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
