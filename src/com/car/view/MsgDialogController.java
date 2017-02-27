package com.car.view;

import org.apache.log4j.Logger;

import com.car.dao.DBHelper;
import com.car.pojo.SendConfPojo;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MsgDialogController {
	@FXML
	private TextArea MsgTempleteFiled;
	private Stage dialogStage;
	private boolean okClicked;
	private DBHelper dbHelper;
	private static Logger logger = Logger.getLogger(MsgDialogController.class);
	private SendConfPojo sendConfPojo;

	public void setSendConfPojo(SendConfPojo sendConfPojo) {
		this.sendConfPojo = sendConfPojo;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	private void initialize() {
		dbHelper = DBHelper.getInstance();
		try {
			SendConfPojo sendConfPojo = dbHelper.getMsgConf();
			setSendConfPojo(sendConfPojo);
			MsgTempleteFiled.setText(sendConfPojo.getMsgTemplete());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
	}

	@FXML
	private void hanlderOK() {
		String msgTemplete = sendConfPojo.getMsgTemplete();
		if (msgTemplete != null && !"".equals(msgTemplete)) {
			String msgf =  MsgTempleteFiled.getText();
			if(msgf!=null &&!"".equals(msgf)){
				sendConfPojo.setMsgTemplete(msgf);
				try {
					dbHelper.updateMsgConf(sendConfPojo);
					FxDialogs.showInformation("INFO", "设置成功");
					okClicked = true;
	                dialogStage.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					FxDialogs.showError("ERROR", e.getMessage());
				}
			}else{
				FxDialogs.showError("ERROR", "模板不能为空");
			}	
		}
		
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
}
