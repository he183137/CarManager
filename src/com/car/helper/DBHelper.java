package com.car.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.car.pojo.CarInfo;
import com.car.pojo.Confpojo;
import com.car.pojo.SendConfPojo;
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
     * 获取所有车辆
     *
     * @return
     * @throws Exception
     */
    public List<CarInfo> getAllCarInfo() throws Exception {
        String sql = "select c_id, " +
                "c_annual_cycle," +
                "c_Inspection_expirationTime," +
                "c_name," +
                "c_phone," +
                "c_car_id" +
                " from t_info ";
        List <Map<String,Object>> carInfo_result = jdbcUtils.findModeResult(sql, null);
        Iterator<Map<String,Object>> iterator = carInfo_result.iterator();
        List<CarInfo> carInfo_list = new ArrayList<>();
        while(iterator.hasNext()){
        	Map<String, Object> map_res = iterator.next();
        	CarInfo carInfo = new CarInfo();
        	carInfo.setC_annual_cycle(Integer.parseInt(map_res.get("c_annual_cycle").toString()));
        	carInfo.setC_car_id(map_res.get("c_car_id").toString());
        	carInfo.setC_id(map_res.get("c_id").toString());
//        	carInfo.setC_identification_card(map_res.get("c_identification_card").toString());
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
     * 根据id获取车辆
     *
     * @param id
     * @return 
     * @throws Exception
     */
    public CarInfo getCarInfoByOne(String id) throws Exception {
        List<Object> parm = new ArrayList<Object>();
        parm.add(id);
        String sql = "select c_id, " +
                "t.c_annual_cycle," +
                "t.c_Inspection_expirationTime," +
                "t.c_name," +
                "t.c_phone," +
                "t.c_car_id," +   
                " from t_info t " +
                "where c_id = ?";
        CarInfo CarInfo =  jdbcUtils.findSimpleRefResult(sql, parm, CarInfo.class);
        jdbcUtils.releaseResultSet();
        return CarInfo;
    }
    
    

    /**
     * 根据name获取车辆
     *
     * @param name
     * @return 
     * @throws Exception
     */
    public List<CarInfo> getCarInfoByName(String name) throws Exception {
      
        String sql = "select c_id, " +
                "t.c_annual_cycle," +
                "t.c_Inspection_expirationTime," +
                "t.c_name," +
                "t.c_phone," +
                "t.c_car_id" +   
                " from t_info t " +
                "where c_name like '%"+name+"%'";
        logger.info(sql);
        List<Map<String, Object>> reslut_list =  jdbcUtils.findModeResult(sql, null);
        if(reslut_list.isEmpty()){
        	return null;
        }
        Iterator<Map<String,Object>> iterator = reslut_list.iterator();
        List<CarInfo> carInfo_list = new ArrayList<>();
        while(iterator.hasNext()){
        	Map<String, Object> map_res = iterator.next();
        	CarInfo carInfo = new CarInfo();
        	carInfo.setC_annual_cycle(Integer.parseInt(map_res.get("c_annual_cycle").toString()));
        	carInfo.setC_car_id(map_res.get("c_car_id").toString());
        	carInfo.setC_id(map_res.get("c_id").toString());

        	carInfo.setC_Inspection_expirationTime(map_res.get("c_Inspection_expirationTime").toString());
        	carInfo.setC_name(map_res.get("c_name").toString());
        	carInfo.setC_phone(map_res.get("c_phone").toString());
        	carInfo_list.add(carInfo);
        }
        jdbcUtils.releaseResultSet();
        return carInfo_list;
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
     * 删除
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
     * 批量修改
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
     * 修改车辆信息
     */
    public boolean updateCarInfo(CarInfo CarInfo) throws Exception{
        boolean flag = false;
       if(CarInfo!=null){
           String id = CarInfo.getC_id();
           int annual_cycle = CarInfo.getC_annual_cycle();
           String name = CarInfo.getC_name();
           String phone = CarInfo.getC_phone();
//           String address= CarInfo.getC_address();
           String car_id = CarInfo.getC_car_id();
           String inspection_time = CarInfo.getC_Inspection_expirationTime();
           //String insurance_time = CarInfo.getC_Insurance_expirationTime();
           String sql = "UPDATE t_info " +
                   " SET c_annual_cycle = '"+annual_cycle +"'," +
                   " c_Inspection_expirationTime = '"+inspection_time+"'," +
                   " c_name = '"+name+"'," +
                   " c_phone='"+phone+"'," +
                   " c_car_id='"+car_id+"'" +
                   " WHERE c_id = '"+id+"'";
           flag = jdbcUtils.updateByPreparedStatement(sql,null);
           if (!flag){
               logger.error("update user error. The sql :"+sql);
           }
       }
        return  flag;
    }
    /***
     *添加车辆信息
     */
    public boolean addCarInfo(CarInfo CarInfo) throws Exception{
        boolean flag = false;
        if (CarInfo != null) {
        	if( CarInfo.getC_id()==null ||"".equals(CarInfo.getC_id())){
        		CarInfo.setC_id(UUID.randomUUID().toString());
        	}
            String id = CarInfo.getC_id();
            int annual_cycle = CarInfo.getC_annual_cycle();
            String name = CarInfo.getC_name();
            String phone = CarInfo.getC_phone();
//            String address= CarInfo.getC_address();
            String car_id = CarInfo.getC_car_id();
            String inspection_time = CarInfo.getC_Inspection_expirationTime();
            //String insurance_time = CarInfo.getC_Insurance_expirationTime();
            String sql = " insert into t_info  (c_id, " +
                    " c_annual_cycle," +
                    " c_Inspection_expirationTime," +                
                    " c_name," +
                    " c_phone," +
                    " c_car_id )" +
                    "values('"+id+"','"+annual_cycle+"','"+inspection_time+"','"+name+"','"+phone+"','"+car_id+"')";
            flag = jdbcUtils.updateByPreparedStatement(sql,null);

            }
            
        return flag;
    }

    /***
     * 配置pojo
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
    
  
    
    /***
     * 短信pojo
     * @return
     * @throws Exception
     */
    public SendConfPojo getMsgConf() throws Exception{
        String sql = "select c_key,c_value from t_conf ";
        List<Map<String, Object>> conf_list =  jdbcUtils.findModeResult(sql,null);
        if(conf_list!=null && !conf_list.isEmpty()){
           Iterator <Map<String,Object> > iterator = conf_list.iterator();
           SendConfPojo SendConfPojo = new SendConfPojo();
            while(iterator.hasNext()){
                Map<String,Object> result_map = iterator.next();
                String c_key = (String) result_map.get("c_key");
                String c_value = (String) result_map.get("c_value");
                switch (c_key){
                    case "countDown":
                    	SendConfPojo.setCountDown(Integer.parseInt(c_value));
                        break;
                    case "msgTemplete":
                    	SendConfPojo.setMsgTemplete(c_value);
                        break;
                 
                    default:
                        break;
                }

            }
            return SendConfPojo;
        }
        return  null;
    }
    
    
    public void updateConf(Confpojo confpojo)throws Exception{
          if(confpojo!=null){
             String port = confpojo.getPort();
             String baudrate= confpojo.getBaudRate();
             String manufacturer =  confpojo.getManufacturer();
             String model = confpojo.getModel();
             String pincode = confpojo.getPinCode();
             String testphoneNo = confpojo.getTestPhoneNo() ==null? "":confpojo.getTestPhoneNo();
            	 updateConf("port",port);
            	 updateConf("baudRate",baudrate);
            	 updateConf("manufacturer",manufacturer);
            	 updateConf("model",model);
                 updateConf("pinCode",pincode);
                 updateConf("testPhoneNo",testphoneNo); 
         }
    }
    
    public void updateMsgConf(SendConfPojo sendConfPojo)throws Exception{
        if(sendConfPojo!=null){
           int countdown = sendConfPojo.getCountDown();
          String msgTemplete = sendConfPojo.getMsgTemplete();
          	 updateConf("countDown",countdown);
          	 updateConf("msgTemplete",msgTemplete);
          	
       }
  }
    
    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException {
		return jdbcUtils.findModeResult(sql, params);
	}
    
    public boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException {
    	return jdbcUtils.updateByPreparedStatement(sql, params);
	}
    
    
    private void updateConf(String key ,String value) throws Exception{

       List <Object> list =  this.getParmList(value,key);
       if(!list.isEmpty()&& list.size()==2){
    	   String sql = "UPDATE t_conf set c_value = ? where c_key = ?"; 
    	   boolean flag = jdbcUtils.updateByPreparedStatement(sql,list);
    	   if(!flag){
    		   throw new Exception("update Conf fail ,key: "+key+" value: "+value);
    	   }
       }
    }
    
    
    private void updateConf(String key ,int value) throws Exception{

        List <Object> list =  this.getParmList(value,key);
        if(!list.isEmpty()&& list.size()==2){
     	   String sql = "UPDATE t_conf set c_value = ? where c_key = ?"; 
     	   boolean flag = jdbcUtils.updateByPreparedStatement(sql,list);
     	   if(!flag){
     		   throw new Exception("update Conf fail ,key: "+key+" value: "+value);
     	   }
        }
     }
    
    /**
     * 生成参数
     * @param parmn
     * @return
     */
    private  List<Object> getParmList(Object ...parmn){
    	List <Object> parm_list = new ArrayList<>();
    	for(Object parm: parmn){
    		parm_list.add(parm);
    	}
    	
    	return parm_list;
    }
    
    public void closeConn(){
    	jdbcUtils.releaseConn();
    }
    public static void main(String[] args) throws Exception {
    	SendConfPojo confPojo = DBHelper.getInstance().getMsgConf();
    	System.out.println(confPojo.getCountDown());
    }
}

