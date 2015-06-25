package main.java.view;

import java.util.Optional;

import org.controlsfx.dialog.Wizard;
import org.controlsfx.dialog.Wizard.WizardPane;

import main.java.MainApp;
import main.java.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Main handler class for GUI elements in Matrixonator.
 * 
 * @author Isaac Jordan
 */
public class MatrixOverviewController {

  @FXML
  private TableView<Matrix> matrixTable;
  @FXML
  private TableColumn<Matrix, String> nameColumn;
  @FXML
  private TableColumn<Matrix, Integer> numRowsColumn;
  @FXML
  private TableColumn<Matrix, Integer> numColsColumn;

  @FXML
  private Label nameLabel;
  @FXML
  private Label numRowsLabel;
  @FXML
  private Label numColsLabel;
  @FXML
  private Label createdDateLabel;

  /**
   * The data as an observable list of matrices.
   */
  private static ObservableList<Matrix> matrixData = FXCollections.observableArrayList();
  
  
  // Reference to the main application.
  @SuppressWarnings("unused")
  private MainApp mainApp;

  /**
   * The constructor. The constructor is called before the initialise() method.
   */
  public MatrixOverviewController() {}

  /**
   * Initialises the controller class. This method is automatically called after the fxml file has
   * been loaded.
   */
  @FXML
  private void initialize() {
    // Initialise the person table with the two columns.
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

    // Not typesafe
    numRowsColumn.setCellValueFactory(new PropertyValueFactory<Matrix, Integer>("numRows"));
    numColsColumn.setCellValueFactory(new PropertyValueFactory<Matrix, Integer>("numCols"));

    // Clear matrix details.
    showMatrixDetails(null);
  }

  /**
   * Handler when MatrixOverview has been brought back into focus
   */
  private void updateMatrixList() {
    matrixTable.setItems(matrixData);
  }

  /**
   * Is called by the main application to give a reference back to itself.
   * 
   * @param mainApp
   */
  public void setMainApp(MainApp mainApp) {
    this.mainApp = mainApp;

    // Add observable list data to the table
    matrixTable.setItems(matrixData);
    
    // Listen for selection changes and show the person details when
    // changed.
    matrixTable.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> showMatrixDetails(newValue));
  }

  /**
   * This method is called when the listener detects that a matrix was selected on the left hand
   * table. It updates the labels on the right-hand side of the GUI.
   * 
   * @param matrix
   */
  private void showMatrixDetails(Matrix matrix) {
    if (matrix != null) {
      nameLabel.setText(matrix.getName());
      numRowsLabel.setText(Integer.toString(matrix.getNumRows()));
      numColsLabel.setText(Integer.toString(matrix.getNumCols()));
      createdDateLabel.setText(matrix.getCreatedDate().toString());
    } else {
      nameLabel.setText("");
      numRowsLabel.setText("");
      numColsLabel.setText("");
      createdDateLabel.setText("");
    }
  }

  /**
   * Called when the user clicks on the new button. This method will guide the user through the
   * wizard that asks them to enter the matrix details.
   */
  @FXML
  private void handleNewMatrix() {
    // define pages to show

    Wizard wizard = new Wizard();
    wizard.setTitle("Create New Matrix");

    // --- page 1
    int row = 0;

    GridPane page1Grid = new GridPane();
    page1Grid.setVgap(10);
    page1Grid.setHgap(10);

    page1Grid.add(new Label("Name:"), 0, row);
    TextField txFirstName = createTextField("name", 80);
    page1Grid.add(txFirstName, 1, row++);

    page1Grid.add(new Label("Number of rows:"), 0, row);
    TextField txNumRows = createTextField("numRows", 80);
    page1Grid.add(txNumRows, 1, row++);

    page1Grid.add(new Label("Number of columns:"), 0, row);
    TextField txNumCols = createTextField("numCols", 80);
    page1Grid.add(txNumCols, 1, row);

    WizardPane page1 = new WizardPane();
    page1.setHeaderText("Please Enter Matrix Details");
    page1.setContent(page1Grid);
    
    TextField[] userData = new TextField[3];
    userData[0] = txFirstName;
    userData[1] = txNumRows;
    userData[2] = txNumCols;
    page1.setUserData(userData);

    // --- page 2

    final WizardPane page2 = new WizardPane() {
      @Override
      public void onEnteringPage(Wizard wizard) {
        int numRows = 0;
        int numCols = 0;
        try {
          numRows = Integer.parseInt((String) wizard.getSettings().get("numRows"));
          numCols = Integer.parseInt((String) wizard.getSettings().get("numCols"));
        } catch (NumberFormatException e) {
          MatrixAlerts.invalidRowColAlert();
          return;
        }

        GridPane page2Grid = new GridPane();
        for (int i = 0; i < numRows; i++) {
          for (int j = 0; j < numCols; j++) {
            // Naming of text fields needs to be improved
            TextField tx = createTextField("" + i + " " + j, 20);
            tx.setPromptText("Enter value");
            page2Grid.add(tx, j, i);

          }
        }
        page2Grid.setHgap(5);
        page2Grid.setVgap(10);
        setContent(page2Grid);

      }
    };
    page2.setHeaderText("Creating Matrix");

    // --- page 3
    WizardPane page3 = new WizardPane() {
      @Override
      public void onEnteringPage(Wizard wizard) {
        String name = (String) wizard.getSettings().get("name");

        int numRows = 0;
        int numCols = 0;
        try {
          numRows = Integer.parseInt((String) wizard.getSettings().get("numRows"));
          numCols = Integer.parseInt((String) wizard.getSettings().get("numCols"));
        } catch (NumberFormatException e) {
          MatrixAlerts.invalidRowColAlert();
          return;
        }

        double[][] data = new double[numRows][numCols];

        double currentData;

        for (int i = 0; i < numRows; i++) {
          for (int j = 0; j < numCols; j++) {
            String raw = (String) wizard.getSettings().get("" + i + " " + j);
            try {
              currentData = Double.valueOf(raw);
            } catch (NumberFormatException e) {
              currentData = 0;
            }

            data[i][j] = currentData;

          }
        }

        matrixData.add(new Matrix(name, data, null));
        updateMatrixList();

      }
    };
    page3.setHeaderText("Goodbye!");
    page3.setContentText("Matrix created.");
    
    Wizard.Flow branchingFlow = new Wizard.Flow() {
        public Optional<WizardPane> advance(WizardPane currentPage) {
            return Optional.of(getNext(currentPage));
        }

        public boolean canAdvance(WizardPane currentPage) {
            if (currentPage != page3) {
            	return true;
            }
            return false;
        }
             
        private WizardPane getNext(WizardPane currentPage) {
            if ( currentPage == null ) {
                return page1;
            } else if ( currentPage == page1) {
            	// Input validation for page 1
            	if (page1.getUserData() != null) {
            		System.out.println(((TextField[]) page1.getUserData())[0].getText());
            		try {
                        Integer.parseInt(((TextField[]) page1.getUserData())[1].getText());
                        Integer.parseInt(((TextField[]) page1.getUserData())[2].getText());
                      } catch (NumberFormatException e) {
                        MatrixAlerts.invalidRowColAlert();
                        return page1;
                      }
            		return page2;
            		
            	}
            	
            	return page2;
            } else {
                return page3;
            }
        }
    };
    
    wizard.setFlow(branchingFlow);

    // show wizard and wait for response
    wizard.showAndWait();
  }

  /**
   * Method is called when the "Edit" button is pressed. If a valid matrix is selected in the table
   * on the left, then it is deleted from the matrixTable.
   */
  @FXML
  private void handleEditMatrix() {
    int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
      // User has selected a valid matrix on the left.
      TextInputDialog dialog =
          new TextInputDialog(matrixTable.getSelectionModel().getSelectedItem().getName());
      dialog.setTitle("Editing Matrix");
      dialog.setHeaderText("Leave blank, or click cancel for no changes.");
      dialog.setContentText("Please enter new name:");

      Optional<String> result = dialog.showAndWait();

      result.ifPresent(name -> matrixTable.getSelectionModel().getSelectedItem().setName(name));
      
      updateMatrixList();

    } else {
      // Nothing is selected
      MatrixAlerts.noSelectionAlert();
    }

  }


  /**
   * Method is called when the "Delete" button is pressed. If a valid matrix is selected in the
   * table on the left, then it is deleted from the matrixTable.
   */
  @FXML
  public void handleDeleteMatrix() {
    int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
      Matrix m = matrixTable.getSelectionModel().getSelectedItem();

      // Prompt user if they want it removed completely
      boolean shallDelete = MatrixAlerts.handleDeleteRequest(m.getName());
      if (shallDelete) {
        MatrixIO.deleteFile(m.getName() + ".matrix");
        MatrixAlerts.showDelComplete(m.getName());
      } else {
        MatrixAlerts.showRemComplete(m.getName());
      }

      // TODO Remove misleading info if there is no such file existing

      matrixTable.getItems().remove(selectedIndex);

      // TODO Remove from Global as well (TEST)
      matrixData.remove(m);
      
      updateMatrixList();

    } else {
      // Nothing is selected
      MatrixAlerts.noSelectionAlert();
    }
  }

  @FXML
  private void handleShowData() {
    int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
      MatrixAlerts.dataAlert(matrixTable.getSelectionModel().getSelectedItem().normalise(), null);
    } else {
      // Nothing is selected
      MatrixAlerts.noSelectionAlert();
    }

  }

  @FXML
  private void handleCalculateRREF() {
    int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
      MatrixAlerts.dataAlert(
          matrixTable.getSelectionModel().getSelectedItem().reducedEchelonForm(), null);
    } else {
      // Nothing is selected
      MatrixAlerts.noSelectionAlert();
    }

  }

  @FXML
  private void handleCalculateDeterminant() {
    int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {

      Matrix m = matrixTable.getSelectionModel().getSelectedItem();

      if (m.getNumCols() != m.getNumRows()) {
        MatrixAlerts.showSquareWarning();
      } else {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Determinant of " + m.getName());
        alert.setHeaderText("Value displayed below.");
        alert.setContentText(String.valueOf(m.determinant()));
        alert.showAndWait();
      }
    } else {
      // Nothing is selected
      MatrixAlerts.noSelectionAlert();
    }
  }

  @FXML
  private void handleCalculateTrace() {
    int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {

      Matrix m = matrixTable.getSelectionModel().getSelectedItem();

      if (m.getNumCols() != m.getNumRows()) {
        MatrixAlerts.showSquareWarning();
      } else {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Trace of " + m.getName());
        alert.setHeaderText("Value displayed below.");
        alert.setContentText(String.valueOf(m.trace()));
        alert.showAndWait();
      }
    } else {
      MatrixAlerts.noSelectionAlert();
    }

  }

  @FXML
  private void handleCalculateInverse() {
    int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {

      Matrix m = matrixTable.getSelectionModel().getSelectedItem();

      if (m.getNumCols() != m.getNumRows()) {
        MatrixAlerts.showSquareWarning();
      } else {

        MatrixAlerts.dataAlert(m.inverse(), m.getName());
      }
    } else {
      // Nothing is selected
      MatrixAlerts.noSelectionAlert();
    }
  }

  @FXML
  private void handleCalculateCofactor() {
    int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {

      Matrix m = matrixTable.getSelectionModel().getSelectedItem();

      if (m.getNumCols() != m.getNumRows()) {
        MatrixAlerts.showSquareWarning();
      } else {

        MatrixAlerts.dataAlert(m.cofactorMatrix(), m.getName());
      }
    } else {
      // Nothing is selected
      MatrixAlerts.noSelectionAlert();
    }

  }

  @FXML
  // Handles when a save operation is requested. DOES NOT SAVE THE DEFAULT MATRICES
  private void handleSaveMatrix() {
    int selectedIndex = matrixTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
      // Do the save command
      Matrix data = matrixTable.getSelectionModel().getSelectedItem();

      // TODO Add proper message if save fails. (Which it should not)
      boolean result = MatrixIO.save(data);
      if (result) {
        MatrixAlerts.onSave();
      } else {
        System.out.println("Matrix file was not saved correctly");
      }
    } else {
      // Nothing is selected
      MatrixAlerts.noSelectionAlert();
    }
  }


  /**
   * A utility method for creating TextFields with specified id and width.
   * 
   * @param id
   * @param width
   * @return
   */
  private TextField createTextField(String id, int width) {
    TextField textField = new TextField();
    textField.setId(id);
    textField.setPrefWidth(width);
    GridPane.setHgrow(textField, Priority.ALWAYS);
    return textField;
  }
}
