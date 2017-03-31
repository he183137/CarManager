package com.car.service;

import java.sql.SQLException;
import java.util.Collection;


import com.car.helper.DBHelper;
import com.car.pojo.Confpojo;
import com.car.pojo.SendSMSPojo;
import com.car.pojo.SendState;

import cn.sendsms.AGateway;
import cn.sendsms.AGateway.Protocols;
import cn.sendsms.Message.MessageEncodings;
import cn.sendsms.OutboundMessage;
import cn.sendsms.Service;
import cn.sendsms.Service.ServiceStatus;
import cn.sendsms.modem.SerialModemGateway;
import net.sf.json.JSONObject;

public class SmsSender {
	
	private static final Object S_CONFIG_LOCK = new Object();

	private static final Service S_SMS_SERVER = Service.getInstance();
	
	/**
	 * <code>S_LOGGER</code> - 日志.
	 */
	private static final org.apache.log4j.Logger S_LOGGER = org.apache.log4j.Logger.getLogger(SmsSender.class);
	
	public void send(SendSMSPojo smsPojo){
		 synchronized (S_CONFIG_LOCK) {
			 SmsService smsService = new SmsService();
	            if (ServiceStatus.STOPPED == S_SMS_SERVER.getServiceStatus()
	                || ServiceStatus.STOPPING == S_SMS_SERVER.getServiceStatus()) {
	                try {
	                    initGateways();
	                    S_SMS_SERVER.startService();
	                    
	                } catch (Exception e) {
	                	S_LOGGER.error("SMS Server Start Failure", e);
	                	try {
							smsService.updateSmsState(smsPojo.getId(), SendState.failure);
						} catch (SQLException t_e) {
							S_LOGGER.error("SMS Send Failure", e);
						}
	                	return;
	                }
	            }
	            
	            try {
		            OutboundMessage outboundMessage = new OutboundMessage(smsPojo.getPhone(),smsPojo.getMessage());
	                outboundMessage.setEncoding(MessageEncodings.ENCUCS2);
	                Collection<AGateway> gateways = S_SMS_SERVER.getGateways();
	                for (AGateway aGateway : gateways) {
	                	aGateway.sendMessage(outboundMessage);
	                	smsService.updateSmsState(smsPojo.getId(), SendState.success);
	                	break;
					}
	            } catch (Exception e) {
                	S_LOGGER.error("SMS Server Send Failure", e);
                	try {
						smsService.updateSmsState(smsPojo.getId(), SendState.failure);
					} catch (SQLException t_e) {
						S_LOGGER.error("SMS Send Failure", e);
					}
                }
	            S_LOGGER.error("SMS Server Send success!\r\n    "+JSONObject.fromObject(smsPojo).toString());
	            
	        }
	}
	
	
	private static void initGateways() throws Exception {
        Confpojo conf = DBHelper.getInstance().getConf();
        SerialModemGateway t_gateway = createGateway(conf);
        try {
        	t_gateway.testGateway();
        	S_SMS_SERVER.addGateway(t_gateway);
        } catch (Exception t_e) {
        	S_LOGGER.error(t_e.getMessage(), t_e);
        }
    }
	
	private static SerialModemGateway createGateway(final Confpojo config) {
        SerialModemGateway gateway =
            new SerialModemGateway("modem." + config.getPort(), config.getPort(), Integer.valueOf(config.getBaudRate()), config.getManufacturer(),
                config.getModel());
        gateway.setInbound(false);
        gateway.setOutbound(true);
        gateway.setProtocol(Protocols.PDU);
        gateway.setSimPin(config.getPinCode());
        return gateway;
    }
	
	
	
	
}
