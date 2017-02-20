package com.car.view;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class CarInfoEditDialogController {
    
	@FXML
	private TextField c_idField;
	@FXML
	private TextField c_nameField;
	@FXML
	private TextField c_identification_cardField;
	@FXML
	private TextArea  c_addressField;
	@FXML
	private TextField c_phoneField;
	@FXML
	private DatePicker c_Inspection_expirationTimePicker;
	@FXML
	private TextField c_caridField;
	
	@FXML
	private void initialize() {
		
		c_Inspection_expirationTimePicker = new DatePicker();
		c_Inspection_expirationTimePicker.setPromptText("YYYY-MM-dd");
		c_Inspection_expirationTimePicker.setValue(LocalDate.now());
		 final Callback<DatePicker, DateCell> dayCellFactory = 
		            new Callback<DatePicker, DateCell>() {
		                @Override
		                public DateCell call(final DatePicker datePicker) {
		                    return new DateCell() {
		                        @Override
		                        public void updateItem(LocalDate item, boolean empty) {
		                            super.updateItem(item, empty);

		                            if (item.isBefore(
		                            		c_Inspection_expirationTimePicker.getValue().plusDays(1))
		                                ) {
		                                    setDisable(true);
		                                    setStyle("-fx-background-color: #ffc0cb;");
		                            }   
		                    }
		                };
		            }
		        };
	}
   	
	
	
}
