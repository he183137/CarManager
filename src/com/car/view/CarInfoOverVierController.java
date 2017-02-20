package com.car.view;

import org.apache.log4j.Logger;

import com.car.dao.DBHelper;
import com.car.main.Main;
import com.car.pojo.CarInfo;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CarInfoOverVierController {
	@FXML
	private TableView<CarInfo> carTable;

	@FXML
	private TableColumn<CarInfo, String> c_id;

	@FXML
	private TableColumn<CarInfo, String> c_name;
	@FXML
	private TableColumn<CarInfo, String> c_identification_card;
	@FXML
	private TableColumn<CarInfo, String> c_address;
	@FXML
	private TableColumn<CarInfo, String> c_phone;
	@FXML
	private TableColumn<CarInfo, String> c_car_id;
	@FXML
	private TableColumn<CarInfo, String> c_Inspection_expirationTime;
	@FXML
	private TableColumn<CarInfo, String> c_Insurance_expirationTime;

	private Main Main;
	private DBHelper dbHelper;
	private static Logger logger = Logger.getLogger(CarInfoOverVierController.class);
	public void setMain(Main main) {
		this.Main = main;
		carTable.setItems(main.getCarInfosData());
	}

	public CarInfoOverVierController() {
	}

	public String getCarInfoById(CarInfo carInfo) {
		return carInfo != null ? carInfo.getC_id() : "";

	}

	@FXML
	private void initialize() {
		dbHelper = new DBHelper();
		c_id.setCellValueFactory(cellData -> cellData.getValue().getC_idProperty());
		c_name.setCellValueFactory(cellData -> cellData.getValue().getC_nameProperty());
		c_identification_card.setCellValueFactory(cellData -> cellData.getValue().getC_identification_cardProperty());
		c_address.setCellValueFactory(cellData -> cellData.getValue().getC_addressProperty());
		c_phone.setCellValueFactory(cellData -> cellData.getValue().getC_phoneProperty());
		c_car_id.setCellValueFactory(cellData -> cellData.getValue().getC_car_idProperty());
		c_Inspection_expirationTime
				.setCellValueFactory(cellData -> cellData.getValue().getC_Inspection_expirationTimeProperty());
		// c_Insurance_expirationTime.setCellValueFactory(cellData->cellData.getValue().getC_Insurance_expirationTimeProperty());
		carTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> getCarInfoById(newValue));
	}

	@FXML
	private void handleDeleteCar() throws Exception {
		CarInfo info = carTable.getSelectionModel().getSelectedItem();
		if(info == null){
			FxDialogs.showError("No Selection", "No Car Selected");
		    
		}else{
			boolean isdelOK = dbHelper.delCarInfo(info);
			if(!isdelOK){
				logger.error("del fail £¬id£º"+info.getC_id());
			}else{
				logger.info("del is ok,id "+info.getC_id());
			}
			carTable.getItems().remove(info);
		}
		
	}
}
