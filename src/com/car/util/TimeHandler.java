package com.car.util;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * Created by h on 2017/2/12.
 */
public class TimeHandler {
    private static  final Logger log  = Logger.getLogger(TimeHandler.class);

    /**
     * 璁＄畻鏃堕棿闂撮殧
     * @param countDown 鏃堕棿闂撮殧
     * @param expiredTime 鍒版湡鏃堕棿
     * @return true:鍒版湡 锛� false锛� 鏈埌鏈熸垨杩囨湡
     * @throws Exception
     */
    public static  boolean isExpired(int countDown,String expiredTime) throws Exception{
        boolean isExpired = false;
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        Date expiredDate= sdf.parse(expiredTime);
        Date currentDate =  sdf.parse(sdf.format(new Date()));
        long expired = expiredDate.getTime();
        long current = currentDate.getTime();
        if (expired>=current){
            long  day=(expiredDate.getTime()-currentDate.getTime())/(24*60*60*1000);
            if (day<=countDown){ //闂撮殧鏃ユ湡灏忎簬璁惧畾鍊硷紝璁や负鍒版湡
                isExpired = true;
            }
        }else{
            log.error("Date is Invalid! ");
        }
        return isExpired;
    }
}
