package com.car.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发送状态
 * <br>
 *  
 * <p>
 * Create on : 2017年3月10日<br>
 * </p>
 * <br>
 * @author sms<br>
 * @version CarManager v6.2.0
 * <br>
 * <strong>Modify History:</strong><br>
 * user     modify_date    modify_content<br>
 * -------------------------------------------<br>
 * <br>
 */
public class SendSMSPojo implements java.lang.Comparable<SendSMSPojo>{

	
	/**
	 * <code>id</code> - {description}.
	 */
	private String id;
	
	/**
	 * <code>phone</code> - {description}.
	 */
	private String phone;
	
	/**
	 * <code>message</code> - {description}.
	 */
	private String message;
	
	/**
	 * <code>sendState</code> - 发送状态.
	 */
	private SendState sendState;
	
	/**
	 * <code>createDate</code> - 创建时间.
	 */
	private Date createDate;
	
	/**
	 * <code>sendDate</code> - 发送时间.
	 */
	private Date sendDate;
	
	/**
	 * <code>infoId</code> - 关联信息id.
	 */
	private String infoId;
	
	
	/**
	 * <code>sendYear</code> - 年检的年份.
	 */
	private String sendYear;
	
	
	/**
	 * <code>sendStates</code> - 查询的状态.
	 */
	private List<SendState> sendStates = null;
	
	


	/*public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}*/


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public SendState getSendState() {
		return sendState;
	}


	public void setSendState(SendState sendState) {
		this.sendState = sendState;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getSendDate() {
		return sendDate;
	}


	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}


//	public Integer getInfoId() {
//		return infoId;
//	}
//
//
//	public void setInfoId(Integer infoId) {
//		this.infoId = infoId;
//	}


	public String getSendYear() {
		return sendYear;
	}


	public String getInfoId() {
		return infoId;
	}


	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}


	public void setSendYear(String sendYear) {
		this.sendYear = sendYear;
	}


	public List<SendState> getSendStates() {
		return sendStates;
	}


	public void setSendStates(List<SendState> sendStates) {
		this.sendStates = sendStates;
	}
	
	public void addSendState(SendState sendState){
		if(this.sendStates == null)sendStates = new ArrayList<SendState>();
		this.sendStates.add(sendState);
	}


	@Override
	public int compareTo(SendSMSPojo o1) {
		if (this.getCreateDate().getTime() < o1.getCreateDate().getTime()) {
			return -1;
		}else if(this.getCreateDate().getTime() > o1.getCreateDate().getTime()){
			return 1;
		}
		return 0;
	}
	
	
	
	
}
