package com.simulation.websocket.config;

public class Constant {
	
	//运行开始
	public static String EQUIPMENT_START_ERROR="000";
	public static String EQUIPMENT_START_SUCCESS="001";
	
	public static String SECTION_START_ERROR="010";
	public static String SECTION_START_SUCCESS="011";
	
	public static String PROCESS_START_ERROR="020";
	public static String PROCESS_START_SUCCESS="021";
	
	//运行中
	public static String EQUIPMENT_RUN_ERROR="100";
	public static String EQUIPMENT_RUN_SUCCESS="101";
	
	public static String SECTION_RUN_ERROR="110";
	public static String SECTION_RUN_SUCCESS="111";
	
	public static String PROCESS_RUN_ERROR="120";
	public static String PROCESS_RUN_SUCCESS="121";
	
	//运行结束
	public static String EQUIPMENT_RUN_END="200";
	public static String SECTION_RUN_END="210";
	public static String PROCESS_RUN_END="220";
	
	//修改
	public static String EQUIPMENT_CHANGE_ERROR="300";
	public static String EQUIPMENT_CHANGE_SUCCESS="301";
	
	public static String SECTION_CHANGE_ERROR="310";
	public static String SECTION_CHANGE_SUCCESS="311";
	
	public static String PROCESS_CHANGE_ERROR="320";
	public static String PROCESS_CHANGE_SUCCESS="321";
	
	//数据类型
	public static String DATA_TYPE_EQUIPMENTA_ACCM="00";
	public static String DATA_TYPE_EQUIPMENTA_STRM="01";
	public static String DATA_TYPE_SECTION_ACCM="10";
	public static String DATA_TYPE_SECTION_STRM="11";
	public static String DATA_TYPE_PROCESS_ACCM="20";
	public static String DATA_TYPE_PROCESS_STRM="21";
	
	//保持到内存数据库的值
	public static String EQUIPMENT_REDIUS_SAVE_DATA="0-0-"; //类型+流序号+时间+值(前面用+好分割，后面用;号分割)
	public static String EQUIPMENT_REDIUS_UPDATE_DATA="0-1-";  //类型+流序号+第几个数据+改变后值(用分号分割)
	public static String EQUIPMENT_REDIUS_CHANGE_DATA="0-2-";  //类型+流序号+第几个数据+值(用分号分割)
	
	public static String SECTION_REDIUS_SAVE_DATA="1-0-";
	public static String SECTION_REDIUS_UPDATE_DATA="1-1-";
	public static String SECTION_REDIUS_CHANGE_DATA="1-2-";
	
	public static String PROCESS_REDIUS_SAVE_DATA="2-0-";
	public static String PROCESS_REDIUS_UPDATE_DATA="2-1-";
	public static String PROCESS_REDIUS_CHANGE_DATA="2-1-";
	
	
	public static String DATA_TYPE_STRM="1";
	public static String DATA_TYPE_ACCM="2";

}
