package com.car.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;


/**
 * Created by h on 2017/2/12.
 */
public class TimeUtil {
	private static final Logger log = Logger.getLogger(TimeUtil.class);
	private static final String time_fomat_str = "yyyy-MM-dd";

	/**
	 * 计算时间间隔
	 * 
	 * @param countDown
	 *            时间间隔
	 * @param expiredTime
	 *            到期时间
	 * @return true:到期 ； false： 未到期或过期
	 * @throws Exception
	 */
	public static boolean isExpired(int countDown, String expiredTime) throws Exception {
		  boolean isExpired = false;
	        SimpleDateFormat sdf =   new SimpleDateFormat(time_fomat_str);
	        Date expiredDate= sdf.parse(expiredTime);
	        Date currentDate =  sdf.parse(sdf.format(new Date()));
	        long expired = expiredDate.getTime();
	        long current = currentDate.getTime();
	        if (expired>=current){
	            long  day=(expiredDate.getTime()-currentDate.getTime())/(24*60*60*1000);
	            if (day<=countDown){ //间隔日期小于设定值，认为到期
	                isExpired = true;
	            }
	        }else{
	            log.error("Date is Invalid! ");
	        }
	        return isExpired;
	    }
	

	public static LocalDate getStr2LocalDate(String timestr) {
		if (timestr != null && !"".equals(timestr)) {
			DateTimeFormatter format = DateTimeFormatter.ofPattern(time_fomat_str);
			format = format.withLocale(Locale.CHINA);
			LocalDate date = LocalDate.parse(timestr, format);
			return date;
		}
		return null;
	}

	public static String getLocalDate2String(LocalDate localDate) {
		if (localDate == null) {
			return "";
		}
		DateTimeFormatter format = DateTimeFormatter.ofPattern(time_fomat_str);
		return localDate.format(format);

	}


}
