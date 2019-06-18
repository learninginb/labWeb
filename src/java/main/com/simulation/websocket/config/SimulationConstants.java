package com.simulation.websocket.config;

/**
 * 仿真/测试过程中的常数
 * 
 * @author starlist
 *
 */
public class SimulationConstants {
	
	//测试类型
	public static String EQUIPMENT_TEST = "1"; // 设备
	public static String SECTION_TEST = "2"; // 工段
	public static String PROCESS_TEST = "3"; // 工艺
	public static String CONTROLLER_TEST = "4"; // 工艺
	// 数据类型
	public static String FLOW_TYPE_STRM = "1"; // 流类型-流
	public static String FLOW_TYPE_ACCM = "2"; // 流类型-积

	// 返回给前段的数据-1
	// 启动正常 1-设备;2-工段;3-工艺
	// 参数定义 3位 ABC--A类型;B设备/工段/工艺;C-值
	// 启动-1
	public static String EQUIPMENT_START_ERROR = "1110";
	public static String EQUIPMENT_START_SUCCESS = "1111";

	public static String SECTION_START_ERROR = "1120";
	public static String SECTION_START_SUCCESS = "1121";

	public static String PROCESS_START_ERROR = "1130";
	public static String PROCESS_START_SUCCESS = "1131";
	
	public static String CONTROLLER_START_ERROR = "1140";
	public static String CONTROLLER_START_SUCCESS = "1141";

	// 运行-2
	public static String EQUIPMENT_RUN_ERROR = "1210";
	public static String EQUIPMENT_RUN_SUCCESS = "1211";

	public static String SECTION_RUN_ERROR = "1220";
	public static String SECTION_RUN_SUCCESS = "1221";

	public static String PROCESS_RUN_ERROR = "1230";
	public static String PROCESS_RUN_SUCCESS = "1231";
	
	public static String CONTROLLER_RUN_ERROR = "1240";
	public static String CONTROLLER_RUN_SUCCESS = "1241";

	// 结束-3
	public static String EQUIPMENT_RUN_END = "1310";
	public static String SECTION_RUN_END = "1320";
	public static String PROCESS_RUN_END = "1330";
	public static String CONTROLLER_RUN_END = "1340";

	// 改变-4
	public static String EQUIPMENT_CHANGE_ERROR = "1410";
	public static String EQUIPMENT_CHANGE_SUCCESS = "1411";

	public static String SECTION_CHANGE_ERROR = "1420";
	public static String SECTION_CHANGE_SUCCESS = "1421";

	public static String PROCESS_CHANGE_ERROR = "1430";
	public static String PROCESS_CHANGE_SUCCESS = "1431";
	
	public static String CONTROLLER_CHANGE_ERROR = "1440";
	public static String CONTROLLER_CHANGE_SUCCESS = "1441";

	// 暂停-5
	public static String EQUIPMENT_PAUSE_ERROR = "1510";
	public static String EQUIPMENT_PAUSE_SUCCESS = "1511";

	public static String SECTION_PAUSE_ERROR = "1520";
	public static String SECTION_PAUSE_SUCCESS = "1521";

	public static String PROCESS_PAUSE_ERROR = "1530";
	public static String PROCESS_PAUSE_SUCCESS = "1531";
	
	public static String CONTROLLER_PAUSE_ERROR = "1540";
	public static String CONTROLLER_PAUSE_SUCCESS = "1541";

	// 取消-6
	public static String EQUIPMENT_CANCEL_ERROR = "1610";
	public static String EQUIPMENT_CANCEL_SUCCESS = "1611";

	public static String SECTION_CANCEL_ERROR = "1620";
	public static String SECTION_CANCEL_SUCCESS = "1621";

	public static String PROCESS_CANCEL_ERROR = "1630";
	public static String PROCESS_CANCEL_SUCCESS = "1631";
	
	public static String CONTROLLER_CANCEL_ERROR = "1640";
	public static String CONTROLLER_CANCEL_SUCCESS = "1641";

	// 停止-7
	public static String EQUIPMENT_STOP_ERROR = "1710";
	public static String EQUIPMENT_STOP_SUCCESS = "1711";

	public static String SECTION_STOP_ERROR = "1720";
	public static String SECTION_STOP_SUCCESS = "1721";

	public static String PROCESS_STOP_ERROR = "1730";
	public static String PROCESS_STOP_SUCCESS = "1731";
	
	public static String CONTROLLER_STOP_ERROR = "1740";
	public static String CONTROLLER_STOP_SUCCESS = "1741";

	// 继续-8
	public static String EQUIPMENT_CONTINUE_ERROR = "1810";
	public static String EQUIPMENT_CONTINUE_SUCCESS = "1711";

	public static String SECTION_CONTINUE_ERROR = "1820";
	public static String SECTION_CONTINUE_SUCCESS = "1821";

	public static String PROCESS_CONTINUE_ERROR = "1830";
	public static String PROCESS_CONTINUE_SUCCESS = "1831";
	
	public static String CONTROLLER_CONTINUE_ERROR = "1840";
	public static String CONTROLLER_CONTINUE_SUCCESS = "1841";

	// 数据-9
	public static String EQUIPMENT_DATA = "1810";
	public static String SECTION_DATA = "1820";
	public static String PROCESS_DATA= "1830";
	public static String CONTROLLER_DATA= "1840";
	// 前端返给后台的数据-2(ABC-A-2;B-类型;C-设备/工段/工艺
	// 改变流/积累-1
	public static String CHANGE_EQUIPMENT_FLOW = "211"; // key:211-equipment_test_id
														// value:A;B;C;D:A-1/2(流积类型)-B(第几股流)-C(第nc+C)个值-D(改变后的值)
	public static String CHANGE_SECTION_FLOW = "212";
	public static String CHANGE_PROCESS_FLOW = "213";
	public static String CHANGE_CONTROLLER_FLOW = "214";

	// 暂停-2
	public static String PAUSE_EQUIPMENT = "221"; // key:211-equipment_test_id
													// value-1
	public static String PAUSE_SECTION = "222";
	public static String PAUSE_PROCESS = "223";
	public static String PAUSE_CONTROLLER = "224";

	// 取消-3
	public static String CANCEL_EQUIPMENT = "231"; // key:211-equipment_test_id
													// value-1
	public static String CANCEL_SECTION = "232";
	public static String CANCEL_PROCESS = "233";
	public static String CANCEL_CONTROLLER = "234";

	// 停止-4
	public static String STOP_EQUIPMENT = "241"; // key:211-equipment_test_id
													// value-1
	public static String STOP_SECTION = "242";
	public static String STOP_PROCESS = "243";
	public static String STOP_CONTROLLER = "244";

	// 开始-5
	public static String START_EQUIPMENT = "251"; // key:211-equipment_test_id
													// value-1
	public static String START_SECTION = "252";
	public static String START_PROCESS = "253";
	public static String START_CONTROLLER = "254";
	// 继续-6
	public static String CONTINUE_EQUIPMENT = "261"; // key:211-equipment_test_id
	// value-1
	public static String CONTINUE_SECTION = "262";
	public static String CONTINUE_PROCESS = "263";
	public static String CONTINUE_CONTROLLER = "264";

	// 改变控制器-7
	public static String CHANGE_EQUIPMENT_CONTROLLER = "271"; // key:211-equipment_test_id
																// value:A;B;C:A-(第几个控制器)-B(参数值名称)个值-C(改变后的值)
	public static String CHANGE_SECTION_CONTROLLER = "272";
	public static String CHANGE_PROCESS_CONTROLLER = "273";
	public static String CHANGE_CONTROLLER_CONTROLLER = "274";

	// 仿真保存到内存数据库的值3
	// 运行流保存1
	public static String EQUIPMENT_REDIUS_SAVE_FLOW = "311"; // key:211-equipment_test_id
																// A,B,C,D-A类型;B-流序号-C-时间;D值(用;号分割)
	public static String SECTION_REDIUS_SAVE_FLOW = "312";
	public static String PROCESS_REDIUS_SAVE_FLOW = "313";
	public static String CONTROLLER_REDIUS_SAVE_FLOW = "314";

	// 运行控制器参数保存2
	public static String EQUIPMENT_REDIUS_SAVE_CONTROLLER = "321"; // key:211-equipment_test_id
																	// A,B,C,D-A第几个控制器;B-参数值名称-C-时间;D值(用;号分割)
	public static String SECTION_REDIUS_SAVE_CONTROLLER = "322";
	public static String PROCESS_REDIUS_SAVE_CONTROLLER = "323";
	public static String CONTROLLER_REDIUS_SAVE_CONTROLLER = "324";

	// 运行变更流保存-3
	public static String EQUIPMENT_REDIUS_UPDATE_FLOW = "331"; // 类型+流序号+第几个数据+改变后值
	public static String SECTION_REDIUS_UPDATE_FLOW = "332";
	public static String PROCESS_REDIUS_UPDATE_FLOW = "333";
	public static String CONTROLLER_REDIUS_UPDATE_FLOW = "334";

	// 运行变更控制器保存-4
	public static String EQUIPMENT_REDIUS_UPDATE_CONTROLLER = "341"; // 第几个控制器+参数值名称+改变后值
	public static String SECTION_REDIUS_UPDATE_CONTROLLER = "342";
	public static String PROCESS_REDIUS_UPDATE_CONTROLLER = "343";
	public static String CONTROLLER_REDIUS_UPDATE_CONTROLLER = "344";

}
