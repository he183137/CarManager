package com.car.service;

import java.util.concurrent.PriorityBlockingQueue;


import com.car.pojo.SendSMSPojo;
import com.car.pojo.SendState;

public class SmsThread extends Thread{
	
	/**
	 * <code>S_LOGGER</code> - 日志.
	 */
	private static final org.apache.log4j.Logger S_LOGGER = org.apache.log4j.Logger.getLogger(SmsThread.class);

	@Override
	public void run() {
		SmsService smsService = new SmsService();
		smsService.init();
		PriorityBlockingQueue<SendSMSPojo> t_smsQ = (PriorityBlockingQueue<SendSMSPojo>) 
				smsService.getSmsQ();
		
		while (!Thread.currentThread().isInterrupted()) {
			SendSMSPojo sendSMSPojo = null;
			try {
				sendSMSPojo = t_smsQ.take();
				S_LOGGER.error("Q.take sendRecordId = " + sendSMSPojo.getId() + " messageBody="+sendSMSPojo.getMessage());
				smsService.updateSmsState(sendSMSPojo.getId(), SendState.sending);
				new SmsSender().send(sendSMSPojo);
	            
			} catch (Exception t_e) {
				S_LOGGER.error(t_e.getMessage(), t_e);
				try {
					smsService.updateSmsState(sendSMSPojo.getId(), SendState.failure);
				} catch (Exception t_e1) {
					S_LOGGER.error(t_e.getMessage(), t_e1);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new SmsThread().start();
	}
}
