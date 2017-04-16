package com.car.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.car.pojo.CarInfo;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class CommonUtil {

	public static boolean isDate(String date) {
		String regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
		return match(regex, date);

	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	private static Map<String, String> getParmMap(CarInfo carInfo) {
		Map<String, String> parm_map = new HashMap<>();
		parm_map.put("id", carInfo.getC_id());
		parm_map.put("carID", carInfo.getC_car_id());
		parm_map.put("annualCycle", String.valueOf(carInfo.getC_annual_cycle()));
		parm_map.put("InspectionTime", carInfo.getC_Inspection_expirationTime());
		parm_map.put("name", carInfo.getC_name());
		parm_map.put("phone", carInfo.getC_phone());
		return parm_map;

	}

	public static String parseMsgTemplete(CarInfo carInfo, String msgTempleteInVar) {
		if (msgTempleteInVar == null || msgTempleteInVar.equals("") || carInfo == null) {
			return null;
		}
		Map<String, String> parm_map = getParmMap(carInfo);
		Set<String> set = parm_map.keySet();
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			String value = parm_map.get(key);
			if(value ==null ){
				continue;
			}
			msgTempleteInVar = msgTempleteInVar.replace("${" + key + "}", parm_map.get(key));
		}
		return msgTempleteInVar;
	}
	

}
