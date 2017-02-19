package com.car.pojo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CarInfo {

	private StringProperty c_id;
	private StringProperty c_name;
	private StringProperty c_identification_card;
	private StringProperty c_address;
	private StringProperty c_phone;
	private StringProperty c_car_id;
	private StringProperty c_Inspection_expirationTime;
	private StringProperty c_Insurance_expirationTime;

	public CarInfo() {
		this.c_id = new SimpleStringProperty();
		this.c_name = new SimpleStringProperty();
		this.c_identification_card = new SimpleStringProperty();
		this.c_address = new SimpleStringProperty();
		this.c_phone = new SimpleStringProperty();
		this.c_car_id = new SimpleStringProperty();
		this.c_Inspection_expirationTime = new SimpleStringProperty();
		this.c_Inspection_expirationTime = new SimpleStringProperty();
	}

	public String getC_id() {
		return c_id.get();
	}

	public void setC_id(String c_id) {
		this.c_id.set(c_id);
	}

	public StringProperty getC_idProperty() {
		return c_id;
	}

	public StringProperty getC_nameProperty() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name.set(c_name);
	}

	public String getC_name() {
		return c_name.get();
	}

	public StringProperty getC_identification_cardProperty() {
		return c_identification_card;
	}

	public void setC_identification_card(String c_identification_card) {
		this.c_identification_card.set(c_identification_card);
	}

	public String getC_identification_card() {
		return c_identification_card.get();
	}

	public StringProperty getC_addressProperty() {
		return c_address;
	}

	public void setC_address(String c_address) {
		this.c_address.set(c_address);
	}

	public String getC_address() {
		return c_address.get();
	}

	public StringProperty getC_phoneProperty() {
		return c_phone;
	}

	public void setC_phone(String c_phone) {
		this.c_phone.set(c_phone);
	}

	public String getC_phone() {
		return c_phone.get();
	}

	public StringProperty getC_car_idProperty() {
		return c_car_id;
	}

	public void setC_car_id(String c_car_id) {
		this.c_car_id.set(c_car_id);
		;
	}

	public String getC_car_id() {
		return c_car_id.get();
	}

	public StringProperty getC_Inspection_expirationTimeProperty() {
		return c_Inspection_expirationTime;
	}

	public void setC_Inspection_expirationTime(String c_Inspection_expirationTime) {
		this.c_Inspection_expirationTime.set(c_Inspection_expirationTime);
	}

	public String getC_Inspection_expirationTime() {
		return c_Inspection_expirationTime.get();
	}

	public StringProperty getC_Insurance_expirationTimeProperty() {
		return c_Insurance_expirationTime;
	}

	public void setC_Insurance_expirationTime(String c_Insurance_expirationTime) {
		this.c_Insurance_expirationTime.set(c_Insurance_expirationTime);
	}

	public String getC_Insurance_expirationTime() {
		return c_Insurance_expirationTime.get();
	}

}
