package org.app.ticket.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.app.ticket.bean.TrainQueryInfo;
import org.app.ticket.bean.UserInfo;
import org.app.ticket.constants.Constants;
import org.app.ticket.core.MainWin;
import org.app.ticket.msg.ResManager;
import org.app.ticket.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动选择火车相关信息
 * 
 * @Title: AutoGetTrainInfo.java
 * @Description: org.app.ticket.logic
 * @Package org.app.ticket.logic
 * @author hncdyj123@163.com
 * @date 2012-9-29
 * @version V1.0
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AutoGetTrainInfo {
	private static final Logger logger = LoggerFactory.getLogger(AutoGetTrainInfo.class);
	// 存放列车信息
	private List<TrainQueryInfo> trainQueryInfoList;
	// 存放用户信息
	private List<UserInfo> userInfoList;
	// 主界面
	private MainWin mainWin;
	// 存放指定的列车
	private String[] specificTrainKeys;

	private String[] specificTrainSeat;

	private static Map seatMap = null;

	static {
		seatMap = new HashMap();
		seatMap.put("9", "商务座");
		seatMap.put("P", "特等座");
		seatMap.put("M", "一等座");
		seatMap.put("O", "二等座");
		seatMap.put("6", "高级软卧");
		seatMap.put("4", "软卧");
		seatMap.put("3", "硬卧");
		seatMap.put("2", "软座");
		seatMap.put("1", "硬座");
		seatMap.put("-1", "无座");
	}

	/** 指定的车 */
	private Map<String, TrainQueryInfo> specificTrains = new HashMap<String, TrainQueryInfo>();
	/** 所有有票的车(不包含指定的车) */
	private Map<String, TrainQueryInfo> specificSeatTrains = new HashMap<String, TrainQueryInfo>();

	public AutoGetTrainInfo(List<TrainQueryInfo> trainQueryInfoList, MainWin mainWin, List<UserInfo> userInfoList) {
		this.trainQueryInfoList = trainQueryInfoList;
		this.mainWin = mainWin;
		this.userInfoList = userInfoList;
		// 获取指定的列车
		specificTrainKeys = getKeys();
		// 获取指定的座位席别
		specificTrainSeat = getSeatKeys();
		trainQueryInfoClass();
	}

	/**
	 * 对火车进行分类
	 * 
	 */
	public void trainQueryInfoClass() {
		for (int j = 0; j < specificTrainKeys.length; j++) {
			if (StringUtil.isEmptyString(specificTrainKeys[j])) {
				break;
			}
			for (int i = trainQueryInfoList.size() - 1; i >= 0; i--) {
				if (specificTrainKeys[j].equals(trainQueryInfoList.get(i).getTrainNo())) {
					// 存放指定的车
					specificTrains.put(trainQueryInfoList.get(i).getTrainNo(), trainQueryInfoList.get(i));
					trainQueryInfoList.remove(i);
				}
			}
		}

		for (int i = trainQueryInfoList.size() - 1; i >= 0; i--) {
			// 获取指定车之外有票的列车
			if (!StringUtil.isEmptyString(trainQueryInfoList.get(i).getMmStr())) {
				specificSeatTrains.put(trainQueryInfoList.get(i).getTrainNo(), trainQueryInfoList.get(i));
				trainQueryInfoList.remove(i);
			}
		}

		String specificTrain = "";
		for (Map.Entry<String, TrainQueryInfo> key : specificTrains.entrySet()) {
			specificTrain += ((TrainQueryInfo) key.getValue()).getTrainNo() + ",";
		}
		String specificSeatTrain = "";
		for (Map.Entry<String, TrainQueryInfo> key : specificSeatTrains.entrySet()) {
			specificSeatTrain += ((TrainQueryInfo) key.getValue()).getTrainNo() + ",";
		}
		if (!StringUtil.isEmptyString(specificTrain)) {
			mainWin.messageOut.setText(mainWin.messageOut.getText() + "您指定的列车为:" + specificTrain.substring(0, specificTrain.length() - 1) + "\n");
			logger.debug("指定列车信息:" + specificTrain.substring(0, specificTrain.length() - 1));
		}
		if (!StringUtil.isEmptyString(specificSeatTrain)) {
			mainWin.messageOut.setText(mainWin.messageOut.getText() + "指定之外的列车为:" + specificSeatTrain.substring(0, specificSeatTrain.length() - 1) + "\n");
			logger.debug("指定之外列车信息:" + specificSeatTrain.substring(0, specificSeatTrain.length() - 1));
		}
	}

	/**
	 * 席别选择
	 * 
	 * @return TrainQueryInfo
	 */
	public TrainQueryInfo getSeattrainQueryInfo() {
		boolean isAssign = false;
		TrainQueryInfo returninfo = null;
		// 指定了车次
		if (specificTrainKeys.length > 0&&!StringUtil.isEmptyString(specificTrainKeys[0])) {
			for (int i = 0; i < specificTrainKeys.length; i++) {
				TrainQueryInfo info = specificTrains.get(specificTrainKeys[i]);
				// 勾选动车优先
				if (mainWin.isBoxkTwoSeat()) {
					if (!Constants.SYS_TICKET_SIGN_1.equals(info.getTwo_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getTwo_seat())) {
						try {
							if (Integer.parseInt(info.getTwo_seat()) >= userInfoList.size()) {
								returninfo = info;
								isAssign = true;
								setUserSest(userInfoList, Constants.TWO_SEAT);
								logger.debug("动车优先车次为:" + info.getTrainCode());
								break;
							}
						} catch (NumberFormatException ex) {
							returninfo = info;
							isAssign = true;
							setUserSest(userInfoList, Constants.TWO_SEAT);
							logger.debug("动车优先车次为:" + info.getTrainCode());
							break;
						}
					}
				}
				// 勾选卧铺优先
				if (mainWin.isHardSleePer()) {
					if (!Constants.SYS_TICKET_SIGN_1.equals(info.getHard_sleeper()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getHard_sleeper())) {
						try {
							if (Integer.parseInt(info.getHard_sleeper()) >= userInfoList.size()) {
								returninfo = info;
								isAssign = true;
								setUserSest(userInfoList, Constants.HARD_SLEEPER);
								logger.debug("卧铺优先车次为:" + info.getTrainCode());
								break;
							}
						} catch (NumberFormatException ex) {
							returninfo = info;
							isAssign = true;
							setUserSest(userInfoList, Constants.HARD_SLEEPER);
							logger.debug("卧铺优先车次为:" + info.getTrainCode());
							break;
						}
					}
				}
				returninfo = getSeattrainQueryInfo(info);
				isAssign = true;
			}
			return returninfo;
		}

		// 未指定车次
		if (!isAssign) {
			for (Map.Entry<String, TrainQueryInfo> map : specificSeatTrains.entrySet()) {
				TrainQueryInfo info = getSeattrainQueryInfo(map.getValue());
				if (info != null) {
					returninfo = info;
					logger.debug("车次为:" + info.getTrainNo());
					return returninfo;
				}
			}
		}
		return null;
	}

	/**
	 * 席别选择 (二等座 -> 一等座 -> 硬卧 -> 软卧 -> 软座 -> 硬座-> 高级软卧 -> 特等座 -> 商务座 -> 无座 )
	 * 
	 * @param trainQueryInfo
	 * @return TrainQueryInfo
	 */
	public TrainQueryInfo getSeattrainQueryInfo(TrainQueryInfo info) {
		String seat = "";
		// 指定了座位席别
		if (specificTrainSeat.length > 0&&!StringUtil.isEmptyString(specificTrainSeat[0])) {
			for (int i = 0; i < specificTrainSeat.length; i++) {
				seat = specificTrainSeat[i];
				try {
					if (Integer.parseInt(info.getTwo_seat()) >= userInfoList.size()) {
						setUserSest(userInfoList, specificTrainSeat[i]);
						return info;
					}
				} catch (NumberFormatException ex) {
					setUserSest(userInfoList, specificTrainSeat[i]);
					return info;
				}
			}
			mainWin.messageOut.setText(mainWin.messageOut.getText() + "您指定的作为席别为:" + seatMap.get(seat) + "\n");
		}

		// 勾选动车优先
		if (mainWin.isBoxkTwoSeat()) {
			if (!Constants.SYS_TICKET_SIGN_1.equals(info.getTwo_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getTwo_seat())) {
				try {
					if (Integer.parseInt(info.getTwo_seat()) >= userInfoList.size()) {
						seat = Constants.TWO_SEAT;
						setUserSest(userInfoList, Constants.TWO_SEAT);
						logger.debug("动车优先车次为:" + info.getTrainCode());
						mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
						return info;
					}
				} catch (NumberFormatException ex) {
					seat = Constants.TWO_SEAT;
					setUserSest(userInfoList, Constants.TWO_SEAT);
					logger.debug("动车优先车次为:" + info.getTrainCode());
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			}
		}

		// 勾选卧铺优先
		if (mainWin.isHardSleePer()) {
			if (!Constants.SYS_TICKET_SIGN_1.equals(info.getHard_sleeper()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getHard_sleeper())) {
				try {
					if (Integer.parseInt(info.getHard_sleeper()) >= userInfoList.size()) {
						seat = Constants.HARD_SLEEPER;
						setUserSest(userInfoList, Constants.HARD_SLEEPER);
						logger.debug("卧铺优先车次为:" + info.getTrainCode());
						mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
						return info;
					}
				} catch (NumberFormatException ex) {
					seat = Constants.HARD_SLEEPER;
					setUserSest(userInfoList, Constants.HARD_SLEEPER);
					logger.debug("卧铺优先车次为:" + info.getTrainCode());
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			}
		}

		// 未指定作为席别 则按照座位顺序席别选取座位席别
		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getTwo_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getTwo_seat())) {
			try {
				if (Integer.parseInt(info.getTwo_seat()) >= userInfoList.size()) {
					seat = Constants.TWO_SEAT;
					setUserSest(userInfoList, Constants.TWO_SEAT);
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			} catch (NumberFormatException ex) {
				seat = Constants.TWO_SEAT;
				setUserSest(userInfoList, Constants.TWO_SEAT);
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getOne_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getOne_seat())) {
			try {
				if (Integer.parseInt(info.getOne_seat()) >= userInfoList.size()) {
					seat = Constants.ONE_SEAT;
					setUserSest(userInfoList, Constants.ONE_SEAT);
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			} catch (NumberFormatException ex) {
				seat = Constants.ONE_SEAT;
				setUserSest(userInfoList, Constants.ONE_SEAT);
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getHard_sleeper()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getHard_sleeper())) {
			try {
				if (Integer.parseInt(info.getHard_sleeper()) >= userInfoList.size()) {
					seat = Constants.HARD_SLEEPER;
					setUserSest(userInfoList, Constants.HARD_SLEEPER);
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			} catch (NumberFormatException ex) {
				seat = Constants.HARD_SLEEPER;
				setUserSest(userInfoList, Constants.HARD_SLEEPER);
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getSoft_sleeper()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getSoft_sleeper())) {
			try {
				if (Integer.parseInt(info.getSoft_sleeper()) >= userInfoList.size()) {
					seat = Constants.SOFT_SLEEPER;
					setUserSest(userInfoList, Constants.SOFT_SLEEPER);
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			} catch (NumberFormatException ex) {
				seat = Constants.SOFT_SLEEPER;
				setUserSest(userInfoList, Constants.SOFT_SLEEPER);
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getSoft_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getSoft_seat())) {
			try {
				if (Integer.parseInt(info.getSoft_seat()) >= userInfoList.size()) {
					seat = Constants.SOFT_SEAT;
					setUserSest(userInfoList, Constants.SOFT_SEAT);
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			} catch (NumberFormatException ex) {
				seat = Constants.SOFT_SEAT;
				setUserSest(userInfoList, Constants.SOFT_SEAT);
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getHard_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getHard_seat())) {
			try {
				if (Integer.parseInt(info.getHard_seat()) >= userInfoList.size()) {
					seat = Constants.HARD_SEAT;
					setUserSest(userInfoList, Constants.HARD_SEAT);
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			} catch (NumberFormatException ex) {
				seat = Constants.HARD_SEAT;
				setUserSest(userInfoList, Constants.HARD_SEAT);
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getVag_sleeper()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getVag_sleeper())) {
			try {
				if (Integer.parseInt(info.getVag_sleeper()) >= userInfoList.size()) {
					seat = Constants.VAG_SLEEPER;
					setUserSest(userInfoList, Constants.VAG_SLEEPER);
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			} catch (NumberFormatException ex) {
				seat = Constants.VAG_SLEEPER;
				setUserSest(userInfoList, Constants.VAG_SLEEPER);
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getBest_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getBest_seat())) {
			try {
				if (Integer.parseInt(info.getBest_seat()) >= userInfoList.size()) {
					seat = Constants.BEST_SEAT;
					setUserSest(userInfoList, Constants.BEST_SEAT);
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			} catch (NumberFormatException ex) {
				seat = Constants.BEST_SEAT;
				setUserSest(userInfoList, Constants.BEST_SEAT);
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getBuss_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getBuss_seat())) {
			try {
				if (Integer.parseInt(info.getBuss_seat()) >= userInfoList.size()) {
					seat = Constants.BUSS_SEAT;
					setUserSest(userInfoList, Constants.BUSS_SEAT);
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			} catch (NumberFormatException ex) {
				seat = Constants.BUSS_SEAT;
				setUserSest(userInfoList, Constants.BUSS_SEAT);
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
				return info;
			}
		}

		if (!Constants.SYS_TICKET_SIGN_1.equals(info.getNone_seat()) && !Constants.SYS_TICKET_SIGN_2.equals(info.getNone_seat())) {
			try {
				if (Integer.parseInt(info.getNone_seat()) >= userInfoList.size()) {
					seat = Constants.NONE_SEAT;
					setUserSest(userInfoList, Constants.NONE_SEAT);
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
					return info;
				}
			} catch (NumberFormatException ex) {
				seat = Constants.NONE_SEAT;
				setUserSest(userInfoList, Constants.NONE_SEAT);
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "自动选择的席别为:" + seatMap.get(seat) + "\n");
				return info;
			}
		}
		return null;
	}

	/**
	 * 获取指定的列车
	 * 
	 * @return
	 */
	private String[] getKeys() {
		return ResManager.getByKey(Constants.SYS_TRAINCODE).split(",");
	}

	/**
	 * 获取指定的座位席别
	 * 
	 * @return
	 */
	private String[] getSeatKeys() {
		return ResManager.getByKey(Constants.SYS_USERSEAT).split(",");
	}

	/**
	 * 席别选择
	 * 
	 * @param userInfoList
	 * @param seat
	 */
	private void setUserSest(List<UserInfo> userInfoList, String seat) {
		for (UserInfo info : userInfoList) {
			info.setSeatType(seat);
		}
	}
}
