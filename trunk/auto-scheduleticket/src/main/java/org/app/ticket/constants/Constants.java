package org.app.ticket.constants;

/**
 * 功能常量类
 * 
 * @Title: Constants.java
 * @Description: org.app.ticket.constants
 * @Package org.app.ticket.constants
 * @author hncdyj123@163.com
 * @date 2012-9-26
 * @version V1.0
 * 
 */
public class Constants {
	// JSESSIONID
	public static final String JSESSIONID = "JSESSIONID";
	// BIGIPSERVEROTSWEB
	public static final String BIGIPSERVEROTSWEB = "BIGipServerotsweb";
	// JSESSIONID VALUE
	public static String JSESSIONID_VALUE = "";
	// BIGIPSERVEROTSWEB VALUE
	public static String BIGIPSERVEROTSWEB_VALUE = "";
	// TOKEN
	public static String TOKEN;
	public static String LEFTTICKETSTR;
	// 获取登录提交的随机数
	public static final String POST_UTL_LOGINACTION_LOGINAYSNSUGGEST = "http://dynamic.12306.cn/otsweb/loginAction.do?method=loginAysnSuggest";
	// 登录
	public static final String POST_UTL_LOGINACTION = "http://dynamic.12306.cn/otsweb/loginAction.do?method=login";
	// 获取TOKEN URL
	public static final String GET_URL_USERTOKEN = "http://dynamic.12306.cn/otsweb/order/querySingleAction.do?method=init";
	// 查询余票URL
	public static final String GET_URL_QUERYTICKET = "http://dynamic.12306.cn/otsweb/order/querySingleAction.do?method=queryLeftTicket";
	// 提交火车车次信息
	public static final String POST_URL_SUBMUTORDERREQUEST = "http://dynamic.12306.cn/otsweb/order/querySingleAction.do?method=submutOrderRequest";
	// 获取验证码(登录)
	public static final String GET_LOGINURL_PASSCODE = "http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand";
	// 获取验证码(提交订单)
	public static final String GET_SUBMITURL_PASSCODE = "http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=randp";
	// 获取联系人(解析html能获得)和提交令牌
	public static final String GET_URL_CONFIRMPASSENGER = "http://dynamic.12306.cn/otsweb/order/confirmPassengerAction.do?method=init";
	// 获取火车票数量
	public static final String GET_URL_GETQUEUECOUNT = "http://dynamic.12306.cn/otsweb/order/confirmPassengerAction.do?method=getQueueCount";
	// 提交订单信息
	public static final String POST_URL_CONFIRMSINGLEFORQUEUEORDER = "http://dynamic.12306.cn/otsweb/order/confirmPassengerAction.do?method=confirmSingleForQueueOrder";
	// &
	public static final String SIGN = "&";
	// 火车票信息
	public static final String TICKET_INFO = "--";

	/******************* 系统配置 ****************************/
	// 指定车次
	public static final String SYS_TRAINCODE = "traincode";
	// 指定座位席别
	public static final String SYS_USERSEAT = "settype";
	// 线程休眠时间
	public static final String SYS_THREAD_SLEEPTIME = "5000";
	// 系统日期输入字符
	public static final String SYS_STRING_DATEFORMAT = "-  -";
	// 无票状态的2种显示字符
	public static final String SYS_TICKET_SIGN_1 = "--";
	public static final String SYS_TICKET_SIGN_2 = "无";

	/******************* 座位席别 ****************************/
	public static String BUSS_SEAT = "9"; // 商务座
	public static String BEST_SEAT = "P";// 特等座(余票)
	public static String ONE_SEAT = "M";// 一等座(余票)
	public static String TWO_SEAT = "O";// 二等座(余票)
	public static String VAG_SLEEPER = "6";// 高级软卧(余票)
	public static String SOFT_SLEEPER = "4";// 软卧(余票)
	public static String HARD_SLEEPER = "3";// 硬卧(余票)
	public static String SOFT_SEAT = "2";// 软座(余票)
	public static String HARD_SEAT = "1";// 硬座(余票)
	public static String NONE_SEAT = "-1";// 无座(余票)

	/******************* 登录相关参数 **************************/
	public static final String LOGIN_LOGINRAND = "loginRand";
	public static final String LOGIN_USERNAME = "loginUser.user_name";
	public static final String LOGIN_NAMEERRORFOCUS = "nameErrorFocus";
	public static final String LOGIN_PASSWORDERRORFOCUS = "passwordErrorFocus";
	public static final String LOGIN_RANDCODE = "randCode";
	public static final String LOGIN_RANDERRORFOCUS = "randErrorFocus";
	public static final String LOGIN_REFUNDFLAG = "refundFlag";
	public static final String LOGIN_REFUNDLOGIN = "refundLogin";
	public static final String LOGIN_PASSWORD = "user.password";

	/******************* 查询余票相关参数 ************************/
	// 学生票
	public static final String QUERY_INCLUDESTUDENT = "includeStudent";
	// 始发站
	public static final String QUERY_FROM_STATION_TELECODE = "orderRequest.from_station_telecode";
	// 查询时间区间段
	public static final String QUERY_START_TIME_STR = "orderRequest.start_time_str";
	// 到站
	public static final String QUERY_TO_STATION_TELECODE = "orderRequest.to_station_telecode";
	// 出发日期
	public static final String QUERY_TRAIN_DATE = "orderRequest.train_date";
	// 火车编号
	public static final String QUERY_TRAIN_NO = "orderRequest.train_no";
	// 未知
	public static final String QUERY_SEATTYPEANDNUM = "seatTypeAndNum";
	// 查询类型
	public static final String QUERY_TRAINCLASS = "trainClass";
	// 查询全部
	public static final String QUERY_TRAINPASSTYPE = "trainPassType";

	/**************** 获取火车票数量 ***********************/
	public static final String GETQUEUECOUNT_FROM = "from";
	public static final String GETQUEUECOUNT_SEAT = "seat";
	public static final String GETQUEUECOUNT_STATION = "station";
	public static final String GETQUEUECOUNT_TICKET = "ticket";
	public static final String GETQUEUECOUNT_TO = "to";
	public static final String GETQUEUECOUNT_TRAIN_DATE = "train_date";

	/**************** 提交订单相关参数 *********************/
	public static final String ORDER_ARRIVE_TIME = "arrive_time";
	public static final String ORDER_FROM_STATION_NAME = "from_station_name";
	public static final String ORDER_FROM_STATION_TELECODE = "from_station_telecode";
	public static final String ORDER_FROM_STATION_TELECODE_NAME = "from_station_telecode_name";
	public static final String ORDER_INCLUDE_STUDENT = "include_student";
	public static final String ORDER_LISHI = "lishi";
	public static final String ORDER_MMSTR = "mmStr";
	public static final String ORDER_ROUND_START_TIME_STR = "round_start_time_str";
	public static final String ORDER_ROUND_TRAIN_DATE = "round_train_date";
	public static final String ORDER_SEATTYPE_NUM = "seattype_num";
	public static final String ORDER_SINGLE_ROUND_TYPE = "single_round_type";
	public static final String ORDER_START_TIME_STR = "start_time_str";
	public static final String ORDER_STATION_TRAIN_CODE = "station_train_code";
	public static final String ORDER_TO_STATION_NAME = "to_station_name";
	public static final String ORDER_TO_STATION_TELECODE = "to_station_telecode";
	public static final String ORDER_TO_STATION_TELECODE_NAME = "to_station_telecode_name";
	public static final String ORDER_TRAIN_CLASS_ARR = "train_class_arr";
	public static final String ORDER_TRAIN_DATE = "train_date";
	public static final String ORDER_TRAIN_PASS_TYPE = "train_pass_type";
	public static final String ORDER_TRAIN_START_TIME = "train_start_time";
	public static final String ORDER_TRAINNO4 = "trainno4";
	public static final String ORDER_YPINFODETAIL = "ypInfoDetail";

	/******************* 提交购票信息 **************************/
	public static final String SUBMIT_LEFTTICKETSTR = "leftTicketStr";
	// 传入参数（姓名，证件类型，证件号码）
	public static final String SUBMIT_OLDPASSENGERS = "oldPassengers";
	// 传入参数 000000000000000000000000000000
	public static final String SUBMIT_BED_LEVEL_ORDER_NUM = "orderRequest.bed_level_order_num";
	public static final String SUBMIT_CANCEL_FLAG = "orderRequest.cancel_flag";
	public static final String SUBMIT_END_TIME = "orderRequest.end_time";
	public static final String SUBMIT_FROM_STATION_NAME = "orderRequest.from_station_name";
	public static final String SUBMIT_FROM_STATION_TELECODE = "orderRequest.from_station_telecode";
	// 传入参数 Y
	public static final String SUBMIT_ID_MODE = "orderRequest.id_mode";
	public static final String SUBMIT_RESERVE_FLAG = "orderRequest.reserve_flag";
	public static final String SUBMIT_SEAT_DETAIL_TYPE_CODE = "orderRequest.seat_detail_type_code";
	public static final String SUBMIT_START_TIME = "orderRequest.start_time";
	public static final String SUBMIT_STATION_TRAIN_CODE = "orderRequest.station_train_code";
	public static final String SUBMIT_TICKET_TYPE_ORDER_NUM = "orderRequest.ticket_type_order_num";
	public static final String SUBMIT_TO_STATION_NAME = "orderRequest.to_station_name";
	public static final String SUBMIT_TO_STATION_TELECODE = "orderRequest.to_station_telecode";
	public static final String SUBMIT_TO_SEAT_TYPE_CODE = "orderRequest.seat_type_code";
	public static final String SUBMIT_TOKEN = "org.apache.struts.taglib.html.TOKEN";
	// 传入参数（seat,seat_detail,ticket,姓名,cardtype,mobileno,Y）
	public static final String SUBMIT_PASSENGERTICKETS = "passengerTickets";
	public static final String SUBMIT_RANDCODE = "randCode";
	public static final String SUBMIT_TEXTFIELD = "textfield";

}
