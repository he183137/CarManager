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
	
	public Main () throws Exception {
		
		DBHelper dbHelper = new DBHelper();
		List<CarInfo> list = dbHelper.getAllCarInfo();
//		CarInfo car1 = new CarInfo();
//		car1.setC_car_id("J28785");
//		car1.setC_address("天津市红桥区三号路程光里12门515");
//		car1.setC_id("c26170d2-381b-4de2-b297-d48fd4d0b4b8");
//		car1.setC_identification_card("120104198511193516");
//		car1.setC_Inspection_expirationTime("2017-8-26");
//		//car1.setC_Insurance_expirationTime("2017-8-26");
//		car1.setC_name("贺鹏");
//		car1.setC_phone("13821478665");
		carInfosData.addAll(list);
//		carInfosData.add(car1);

	}
	
	public ObservableList<CarInfo> getCarInfosData() {
		return carInfosData;
	}


	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("车检管理工具");
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
