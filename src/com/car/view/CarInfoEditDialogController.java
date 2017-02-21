package com.car.view;

import java.time.LocalDate;

import com.car.dao.DBHelper;
import com.car.pojo.CarInfo;
import com.car.util.TimeUtil;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CarInfoEditDialogController {

	// @FXML
	// private TextField c_idField;
	@FXML
	private TextField c_nameField;
	@FXML
	private TextField c_identification_cardField;
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
	private DBHelper dbHelper;
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	@FXML
	private void initialize() {
		dbHelper = DBHelper.getInstance();
		c_Inspection_expirationTimePicker = new DatePicker();
		c_Inspection_expirationTimePicker.setPromptText("YYYY-MM-dd");
		c_Inspection_expirationTimePicker.setValue(LocalDate.now());
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(c_Inspection_expirationTimePicker.getValue().plusDays(1))) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}
					}
				};
			}
		};
		c_Inspection_expirationTimePicker.setDayCellFactory(dayCellFactory);
	}
	
	
	  /**
     * Called when the user clicks ok.
	 * @throws Exception 
     */
    @FXML
    private void handleOk() throws Exception {
        if (isInputValid()) {
        	carinfo.setC_address(c_addressField.getText());
        	carinfo.setC_car_id(c_caridField.getText());
        	carinfo.setC_identification_card(c_identification_cardField.getText());
        	carinfo.setC_name(c_nameField.getText());
        	carinfo.setC_phone(c_phoneField.getText());
        	carinfo.setC_Inspection_expirationTime(TimeUtil.getLocalDate2String(c_Inspection_expirationTimePicker.getValue()));
        	dbHelper.addCarInfo(carinfo);
            okClicked = true;
            dialogStage.close();
        }
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
			if (isInputValid()) {
				c_nameField.setText(carInfo.getC_name());
				c_identification_cardField.setText(carInfo.getC_identification_card());
				c_phoneField.setText(carInfo.getC_phone());
				c_addressField.setText(carInfo.getC_address());
				c_caridField.setText(carInfo.getC_car_id());
				c_Inspection_expirationTimePicker
						.setValue(TimeUtil.getStr2LocalDate(carInfo.getC_Inspection_expirationTime()));
			}
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
			errorMessage += "No valid name!\n";
		}
		if (c_identification_cardField.getText() == null || c_identification_cardField.getText().length() == 0) {
			errorMessage += "No valid c_identification_card!\n";
		}
		if (c_phoneField.getText() == null || c_phoneField.getText().length() == 0) {
			errorMessage += "No valid phone!\n";
		}

		if (c_addressField.getText() == null || c_addressField.getText().length() == 0) {
			errorMessage += "No validaddress!\n";
		}

		if (c_caridField.getText() == null || c_caridField.getText().length() == 0) {
			errorMessage += "No valid carID!\n";
		}

		if (c_Inspection_expirationTimePicker.getValue() == null
				|| c_Inspection_expirationTimePicker.getValue().toString().equals("")) {
			errorMessage += "No valid Inspection_expirationTime!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			FxDialogs.showError("NO Vaild Vaule", errorMessage);
			return false;
		}
	}

}
