package com.car.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.commons.lang.StringUtils;

import com.car.helper.DBHelper;
import com.car.pojo.SendSMSPojo;
import com.car.pojo.SendState;

import net.sf.json.JSONObject;

public class SmsService {

	
	private static SimpleDateFormat my_fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	private static final Queue<SendSMSPojo> smsQ = new PriorityBlockingQueue<SendSMSPojo>();
	
	/**
	 * <code>S_LOGGER</code> - 日志.
	 */
	private static final org.apache.log4j.Logger S_LOGGER = org.apache.log4j.Logger.getLogger(SmsService.class);
	
	
	public Queue<SendSMSPojo> getSmsQ(){
		return smsQ;
	}
	
	public void init(){
		try {
			List<SendSMSPojo> waitingSmsPojos = getWaitingSmsPojos();
			if (waitingSmsPojos!=null && waitingSmsPojos.size()>0) {
				smsQ.addAll(waitingSmsPojos);
			}
		} catch (Exception t_e) {
			S_LOGGER.error(t_e.getMessage(),t_e);
		}
	}
	
	/**
	 * 发送短信.
	 * @param sendstatePojo
	 * @throws Exception
	 */
	public void sendSms(final SendSMSPojo smsPojo) throws Exception{
		if (smsPojo == null) {
			throw new IllegalArgumentException("smsPojo is null. ");
		}
		try {
			
			SendState state = smsPojo.getSendState()!=null?smsPojo.getSendState():SendState.wait;
			
			String sql = String.format("insert into t_send_state(c_id,c_phone,c_message,c_state,c_create_date,c_info_id,c_send_year) "
					+ "values('%s','%s','%s','%s','%s','%s','%s')", 
					smsPojo.getId(), 
					smsPojo.getPhone(),
					smsPojo.getMessage(),
					state.name(),
					my_fmt.format(smsPojo.getCreateDate()) ,
					smsPojo.getInfoId(),
					smsPojo.getSendYear()
					);
			S_LOGGER.info(sql);
			DBHelper.getInstance().updateByPreparedStatement(sql, null);
			smsQ.add(smsPojo);
		} catch (Exception t_e) {
			throw t_e;
		}
		
	}
	
	
	
	
	
	private List<SendSMSPojo> getWaitingSmsPojos() throws Exception{
		SendSMSPojo queryParam = new SendSMSPojo();
		queryParam.addSendState(SendState.sending);
		queryParam.addSendState(SendState.wait);
		return getSendSMSPojoList(queryParam);
	}
	
	
	public List<SendSMSPojo> getSendSMSPojoList(SendSMSPojo queryParam) throws Exception{
		String sql = String.format("select * from t_send_state where 1=1", null);
		
		if (queryParam.getSendState()!=null) {
			sql = String.format("%s%s",sql," and c_state='"+queryParam.getSendState().name()+"' ");
		}
		
		if (queryParam.getSendStates() != null) {
			sql = String.format("%s%s",sql," and c_state in ('"+ StringUtils.join(queryParam.getSendStates(), "','") +"') ");
		}
		
		
		List<Map<String, Object>> modeResult = DBHelper.getInstance().findModeResult(sql, null);
		
		List<SendSMSPojo> result = null;
		
		if(modeResult!=null  && modeResult.size()>0){
			result = new ArrayList<SendSMSPojo>();
			for (Map<String, Object> sendSMSPojo : modeResult) {
				SendSMSPojo smsPojo = new SendSMSPojo();
//				smsPojo.setId((Integer) sendSMSPojo.get("c_id"));
				smsPojo.setId(sendSMSPojo.get("c_id").toString());
				smsPojo.setInfoId(sendSMSPojo.get("c_info_id").toString());
				smsPojo.setPhone((String) sendSMSPojo.get("c_phone"));
				smsPojo.setMessage((String) sendSMSPojo.get("c_message"));
				smsPojo.setSendState(SendState.valueOf((String) sendSMSPojo.get("c_state")));
				smsPojo.setCreateDate(getFormString(sendSMSPojo,"c_create_date"));
				smsPojo.setSendDate(getFormString(sendSMSPojo,"c_send_date"));
				smsPojo.setSendYear((String) sendSMSPojo.get("c_send_year"));
				result.add(smsPojo);
			}
		}
		return result;
	}
	
	private Date getFormString(Map<String, Object> rowDate,String column) throws ParseException{
		String str = (String)rowDate.get(column);
		if(str != null && str.length()>0){
			return my_fmt.parse((String)str);
		}
		return null;
	}
	
	
	
	public void deleteSms() throws SQLException{
		String sql = String.format("delete from t_send_state", null);
		DBHelper.getInstance().updateByPreparedStatement(sql, null);
	}
	
	
	public void updateSmsState(String smsId,SendState sendState) throws SQLException{
		String sql = String.format("update t_send_state set c_send_date='%s',c_state='%s' where c_id='%s'", 
				my_fmt.format(new Date()),
				sendState.name(),
				smsId);
		DBHelper.getInstance().updateByPreparedStatement(sql, null);
	}
	
	
	/*
	public static void main(String[] args) throws Exception {
		DBHelper.getInstance();
		SmsService smsService = new SmsService();
		smsService.deleteSms();
		
		SendSMSPojo smsPojo = new SendSMSPojo();
		smsPojo.setInfoId(11233);
		smsPojo.setId(1);
		smsPojo.setPhone("13012112345");
		smsPojo.setMessage("hello");
		smsPojo.setSendState(SendState.sending);
		smsPojo.setCreateDate(new Date());
		smsPojo.setSendYear("2017");
		smsService.sendSms(smsPojo);
		
		SendSMSPojo smsPojo2 = new SendSMSPojo();
		smsPojo2.setInfoId(11233);
		smsPojo2.setId(2);
		smsPojo2.setPhone("13012112345");
		smsPojo2.setMessage("hello123");
		smsPojo2.setSendState(SendState.wait);
		smsPojo2.setCreateDate(new Date());
		smsPojo2.setSendYear("2017");
		smsService.sendSms(smsPojo2);
		
		SendSMSPojo smsPojo3 = new SendSMSPojo();
		smsPojo3.setInfoId(11233);
		smsPojo3.setId(3);
		smsPojo3.setPhone("13012112345");
		smsPojo3.setMessage("hello123");
		smsPojo3.setSendState(SendState.success);
		smsPojo3.setCreateDate(new Date());
		smsPojo3.setSendYear("2017");
		smsService.sendSms(smsPojo3);
		
		SendSMSPojo smsPojo4 = new SendSMSPojo();
		smsPojo4.setInfoId(11233);
		smsPojo4.setId(4);
		smsPojo4.setPhone("13012112345");
		smsPojo4.setMessage("hello123456");
		smsPojo4.setSendState(SendState.success);
		smsPojo4.setCreateDate(new Date());
		smsPojo4.setSendYear("2017");
		smsService.sendSms(smsPojo4);
		
		SendSMSPojo query = new SendSMSPojo() ;
		query.addSendState(SendState.wait);
		query.addSendState(SendState.sending);
		List<SendSMSPojo> sendSMSPojoList = smsService.getSendSMSPojoList(query);
		for (SendSMSPojo sendSMSPojo : sendSMSPojoList) {
			System.out.println(JSONObject.fromObject(sendSMSPojo).toString());
		}
	}*/
	
	
}
