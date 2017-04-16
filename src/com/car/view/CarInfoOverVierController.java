package com.car.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.car.helper.DBHelper;
import com.car.main.Main;
import com.car.pojo.CarInfo;
import com.car.pojo.Confpojo;
import com.car.pojo.SendConfPojo;
import com.car.pojo.SendSMSPojo;
import com.car.service.SmsService;
import com.car.service.SmsThread;
import com.car.util.CommonUtil;
import com.car.util.SendSmsUtil;
import com.car.util.TimeUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import jdk.nashorn.internal.ir.CatchNode;

public class CarInfoOverVierController {
	@FXML
	private TableView<CarInfo> carTable;

	@FXML
	private TableColumn<CarInfo, String> c_id;

	@FXML
	private TableColumn<CarInfo, String> c_name;
	@FXML
	private TableColumn<CarInfo, Number> c_annual_cycle;
//	@FXML
//	private TableColumn<CarInfo, String> c_address;
	@FXML
	private TableColumn<CarInfo, String> c_phone;
	@FXML
	private TableColumn<CarInfo, String> c_car_id;
	@FXML
	private TableColumn<CarInfo, String> c_Inspection_expirationTime;
//	@FXML
//	private TableColumn<CarInfo, String> c_Insurance_expirationTime;
    
    @FXML 
	private  TextField portField;
    
    @FXML
    private TextField baudRateField;
    
    @FXML
    private TextField pinCodeField;
    
    @FXML 
    private TextField manufacturerField;
    
    @FXML
    private TextField modelField;
    
    @FXML
    private TextField testPhoneNoField;
    
 
    @FXML
    private TextField countdonwField;
    
    
    @FXML
	private TableView<CarInfo> carTable2;

	@FXML
	private TableColumn<CarInfo, String> c_id2;

	@FXML
	private TableColumn<CarInfo, String> c_name2;
	@FXML
	private TableColumn<CarInfo, Number> c_annual_cycle2;
//	@FXML
//	private TableColumn<CarInfo, String> c_address2;
	@FXML
	private TableColumn<CarInfo, String> c_phone2;
	@FXML
	private TableColumn<CarInfo, String> c_car_id2;
	@FXML
	private TableColumn<CarInfo, String> c_Inspection_expirationTime2;
	
	@FXML
	private TextField search_text;
	
	
	private ObservableList<CarInfo> expriedCarList = FXCollections.observableArrayList();
	private Main Main;
	private SendConfPojo sendConfPojo; 
	

	private Confpojo confpojo;
	private static Logger logger = Logger.getLogger(CarInfoOverVierController.class);
	private DBHelper dbHelper;
	
	public void setMain(Main main) {
		this.Main = main;
		carTable.setItems(main.getCarInfosData());
	}
	
	public CarInfoOverVierController() {
	}

	public String getCarInfoById(CarInfo carInfo) {
		return carInfo != null ? carInfo.getC_id() : "";

	}

	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() throws Exception {
		dbHelper = DBHelper.getInstance();
		c_id.setCellValueFactory(cellData -> cellData.getValue().getC_idProperty());
		c_name.setCellValueFactory(cellData -> cellData.getValue().getC_nameProperty());
		c_annual_cycle.setCellValueFactory(cellData -> cellData.getValue().getC_annual_cycleProperty());
//		c_address.setCellValueFactory(cellData -> cellData.getValue().getC_addressProperty());
		c_phone.setCellValueFactory(cellData -> cellData.getValue().getC_phoneProperty());
		c_car_id.setCellValueFactory(cellData -> cellData.getValue().getC_car_idProperty());
		c_Inspection_expirationTime
				.setCellValueFactory(cellData -> cellData.getValue().getC_Inspection_expirationTimeProperty());
		// c_Insurance_expirationTime.setCellValueFactory(cellData->cellData.getValue().getC_Insurance_expirationTimeProperty());
//		carTable.getSelectionModel().selectedItemProperty()
//				.addListener((observable, oldValue, newValue) -> getCarInfoById(newValue));
		countdonwField.textProperty().addListener(new ChangeListener() {

			 public void changed(ObservableValue observable, Object oldValue, Object newValue) {

				 try {
					saveCountDown();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			});
		setConfPojo(dbHelper.getConf());
		setSendConfPojo(dbHelper.getMsgConf());
	}

	@FXML
	private void handleDeleteCar() throws Exception {
		CarInfo info = carTable.getSelectionModel().getSelectedItem();
		if(info == null){
			FxDialogs.showError("No Selection", "No Car Selected");
		    
		}else{
			boolean isdelOK = dbHelper.delCarInfo(info);
			if(!isdelOK){
				logger.error("del fail id:"+info.getC_id());
				FxDialogs.showError("Delete Error", "删除失败");
			}else{
				carTable.getItems().remove(info);
				logger.info("del is ok,id "+info.getC_id());
			}	
		}
		
	}
	
	
	
	@FXML
	private void handleNewCarInfo() throws Exception {
	    CarInfo tempCar = new CarInfo();
	    boolean okClicked = Main.showCarInfoEditDialog(tempCar,"添加人员");
	    if (okClicked) {
	    	dbHelper.addCarInfo(tempCar);
	    	Main.getCarInfosData().add(tempCar);
	    }
	}

	
	@FXML
	private void handleEditPerson() throws Exception {
	    CarInfo selectedCar = carTable.getSelectionModel().getSelectedItem();
	    if (selectedCar != null) {
	        boolean okClicked = Main.showCarInfoEditDialog(selectedCar,"修改人员");
	        if (okClicked) {
	        	dbHelper.updateCarInfo(selectedCar); 
	        }

	    } else {
	        // Nothing selected.
	    	FxDialogs.showError("Error", "No car selected");
	    }
	}
	
	public void setConfPojo(Confpojo confpojo){
		this.confpojo = confpojo;
		if(confpojo !=null){
			portField.setText(confpojo.getPort());
			baudRateField.setText(confpojo.getBaudRate());
			manufacturerField.setText(confpojo.getManufacturer());
			modelField.setText(confpojo.getModel());
			pinCodeField.setText(confpojo.getPinCode());
			testPhoneNoField.setText(confpojo.getTestPhoneNo());
			
		}

	}
	
	public void setSendConfPojo(SendConfPojo sendConfPojo) {
		this.sendConfPojo = sendConfPojo;
		if(sendConfPojo!=null){
			countdonwField.setText(String.valueOf(sendConfPojo.getCountDown()));
		}
	}
	
	@FXML
	private void handleOKConf() throws Exception{
		confpojo.setPort(portField.getText());
		confpojo.setBaudRate(baudRateField.getText());
		confpojo.setManufacturer(manufacturerField.getText());
		confpojo.setModel(modelField.getText());
		confpojo.setPinCode(pinCodeField.getText());
		confpojo.setTestPhoneNo(testPhoneNoField.getText());
		try{
			dbHelper.updateConf(confpojo);
			FxDialogs.showInformation("INFO", "设置成功");
		}catch (Exception e) {
			// TODO: handle exception
			FxDialogs.showError("ERROR", e.getMessage());
		}
		
	}
	
	@FXML
	private void handlerTestConf() throws Exception{
		String testPhoneNo = confpojo.getTestPhoneNo();
		String testPhoneF = testPhoneNoField.getText();
		if(testPhoneF!=null && !"".equals(testPhoneF)){	
			if(testPhoneNo!=null && !"".equals(testPhoneNo)){
				boolean flag = SendSmsUtil.sendSms();
				if(!flag){
					FxDialogs.showError("ERROR", "测试失败");
				}else{
					FxDialogs.showInformation("INFO", "测试成功");
				}	
			}else{
				FxDialogs.showError("ERROR", "测试电话异常：error1"); //error1:数据库该值为空
			}
		}else{
			FxDialogs.showError("ERROR", "测试电话不能为空");
		}
	}
	
	
	private List<CarInfo> getExpireCar(List<CarInfo> allCarInfo){
		if(allCarInfo==null ||allCarInfo.isEmpty())  {
			return null;
		}
		List <CarInfo> expriedCarList = new ArrayList<>();
		try{
			
			Iterator<CarInfo> iterable = allCarInfo.iterator();
			while(iterable.hasNext()){
				CarInfo carInfo = iterable.next();
				String inspectionTime = carInfo.getC_Inspection_expirationTime();
				boolean isExpired = TimeUtil.isExpired(sendConfPojo.getCountDown(), inspectionTime);
				if(isExpired){
					expriedCarList.add(carInfo);
				}
			}	
		}catch (Exception e) {
			// TODO: handle exception
			FxDialogs.showError("ERROR", e.getMessage());
		}
		return expriedCarList;
	}
	
	@FXML
	private void handlerCheckDate() throws Exception{
		List<CarInfo> lists = getExpireCar(dbHelper.getAllCarInfo());
		if(lists==null || lists.isEmpty()){
			FxDialogs.showError("INFO", "无即将到期车辆");
		}else{
			if(!carTable2.getItems().isEmpty()){//清空上次查询记录
				expriedCarList.clear();
			}
			expriedCarList.addAll(lists);
			initTableData(expriedCarList, carTable2);
			carTable2.setItems(expriedCarList);	
//			c_id2.setCellValueFactory(cellData -> cellData.getValue().getC_idProperty());
//			c_name2.setCellValueFactory(cellData -> cellData.getValue().getC_nameProperty());
//			c_annual_cycle2.setCellValueFactory(cellData -> cellData.getValue().getC_annual_cycleProperty());
////			c_address2.setCellValueFactory(cellData -> cellData.getValue().getC_addressProperty());
//			c_phone2.setCellValueFactory(cellData -> cellData.getValue().getC_phoneProperty());
//			c_car_id2.setCellValueFactory(cellData -> cellData.getValue().getC_car_idProperty());
//			c_Inspection_expirationTime2
//					.setCellValueFactory(cellData -> cellData.getValue().getC_Inspection_expirationTimeProperty());
		}
	}
	
	@FXML
	private void saveCountDown()throws Exception{
		String countdown = countdonwField.getText();
		try{
			sendConfPojo.setCountDown(Integer.parseInt(countdown));	
			logger.debug("countDown: "+countdown);
			dbHelper.updateMsgConf(sendConfPojo);
		}catch (NumberFormatException e) {
			// TODO: handle exception
			logger.error("input: "+countdown);
		}
	}
	
	
	@FXML
	private void handlerSendSms () throws Exception{
	
		if(expriedCarList==null || expriedCarList.isEmpty()){
			FxDialogs.showError("INFO", "无到期车辆短信发送");
		}else{
			SmsService sms = new SmsService();
			Iterator<CarInfo> car_iter = expriedCarList.iterator();
			while (car_iter.hasNext()) {
				CarInfo carInfo = car_iter.next();
				SendSMSPojo smsPojo = new SendSMSPojo();
				smsPojo.setId(UUID.randomUUID().toString());
				smsPojo.setInfoId(carInfo.getC_id().toString());
				smsPojo.setPhone(carInfo.getC_phone());
				smsPojo.setMessage(CommonUtil.parseMsgTemplete(carInfo, this.sendConfPojo.getMsgTemplete()));
				Date date  = new Date();
				smsPojo.setSendDate(date);
			    smsPojo.setCreateDate(new Date());
			    Calendar calendar = Calendar.getInstance();
			    SimpleDateFormat sFormat = new SimpleDateFormat("YYYY");
			    calendar.setTime(date);
			    smsPojo.setSendYear(sFormat.format(date));
			    sms.sendSms(smsPojo);
			}
			
			FxDialogs.showInformation("短信状态", "发送完毕");
		}
	
	}
	
	@FXML
	private void handleEditMsgTemplete() throws Exception{
		 boolean okClicked = Main.showMsgEditDialog(this.sendConfPojo);
		 if (okClicked) {
	        	dbHelper.updateMsgConf(this.sendConfPojo);
	    }
	}
	
	@FXML
	
	private void handleSearch4Name() throws Exception{
		 List<CarInfo> list = null;
		String search_name = search_text.getText();
		try{
			if(StringUtils.isEmpty(search_name)){
				 list =  dbHelper.getAllCarInfo();
			  }else{
				 list =  dbHelper.getCarInfoByName(search_name);
			  }
			if(list ==null || list.isEmpty()){
				FxDialogs.showInformation("查询结果","查询结果为空");
				
			}
			ObservableList<CarInfo> carInfos = Main.getCarInfosData();
			carInfos.clear();
			carInfos.setAll(list);
			initTableData(carInfos, carTable);
			
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
		}
}
	
	
	
	@FXML
	private void refreshCarInfo(){
		try{
			List<CarInfo> list = dbHelper.getAllCarInfo();
			ObservableList<CarInfo> carInfos  = Main.getCarInfosData();
			if(!carInfos.isEmpty()){
				carInfos.clear();
				carInfos.addAll(list);
			}
			initTableData(carInfos, carTable);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
		}
		
			
	}
	
	private void initTableData(ObservableList<CarInfo> list,TableView<CarInfo> table ){
		table.setItems(list);
		c_id.setCellValueFactory(cellData -> cellData.getValue().getC_idProperty());
		c_name.setCellValueFactory(cellData -> cellData.getValue().getC_nameProperty());
		c_annual_cycle.setCellValueFactory(cellData -> cellData.getValue().getC_annual_cycleProperty());
		c_phone.setCellValueFactory(cellData -> cellData.getValue().getC_phoneProperty());
		c_car_id.setCellValueFactory(cellData -> cellData.getValue().getC_car_idProperty());
		c_Inspection_expirationTime
				.setCellValueFactory(cellData -> cellData.getValue().getC_Inspection_expirationTimeProperty());
	}
		
}
