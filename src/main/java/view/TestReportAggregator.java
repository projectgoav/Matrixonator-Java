package main.java.view;

import javafx.fxml.FXML;
import main.java.async.IReportAggregator;

public class TestReportAggregator implements IReportAggregator {

	TestController aController;
	
	public TestReportAggregator(TestController controller)
	{
		aController = controller;
	}

	@Override
	@FXML
	public <TSource> void notify(TSource result, int owner) {

		switch(owner)
		{
		case 0:  { aController.Label00.setText("Task Complete"); break; }
		case 1:  { aController.Label01.setText("Task Complete"); break; }
		case 2:  { aController.Label02.setText("Task Complete"); break; }
		case 3:  { aController.Label03.setText("Task Complete"); break; }
		case 4:  { aController.Label04.setText("Task Complete"); break; }
		case 10: { aController.Label10.setText("Task Complete"); break; }
		case 11: { aController.Label11.setText("Task Complete"); break; }
		case 12: { aController.Label12.setText("Task Complete"); break; }
		case 13: { aController.Label13.setText("Task Complete"); break; }
		case 14: { aController.Label14.setText("Task Complete"); break; }
		case 20: { aController.Label20.setText("Task Complete"); break; }
		case 21: { aController.Label21.setText("Task Complete"); break; }
		case 22: { aController.Label22.setText("Task Complete"); break; }
		case 23: { aController.Label23.setText("Task Complete"); break; }
		case 24: { aController.Label24.setText("Task Complete"); break; }
		case 30: { aController.Label30.setText("Task Complete"); break; }
		case 31: { aController.Label31.setText("Task Complete"); break; }
		case 32: { aController.Label32.setText("Task Complete"); break; }
		case 33: { aController.Label33.setText("Task Complete"); break; }
		case 34: { aController.Label34.setText("Task Complete"); break; }
		default: { System.out.println("[TestReportAggregator] Unknown Thread reported (ID: " + owner + ")"); break; }
		}
		
	}
	
}
