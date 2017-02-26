package com.car.pojo;

import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SendConfPojo {
    private IntegerPropertyBase  countDown;
    private StringProperty msgTemplete;
    
  	public SendConfPojo() {
  		this.countDown = new SimpleIntegerProperty();
  		this.msgTemplete = new SimpleStringProperty();
  	
  	
  	}
    public IntegerPropertyBase getCountDownProperty() {
		return countDown;
	}
    
    public int getCountDown(){
    	
    	return this.countDown.get();
    }
	public void setCountDown(int countDown) {
		this.countDown.set(countDown);
	}
	
	public StringProperty getMsgTempleteProperty() {
		return msgTemplete;
	}
	public void setMsgTemplete(String msgTemplete) {
		this.msgTemplete.set(msgTemplete);
	}
	
    public String getMsgTemplete(){
    	return this.msgTemplete.get();
    }   
    
}
