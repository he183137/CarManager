package com.car.main;

import java.util.List;

import com.car.dao.DBHelper;
import com.car.pojo.CarInfo;
import com.car.view.CarInfoOverVierController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Stage primaryStage;
	
	private ObservableList<CarInfo> carInfosData = FXCollections.observableArrayList();
	
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
	}
   
	
	private void showMain(){
		
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/CarOverview.fxml"));
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
		launch(args);
	}
}
