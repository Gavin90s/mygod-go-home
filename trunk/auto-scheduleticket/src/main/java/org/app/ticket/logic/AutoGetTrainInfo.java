package org.app.ticket.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.app.ticket.bean.TrainQueryInfo;
import org.app.ticket.constants.Constants;
import org.app.ticket.core.MainWin;
import org.app.ticket.msg.ResManager;
import org.app.ticket.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动火车火车相关信息
 * 
 * @Title: AutoGetTrainInfo.java
 * @Description: org.app.ticket.logic
 * @Package org.app.ticket.logic
 * @author hncdyj123@163.com
 * @date 2012-9-29
 * @version V1.0
 * 
 */
public class AutoGetTrainInfo {
	private static final Logger logger = LoggerFactory.getLogger(AutoGetTrainInfo.class);

	private List<TrainQueryInfo> trainQueryInfoList;

	private MainWin mainWin;

	/** 指定的车 */
	private Map<String, TrainQueryInfo> specificTrains = new HashMap<String, TrainQueryInfo>();
	/** 所有有票的车(不包含指定的车) */
	private Map<String, TrainQueryInfo> specificSeatTrains = new HashMap<String, TrainQueryInfo>();

	public AutoGetTrainInfo() {

	}

	public AutoGetTrainInfo(List<TrainQueryInfo> trainQueryInfoList, MainWin mainWin) {
		this.trainQueryInfoList = trainQueryInfoList;
		this.mainWin = mainWin;
	}

	/**
	 * 对火车进行分类
	 * 
	 */
	public void trainQueryInfoClass() {
		String[] keys = getKeys();
		for (int j = 0; j < keys.length; j++) {
			if (StringUtil.isEmptyString(keys[j])) {
				break;
			}
			for (int i = trainQueryInfoList.size() - 1; i >= 0; i--) {
				if (keys[j].equals(trainQueryInfoList.get(i).getTrainNo())) {
					// 存放指定的车
					specificTrains.put(trainQueryInfoList.get(i).getTrainCode(), trainQueryInfoList.get(i));
					logger.debug("指定车次为:" + trainQueryInfoList.get(i).getTrainNo());
					trainQueryInfoList.remove(i);
				}
				if (!StringUtil.isEmptyString(trainQueryInfoList.get(i).getMmStr())) {
					specificSeatTrains.put(trainQueryInfoList.get(i).getTrainCode(), trainQueryInfoList.get(i));
				}
			}
		}
		System.out.println(1);
	}

	/**
	 * 席别选择
	 * 
	 * @return TrainQueryInfo
	 */
	public TrainQueryInfo getSeattrainQueryInfo() {
		String[] keys = getKeys();
		boolean isAssign = false;
		TrainQueryInfo returninfo = null;
		if (keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				if (StringUtil.isEmptyString(keys[i])) {
					break;
				}
				TrainQueryInfo info = specificTrains.get(keys[i]);
				// 勾选动车优先
				if (mainWin.isBoxkTwoSeat()) {
					if (!Constants.SYS_TICKET_SIGN_1.equals(info.getTwo_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getTwo_seat())) {
						try {
							if (Integer.parseInt(info.getTwo_seat()) >= trainQueryInfoList.size()) {
								returninfo = info;
								isAssign = true;
								logger.debug("动车优先车次为:" + info.getTrainCode());
								break;
							}
						} catch (NumberFormatException ex) {
							returninfo = info;
							isAssign = true;
							logger.debug("动车优先车次为:" + info.getTrainCode());
							break;
						}
					}
				}
				// 勾选卧铺优先
				if (mainWin.isHardSleePer()) {
					if (!Constants.SYS_TICKET_SIGN_1.equals(info.getHard_sleeper()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getHard_sleeper())) {
						try {
							if (Integer.parseInt(info.getHard_sleeper()) >= trainQueryInfoList.size()) {
								returninfo = info;
								isAssign = true;
								logger.debug("卧铺优先车次为:" + info.getTrainCode());
								break;
							}
						} catch (NumberFormatException ex) {
							returninfo = info;
							isAssign = true;
							logger.debug("卧铺优先车次为:" + info.getTrainCode());
							break;
						}
					}
				}
				returninfo = getSeattrainQueryInfo(info);
				logger.debug("指定车次为:" + info.getTrainCode());
				isAssign = true;
			}
		}
		if (!isAssign) {
			for (Map.Entry<String, TrainQueryInfo> map : specificSeatTrains.entrySet()) {
				TrainQueryInfo info = getSeattrainQueryInfo(map.getValue());
				if (info != null) {
					returninfo = info;
				}
				logger.debug("车次为:" + info.getTrainCode());
				break;
			}
		}
		return returninfo;
	}

	/**
	 * 席别选择 (二等座 -> 一等座 -> 硬卧 -> 软卧 -> 软座 -> 硬座-> 高级软卧 -> 特等座 -> 商务座 -> 无座 )
	 * 
	 * @param trainQueryInfo
	 * @return TrainQueryInfo
	 */
	public TrainQueryInfo getSeattrainQueryInfo(TrainQueryInfo info) {
		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getTwo_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getTwo_seat())) {
			try {
				if (Integer.parseInt(info.getTwo_seat()) >= trainQueryInfoList.size()) {
					return info;
				}
			} catch (NumberFormatException ex) {
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getOne_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getOne_seat())) {
			try {
				if (Integer.parseInt(info.getOne_seat()) >= trainQueryInfoList.size()) {
					return info;
				}
			} catch (NumberFormatException ex) {
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getHard_sleeper()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getHard_sleeper())) {
			try {
				if (Integer.parseInt(info.getHard_sleeper()) >= trainQueryInfoList.size()) {
					return info;
				}
			} catch (NumberFormatException ex) {
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getSoft_sleeper()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getSoft_sleeper())) {
			try {
				if (Integer.parseInt(info.getSoft_sleeper()) >= trainQueryInfoList.size()) {
					return info;
				}
			} catch (NumberFormatException ex) {
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getSoft_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getSoft_seat())) {
			try {
				if (Integer.parseInt(info.getSoft_seat()) >= trainQueryInfoList.size()) {
					return info;
				}
			} catch (NumberFormatException ex) {
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getHard_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getHard_seat())) {
			try {
				if (Integer.parseInt(info.getHard_seat()) >= trainQueryInfoList.size()) {
					return info;
				}
			} catch (NumberFormatException ex) {
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getVag_sleeper()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getVag_sleeper())) {
			try {
				if (Integer.parseInt(info.getVag_sleeper()) >= trainQueryInfoList.size()) {
					return info;
				}
			} catch (NumberFormatException ex) {
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getBest_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getBest_seat())) {
			try {
				if (Integer.parseInt(info.getBest_seat()) >= trainQueryInfoList.size()) {
					return info;
				}
			} catch (NumberFormatException ex) {
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getBuss_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getBuss_seat())) {
			try {
				if (Integer.parseInt(info.getBuss_seat()) >= trainQueryInfoList.size()) {
					return info;
				}
			} catch (NumberFormatException ex) {
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getNone_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getNone_seat())) {
			try {
				if (Integer.parseInt(info.getNone_seat()) >= trainQueryInfoList.size()) {
					return info;
				}
			} catch (NumberFormatException ex) {
				return info;
			}
		}
		return null;
	}

	private String[] getKeys() {
		return ResManager.getByKey(Constants.SYS_TRAINCODE).split(",");
	}
}
