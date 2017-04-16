package com.car.main;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.car.helper.DBHelper;
import com.car.pojo.CarInfo;
import com.car.pojo.SendConfPojo;
import com.car.service.SmsThread;
import com.car.view.CarInfoEditDialogController;
import com.car.view.CarInfoOverVierController;
import com.car.view.MsgDialogController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Stage primaryStage;
	
	private ObservableList<CarInfo> carInfosData = FXCollections.observableArrayList();
	private static boolean   isDebug = false;
	
	private static Logger logger  = Logger.getLogger(Main.class);
	private DBHelper dbHelper;
	public Main () throws Exception {
		
		dbHelper = DBHelper.getInstance();
		List<CarInfo> list = dbHelper.getAllCarInfo();
		carInfosData.addAll(list);
	}
	
	public ObservableList<CarInfo> getCarInfosData() {
		return carInfosData;
	}


	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("车检管理系统"); 
		showMain();
		logger.info("GUI OK");
		new SmsThread().start();
	}
	
	
	public boolean showCarInfoEditDialog(CarInfo carInfo,String actionName) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        if (isDebug){
	        	  loader.setLocation(Main.class.getResource("../view/CarEditDialog.fxml"));
	        }else{
		        loader.setLocation(Main.class.getResource("/view/CarEditDialog.fxml"));
	        }

	      
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle(actionName);
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        CarInfoEditDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setCarInfo(carInfo);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean showMsgEditDialog(SendConfPojo sendConfPojo) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        if(isDebug){
	        	  loader.setLocation(Main.class.getResource("../view/MsgDialog.fxml"));

	        }else{
		        loader.setLocation(Main.class.getResource("/view/MsgDialog.fxml"));
	        }

	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("短信模板");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        MsgDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setSendConfPojo(sendConfPojo);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	private void showMain(){
		
		try {

			FXMLLoader loader = new FXMLLoader();
            if (isDebug){
            	loader.setLocation(Main.class.getResource("../view/CarOverview.fxml"));
            }else{
    			loader.setLocation(Main.class.getResource("/view/CarOverview.fxml"));
            }

			AnchorPane personOverview = (AnchorPane) loader.load();
			Scene scene = new Scene(personOverview);
			CarInfoOverVierController carInfoOverVierController = loader.getController();
			carInfoOverVierController.setMain(this);
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public static void main(String[] args) {
		if(args[0].equals("debug")){
			isDebug = true;
		}
		launch(args);
	}
}
