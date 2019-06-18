package com.simulation.service.sys;

import java.util.List;
import java.util.Set;

public interface RedisService {
	
	/** 
     * 新增 
     * <br>------------------------------<br> 
     * @param user 
     * @return 
     */  
     boolean add(String key ,String value);  

    /** 
     * 批量新增 使用pipeline方式 
     * <br>------------------------------<br> 
     * @param list 
     * @return 
     */  
     boolean add(List<String> key,List<String> values);  

    /** 
     * 删除 
     * <br>------------------------------<br> 
     * @param key 
     */  
     void delete(String key);  

    /** 
     * 删除多个 
     * <br>------------------------------<br> 
     * @param keys 
     */  
     void delete(List<String> keys);  

    /** 
     * 修改 
     * <br>------------------------------<br> 
     * @param user 
     * @return  
     */  
     boolean update(String key,String value);  

    /** 
     * 通过key获取 
     * <br>------------------------------<br> 
     * @param keyId 
     * @return  
     */  
     String get(String key);
     
     /**
      * 查看redis里有多少数据
      */
     public long dbSize();
     
     
     /**
      * 通过正则匹配keys
      * 
      * @param pattern
      * @return
      */
     Set<String> setkeys(String pattern);

     /**
      * 检查key是否已经存在
      * 
      * @param key
      * @return
      */
      boolean exists(String key);

     /**
      * 清空redis 所有数据
      * 
      * @return
      */
    String flushDB();
     
     /**
      * 检查是否连接成功
      * 
      * @return
      */
     String ping();

     
     List<String> find(String key);
     
     void deleteKeys(String key);
}
