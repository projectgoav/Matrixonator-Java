package main.java.view;

import java.util.ArrayList;

import main.java.MainApp;
import main.java.async.TaskPool;
import main.java.async.tasks.SimpleWaitTask;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TestController {

	@FXML
	protected Label Label00;
	@FXML
	protected Label Label01;
	@FXML
	protected Label Label02;
	@FXML
	protected Label Label03;
	@FXML
	protected Label Label04;
	@FXML
	protected Label Label10;
	@FXML
	protected Label Label11;
	@FXML
	protected Label Label12;
	@FXML
	protected Label Label13;
	@FXML
	protected Label Label14;
	@FXML
	protected Label Label20;
	@FXML
	protected Label Label21;
	@FXML
	protected Label Label22;
	@FXML
	protected Label Label23;
	@FXML
	protected Label Label24;
	@FXML
	protected Label Label30;
	@FXML
	protected Label Label31;
	@FXML
	protected Label Label32;
	@FXML
	protected Label Label33;
	@FXML
	protected Label Label34;

	private TaskPool aPool;
	private MainApp _app;
	
	@FXML
	public void initialize()
	{
		System.out.println("> Starting up...");
		aPool = new TaskPool(16,32,100);
		startTasks();
	}
		
	private void startTasks()
	{
		System.out.println("> Adding tasks...");
		
		@SuppressWarnings("serial")
		ArrayList<Task<Void>> tasks = new ArrayList<Task<Void>>()
		{{
			add(new SimpleWaitTask(0, 550));
		    add(new SimpleWaitTask(1, 156));
		    add(new SimpleWaitTask(2, 100));
		    add(new SimpleWaitTask(3, 100));
		    add(new SimpleWaitTask(4, 1500));
		    add(new SimpleWaitTask(10, 350));
		    add(new SimpleWaitTask(11, 150));
		    add(new SimpleWaitTask(12, 550));
		    add(new SimpleWaitTask(13, 150));
		    add(new SimpleWaitTask(14, 100));
		    add(new SimpleWaitTask(20, 150));
		    add(new SimpleWaitTask(21, 350));
		    add(new SimpleWaitTask(22, 150));
		    add(new SimpleWaitTask(23, 100));
		    add(new SimpleWaitTask(24, 150));
		    add(new SimpleWaitTask(30, 350));
		    add(new SimpleWaitTask(31, 1500));
		    add(new SimpleWaitTask(32, 150));
		    add(new SimpleWaitTask(33, 250));
		    add(new SimpleWaitTask(34, 1500));
		}};
		
		Label00.textProperty().bind(tasks.get(0).messageProperty());
		Label01.textProperty().bind(tasks.get(1).messageProperty());
		Label02.textProperty().bind(tasks.get(2).messageProperty());
		Label03.textProperty().bind(tasks.get(3).messageProperty());
		Label04.textProperty().bind(tasks.get(4).messageProperty());
		Label10.textProperty().bind(tasks.get(5).messageProperty());
		Label11.textProperty().bind(tasks.get(6).messageProperty());
		Label12.textProperty().bind(tasks.get(7).messageProperty());
		Label13.textProperty().bind(tasks.get(8).messageProperty());
		Label14.textProperty().bind(tasks.get(9).messageProperty());
		Label20.textProperty().bind(tasks.get(10).messageProperty());
		Label21.textProperty().bind(tasks.get(11).messageProperty());
		Label22.textProperty().bind(tasks.get(12).messageProperty());
		Label23.textProperty().bind(tasks.get(13).messageProperty());
		Label24.textProperty().bind(tasks.get(14).messageProperty());
		Label30.textProperty().bind(tasks.get(15).messageProperty());
		Label31.textProperty().bind(tasks.get(16).messageProperty());
		Label32.textProperty().bind(tasks.get(17).messageProperty());
		Label33.textProperty().bind(tasks.get(18).messageProperty());
		Label34.textProperty().bind(tasks.get(19).messageProperty());

		

		System.out.println("> Inserting into pool...");
		for(Task<Void> t : tasks)
		{
			boolean ok = aPool.pool(t);
			if(!ok) { 
				System.out.println("Unable to add Task to Pool");
			}
		}	
	}


	public void setMainApp(MainApp mainApp) {
		_app = mainApp;	
	}
	
	
	
	
	
}
