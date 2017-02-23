package com.car.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.car.pojo.CarInfo;
import com.car.pojo.Confpojo;
import com.car.util.JdbcUtils;

/**
 * Created by h on 2017/2/12.
 */
public class DBHelper {
    private static Logger logger = Logger.getLogger(DBHelper.class);
    private JdbcUtils jdbcUtils;
    private String db_path = "db\\CarData.db";
    private static DBHelper instance = new DBHelper();

    private DBHelper() {
        this.jdbcUtils = new JdbcUtils(db_path);
        init();
    }
    
    public static DBHelper getInstance(){
    	return instance;
    } 
    
    private  void init(){
        jdbcUtils.getConnection();
    }
    /**
     * 鏌ヨ鍏ㄩ儴淇℃伅
     *
     * @return
     * @throws Exception
     */
    public List<CarInfo> getAllCarInfo() throws Exception {
        String sql = "select c_id, " +
                "c_identification_card," +
                "c_Inspection_expirationTime," +
                "c_Insurance_expirationTime," +
                "c_name," +
                "c_phone," +
                "c_car_id," +
                "c_address " +
                " from t_info ";
        List <Map<String,Object>> carInfo_result = jdbcUtils.findModeResult(sql, null);
        Iterator<Map<String,Object>> iterator = carInfo_result.iterator();
        List<CarInfo> carInfo_list = new ArrayList<>();
        while(iterator.hasNext()){
        	Map<String, Object> map_res = iterator.next();
        	CarInfo carInfo = new CarInfo();
        	carInfo.setC_address(map_res.get("c_address").toString());
        	carInfo.setC_car_id(map_res.get("c_car_id").toString());
        	carInfo.setC_id(map_res.get("c_id").toString());
        	carInfo.setC_identification_card(map_res.get("c_identification_card").toString());
        	carInfo.setC_Inspection_expirationTime(map_res.get("c_Inspection_expirationTime").toString());
//        	carInfo.setC_Insurance_expirationTime(map_res.get("c_Insurance_expirationTime").toString());
        	carInfo.setC_name(map_res.get("c_name").toString());
        	carInfo.setC_phone(map_res.get("c_phone").toString());
        	carInfo_list.add(carInfo);
        }
//        List<CarInfo> carInfo_list = jdbcUtils.findMoreRefResult(sql, null, CarInfo.class);
        jdbcUtils.releaseResultSet();
        return carInfo_list;
    }

    /**
     * 鏌ヨ鍗曚釜鐢ㄦ埛淇℃伅
     *
     * @param id
     * @return 鐢ㄦ埛pojo
     * @throws Exception
     */
    public CarInfo getCarInfoByOne(String id) throws Exception {
        List<Object> parm = new ArrayList<Object>();
        parm.add(id);
        String sql = "select c_id, " +
                "t.c_identification_card," +
                "t.c_Inspection_expirationTime," +
                "t.c_Insurance_expirationTime," +
                "t.c_name," +
                "t.c_phone," +
                "t.c_car_id," +
                "t.c_address" +
                " from t_info t " +
                "where c_id = ?";
        CarInfo CarInfo =  jdbcUtils.findSimpleRefResult(sql, parm, CarInfo.class);
        jdbcUtils.releaseResultSet();
        return CarInfo;
    }

    /**
     * 鍒犻櫎鐢ㄦ埛
     *
     * @param CarInfo
     * @throws Exception
     */
    public boolean delCarInfo(CarInfo CarInfo) throws Exception {
        boolean flag = false;
        if (CarInfo != null&&CarInfo.getC_id()!=null) {
            String id = CarInfo.getC_id();
            String sql = "delete from t_info where c_id = '"+id+"'";
            flag= jdbcUtils.updateByPreparedStatement(sql, null);
        }else{
            flag = false;
        }
       return flag;
    }

    /**
     * 鎵归噺鍒犻櫎鐢ㄦ埛
     * @param cars
     * @throws Exception
     */
    public void delCarInfos(List<CarInfo> cars)throws Exception{
        if (cars != null && !cars.isEmpty()) {
            Iterator<CarInfo> iterator = cars.iterator();
            while (iterator.hasNext()) {
                CarInfo CarInfo = iterator.next();
                delCarInfo(CarInfo);
            }
        }
     }

    /**
     * 鎵归噺淇敼鐢ㄦ埛淇℃伅
     * @param cars
     * @throws Exception
     */
    public void updateCarInfos(List<CarInfo> cars) throws Exception {
        if (cars != null && !cars.isEmpty()) {
            Iterator<CarInfo> iterator = cars.iterator();
            while (iterator.hasNext()) {
                CarInfo CarInfo = iterator.next();
                updateCarInfo(CarInfo);
            }
        }
    }
    /**
     * 淇敼鐢ㄦ埛淇℃伅
     */
    public boolean updateCarInfo(CarInfo CarInfo) throws Exception{
        boolean flag = false;
       if(CarInfo!=null){
           String id = CarInfo.getC_id();
           String identification_card = CarInfo.getC_identification_card();
           String name = CarInfo.getC_name();
           String phone = CarInfo.getC_phone();
           String address= CarInfo.getC_address();
           String car_id = CarInfo.getC_car_id();
           String inspection_time = CarInfo.getC_Inspection_expirationTime();
           //String insurance_time = CarInfo.getC_Insurance_expirationTime();
           String sql = "UPDATE t_info " +
                   " SET c_identification_card = '"+identification_card +"'," +
                   " c_Inspection_expirationTime = '"+inspection_time+"'," +
                   " c_Insurance_expirationTime='"+""+"'," +
                   " c_name = '"+name+"'," +
                   " c_phone='"+phone+"'," +
                   " c_car_id='"+car_id+"'," +
                   " c_address='"+address+"'"+
                   " WHERE c_id = '"+id+"'";
           flag = jdbcUtils.updateByPreparedStatement(sql,null);
           if (!flag){
               logger.error("update user error. The sql :"+sql);
           }
       }
        return  flag;
    }
    /***
     * 娣诲姞鐢ㄦ埛
     */
    public boolean addCarInfo(CarInfo CarInfo) throws Exception{
        boolean flag = false;
        if (CarInfo != null) {
        	if( CarInfo.getC_id()==null ||"".equals(CarInfo.getC_id())){
        		CarInfo.setC_id(UUID.randomUUID().toString());
        	}
            String id = CarInfo.getC_id();
            String identification_card = CarInfo.getC_identification_card();
            String name = CarInfo.getC_name();
            String phone = CarInfo.getC_phone();
            String address= CarInfo.getC_address();
            String car_id = CarInfo.getC_car_id();
            String inspection_time = CarInfo.getC_Inspection_expirationTime();
            //String insurance_time = CarInfo.getC_Insurance_expirationTime();
            String sql = " insert into t_info  (c_id, " +
                    " c_identification_card," +
                    " c_Inspection_expirationTime," +
                    " c_Insurance_expirationTime," +
                    " c_name," +
                    " c_phone," +
                    " c_car_id," +
                    " c_address )" +
                    "values('"+id+"','"+identification_card+"','"+inspection_time+"','"+""+"','"+name+"','"+phone+"','"+car_id+"','"+address+"')";
            flag = jdbcUtils.updateByPreparedStatement(sql,null);

            }
            
        return flag;
    }

    /***
     * 鑾峰彇閰嶇疆琛ㄤ俊鎭�
     * @return
     * @throws Exception
     */
    public Confpojo getConf() throws Exception{
        String sql = "select c_key,c_value from t_conf ";
        List<Map<String, Object>> conf_list =  jdbcUtils.findModeResult(sql,null);
        if(conf_list!=null && !conf_list.isEmpty()){
           Iterator <Map<String,Object> > iterator = conf_list.iterator();
           Confpojo confpojo = new Confpojo();
            while(iterator.hasNext()){
                Map<String,Object> result_map = iterator.next();
                String c_key = (String) result_map.get("c_key");
                String c_value = (String) result_map.get("c_value");
                switch (c_key){
                    case "countDown":
                        confpojo.setCountDown(Integer.parseInt(c_value));
                        break;
                    case "msgTemplete":
                        confpojo.setMsgTemplete(c_value);
                        break;
                    case "port":
                        confpojo.setPort(c_value);
                        break;
                    case "baudRate":
                        confpojo.setBaudRate(c_value);
                        break;
                    case "pinCode":
                        confpojo.setPinCode(c_value);
                        break;
                    case "manufacturer":
                        confpojo.setManufacturer(c_value);
                        break;
                    case "model":
                        confpojo.setModel(c_value);
                        break;
                    case "testPhoneNo":
                        confpojo.setTestPhoneNo(c_value);
                        break;
                    default:
                        break;
                }

            }
            return confpojo;
        }
        return  null;
    }
    
    public void closeConn(){
    	jdbcUtils.releaseConn();
    }
//    public static void main(String[] args) throws Exception {
//    	System.out.println(UUID.randomUUID().toString());
////        DBHelper dbHelper = new DBHelper();
////        CarInfo CarInfo = new CarInfo();
////        CarInfo.setC_id("c26170d2-381b-4de2-b297-d48fd4d0b4b8");
////        System.out.println( dbHelper.delCarInfo(CarInfo));
////        Confpojo confpojo = dbHelper.getConf();
////        System.out.println(confpojo.getCountDown());
////        CarInfo CarInfo = new CarInfo();
////        CarInfo.setC_id(UUID.randomUUID().toString());
////        CarInfo.setC_car_id("");
////        CarInfo.setC_address("");
////        CarInfo.setC_identification_card("");
////        CarInfo.setC_Inspection_expirationTime("");
////        CarInfo.setC_Insurance_expirationTime("");
////        CarInfo.setC_name("");
////        CarInfo.setC_phone("");
//
//
//    }
}
