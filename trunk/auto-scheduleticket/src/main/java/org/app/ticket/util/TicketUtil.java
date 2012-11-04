package org.app.ticket.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.app.ticket.bean.TrainQueryInfo;
import org.app.ticket.constants.Constants;
import org.app.ticket.constants.StationConstant;
import org.app.ticket.core.ClientCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Title: TicketUtil.java
 * @Description: org.app.ticket.util
 * @Package org.app.ticket.util
 * @author hncdyj123@163.com
 * @date 2012-9-26
 * @version V1.0
 * 
 */
public class TicketUtil {
	private static final Logger logger = LoggerFactory.getLogger(ClientCore.class);

	private static Map<String, String> cityName2Code = new HashMap<String, String>();
	static {
		String city[] = StationConstant.stationString.split("@");
		for (String tmp : city) {
			if (tmp.isEmpty())
				continue;
			String temp[] = tmp.split("\\|");
			cityName2Code.put(temp[1], temp[2]);
		}
	}
	public final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PART_FORMAT = "yyyy-MM-dd";
	public static final String TIME_PART_FORMAT = "HH:mm:ss.SSS";

	public static String getCityCode(String cityName) {
		return cityName2Code.get(cityName);
	}

	/**
	 * 查询返回字符对象化
	 * 
	 * @param response
	 * @return List<TrainQueryInfo>
	 */
	public static List<TrainQueryInfo> parserQueryInfo(String response) {
		List<TrainQueryInfo> tqis = new ArrayList<TrainQueryInfo>();
		response = response.replaceAll("&nbsp;", "");
		logger.info("Response String is " + response);
		// Session 过期
		if ("-10".equals(response)) {
			logger.info("Session is timeout!");
			return tqis;
		}
		String[] number = response.split("\\\\n");
		for (int i = 0; i < number.length; i++) {
			String info = number[i];
			if (!StringUtil.isEmptyString(info)) {
				String[] ticketInfo = info.split(",");
				TrainQueryInfo trainQueryInfo = new TrainQueryInfo();
				trainQueryInfo.setTrainCode(ticketInfo[0]);
				trainQueryInfo.setTrainNo(ticketInfo[1].substring(ticketInfo[1].lastIndexOf("\'>") + 2, ticketInfo[1].lastIndexOf("</span>")));
				String startstation = isStartOrEndStation(ticketInfo[2]);
				trainQueryInfo.setFromStation(startstation.substring(0, startstation.lastIndexOf("<br>")));
				trainQueryInfo.setFromStationCode(TicketUtil.getCityCode(startstation.substring(0, startstation.lastIndexOf("<br>"))));
				trainQueryInfo.setStartTime(startstation.substring(startstation.lastIndexOf("<br>") + 4, startstation.length()));
				String endstation = isStartOrEndStation(ticketInfo[3]);
				trainQueryInfo.setToStation(endstation.substring(0, endstation.lastIndexOf("<br>")));
				trainQueryInfo.setToStationCode(TicketUtil.getCityCode(endstation.substring(0, endstation.lastIndexOf("<br>"))));
				trainQueryInfo.setEndTime(endstation.substring(endstation.lastIndexOf("<br>") + 4, endstation.length()));
				trainQueryInfo.setTakeTime(ticketInfo[4]);
				trainQueryInfo.setBuss_seat(isExistTicket(ticketInfo[5]));
				trainQueryInfo.setBest_seat(isExistTicket(ticketInfo[6]));
				trainQueryInfo.setOne_seat(isExistTicket(ticketInfo[7]));
				trainQueryInfo.setTwo_seat(isExistTicket(ticketInfo[8]));
				trainQueryInfo.setVag_sleeper(isExistTicket(ticketInfo[9]));
				trainQueryInfo.setSoft_sleeper(isExistTicket(ticketInfo[10]));
				trainQueryInfo.setHard_sleeper(isExistTicket(ticketInfo[11]));
				trainQueryInfo.setSoft_seat(isExistTicket(ticketInfo[12]));
				trainQueryInfo.setHard_seat(isExistTicket(ticketInfo[13]));
				trainQueryInfo.setNone_seat(isExistTicket(ticketInfo[14]));
				trainQueryInfo.setOther_seat(isExistTicket(ticketInfo[15]));
				String mmString = ticketInfo[16];
				trainQueryInfo.setMmStr(getMMString(mmString)[10]);
				trainQueryInfo.setTrainno4(getMMString(mmString)[3]);
				trainQueryInfo.setYpInfoDetail(getMMString(mmString)[9]);
				tqis.add(trainQueryInfo);
			}
		}
		return tqis;
	}

	/**
	 * 是否存在火车票信息 火车票信息存在下面三种情况 1.<font color='darkgray'>无</font> 2.-- 3.<font
	 * color='darkgray'>有</font> 4.有票情况下可能是数字
	 * 
	 * @return String
	 */
	private static String isExistTicket(String ticket) {
		if (Constants.TICKET_INFO.equals(ticket)) {
			return ticket;
		}
		if (!ticket.contains("\'>")) {
			return ticket;
		}
		String ticketinfo = ticket.substring(ticket.lastIndexOf("\'>") + 2, ticket.lastIndexOf("</font>"));
		return ticketinfo;
	}

	/**
	 * 判断是否是起始或者终点站 如果不是起始或终点没有<img src='/otsweb/images/tips/first.gif'>或<img
	 * src='/otsweb/images/tips/last.gif'>
	 * 
	 * @return String
	 */
	private static String isStartOrEndStation(String startOrEnd) {
		if (startOrEnd.contains("\'>")) {
			startOrEnd = startOrEnd.substring(startOrEnd.lastIndexOf("\'>") + 2, startOrEnd.length());
		}
		System.out.println(startOrEnd);
		return startOrEnd;
	}

	/**
	 * 获取token
	 * 
	 * @param html
	 * @return String
	 */
	public static void getToken(String html) {
		String regex = "TOKEN\" value=\"";
		Constants.TOKEN = html.substring(html.indexOf(regex) + regex.length(), html.indexOf(regex) + regex.length() + 32);
		logger.debug("token = " + Constants.TOKEN);
	}

	public static void getCredential(String html) {
		String regex = "name=\"leftTicketStr\" id=\"left_ticket\"";
		Constants.LEFTTICKETSTR = html.substring(html.indexOf(regex) + regex.length() + 9, html.indexOf(regex) + 86);
		logger.debug("leftTicketStr = " + Constants.LEFTTICKETSTR);
	}

	/**
	 * 获取预定按钮中的相关参数
	 * 
	 * @param mmString
	 * @return String[]
	 */
	private static String[] getMMString(String mmString) {
		String[] mString = new String[20];
		if (mmString.contains("class=\'yuding_u\'")) {
			String regex = "getSelected(\'";
			mString = mmString.substring(mmString.indexOf("regex") + regex.length(), mmString.indexOf("\')")).split("#");
			logger.debug("mmString is " + mString);
		}
		return mString;
	}

}
