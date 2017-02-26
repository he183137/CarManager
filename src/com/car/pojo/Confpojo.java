package com.car.pojo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Created by h on 2017/2/12.
 */
public class Confpojo {

//    private int countDown;
//    private StringProperty msgTemplete;
    private StringProperty port;
    private StringProperty baudRate;
    private StringProperty pinCode;
    private StringProperty manufacturer;
    private StringProperty model;
    private StringProperty testPhoneNo;

    
	public Confpojo() {
		this.port = new SimpleStringProperty();
		this.baudRate = new SimpleStringProperty();
		this.pinCode = new SimpleStringProperty();
		this.manufacturer = new SimpleStringProperty();
		this.model = new SimpleStringProperty();
		this.testPhoneNo = new SimpleStringProperty();
	
	}


    public String getPort() {
        return port.get();
    }

    public void setPort(String port) {
        this.port.set(port);
    }

    public StringProperty getPortProperty(){
    	return this.port;
    } 
    public String getBaudRate() {
        return baudRate.get();
    }
    
    public StringProperty getBaudRateProperty(){
    	return this.baudRate;
    }

    public void setBaudRate(String baudRate) {
        this.baudRate.set( baudRate);
    }

    public String getPinCode() {
        return pinCode.get();
    }

    public void setPinCode(String pinCode) {
        this.pinCode.set(pinCode);
    }

    public StringProperty getPinCodeProperty(){
    	return this.pinCode;
    }
    public String getManufacturer() {
        return manufacturer.get();
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public StringProperty getManufacturerProperty(){
    	return this.manufacturer;
    }
    public String getModel() {
        return model.get();
    }

    public void setModel(String model) {
        this.model.set(model);
    }
    public StringProperty getModelProperty(){
    	return this.model;
    }
    public String getTestPhoneNo() {
        return testPhoneNo.get();
    }

    public void setTestPhoneNo(String testPhoneNo) {
        this.testPhoneNo.set(testPhoneNo);
    }
    public StringProperty getTestPhoneNoProperty(){
    	return this.testPhoneNo;
    }
}
