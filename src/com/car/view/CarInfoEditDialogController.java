package com.car.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;

import com.car.helper.DBHelper;
import com.car.pojo.CarInfo;
import com.car.util.CommonUtil;
import com.car.util.TimeUtil;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class CarInfoEditDialogController {

	// @FXML
	// private TextField c_idField;
	@FXML
	private TextField c_nameField;
	@FXML
	private TextField c_annualCycleField;
	@FXML
	private TextArea c_addressField;
	@FXML
	private TextField c_phoneField;
	@FXML
	private DatePicker c_Inspection_expirationTimePicker;
	@FXML
	private TextField c_caridField;
    
	private CarInfo carinfo;
	private Stage dialogStage;
	private boolean okClicked;
	private static Logger logger = Logger.getLogger(CarInfoEditDialogController.class);
	public boolean isOkClicked() {
		return okClicked;
	}

	
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	@FXML
	private void initialize() {

	}
	
	
	  /**
     * Called when the user clicks ok.
	 * @throws Exception 
     */
    @FXML
    private void handleOk() throws Exception {
//        if (isInputValid()) {
    	try{
    		carinfo.setC_annual_cycle(Integer.parseInt(c_annualCycleField.getText()));
    	}catch (NumberFormatException e) {
			// TODO: handle exception
    		FxDialogs.showError("ERROR", "车检周期应为数字");
    		logger.error("车检周期不正确");
		}
        	
        	carinfo.setC_car_id(c_caridField.getText());
//        	carinfo.setC_identification_card(c_identification_cardField.getText());
        	carinfo.setC_name(c_nameField.getText());
        	carinfo.setC_phone(c_phoneField.getText());
        	c_Inspection_expirationTimePicker.setConverter(new StringConverter<LocalDate>() {
        		 DateTimeFormatter dateFormatter = 
        	                DateTimeFormatter.ofPattern( "yyyy-MM-dd");
        	            @Override
        	            public String toString(LocalDate date) {
        	                if (date != null) {
        	                    return dateFormatter.format(date);
        	                } else {
        	                    return "";
        	                }
        	            }
        	            @Override
        	            public LocalDate fromString(String string) {
        	                if (string != null && !string.isEmpty()) {
        	                    return LocalDate.parse(string, dateFormatter);
        	                } else {
        	                    return null;
        	                }
        	            }

			});
        	carinfo.setC_Inspection_expirationTime(c_Inspection_expirationTimePicker.getEditor().getText());
        	if(isInputValid()){
        		okClicked = true;
                dialogStage.close();
        	}
            
//        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
     
	public void setCarInfo(CarInfo carInfo) {
		this.carinfo = carInfo; 
		if (carInfo != null) {
				c_nameField.setText(carInfo.getC_name());
				c_annualCycleField.setText(String.valueOf(carInfo.getC_annual_cycle()));
				c_phoneField.setText(carInfo.getC_phone());
//				c_addressField.setText(carInfo.getC_address());
				c_caridField.setText(carInfo.getC_car_id());
				c_Inspection_expirationTimePicker
						.setValue(TimeUtil.getStr2LocalDate(carInfo.getC_Inspection_expirationTime()));
				
		} 
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";
		if (c_nameField.getText() == null || c_nameField.getText().length() == 0) {
			
			errorMessage += "请输入姓名";
		}
		if (c_annualCycleField.getText() == null || c_annualCycleField.getText().length() == 0) {
			errorMessage += "请输入车检周期";
		}
		if (c_phoneField.getText() == null || c_phoneField.getText().length() == 0) {
			errorMessage += "请输入电话号码";
		}

	

		if (c_caridField.getText() == null || c_caridField.getText().length() == 0) {
			errorMessage += "请输入车牌号";
		}

		if (c_Inspection_expirationTimePicker.getValue() == null
				|| c_Inspection_expirationTimePicker.getValue().toString().equals("")) {
			errorMessage += "请输入验车时间";
		}else {
			if (!CommonUtil.isDate(c_Inspection_expirationTimePicker.getValue().toString())){
				errorMessage += "时间格式不正确";
			}
		}
         
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			FxDialogs.showError("无效输入", errorMessage);
			return false;
		}
	}

}
