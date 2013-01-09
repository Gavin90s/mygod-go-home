package org.app.ticket.bean;

/**
 * 列车信息实体类
 * 
 * @Title: TrainQueryInfo.java
 * @Description: org.app.ticket.bean
 * @Package org.app.ticket.bean
 * @author hncdyj123@163.com
 * @date 2012-9-26
 * @version V1.0
 * 
 */
public class TrainQueryInfo {

	private String trainCode;// 序号
	private String trainNo; // 车次
	private String trainDate; // 出发日期
	private String fromStation;// 发站
	private String fromStationName; // 发站中心火车站
	private String fromStationCode; // 发站code
	private String startTime;// 发时
	private String toStation;// 到站
	private String toStationName;// 到站中心火车站
	private String toStationCode;// 到站code
	private String endTime; // 到时
	private String locationCode; // 位置code
	private String takeTime;// 历时
	private String formStationNo; // 发站编号
	private String toStationNo; // 到站编号

	private String buss_seat; // 商务座
	private String best_seat;// 特等座(余票)
	private String one_seat;// 一等座(余票)
	private String two_seat;// 二等座(余票)
	private String vag_sleeper;// 高级软卧(余票)
	private String soft_sleeper;// 软卧(余票)
	private String hard_sleeper;// 硬卧(余票)
	private String soft_seat;// 软座(余票)
	private String hard_seat;// 硬座(余票)
	private String none_seat;// 无座(余票)
	private String other_seat;// 其他

	private String mmStr;// mmString
	private String trainno4;// trainno4
	private String ypInfoDetail;// ypInfoDetail
	private String single_round_type = "1"; // single_round_type

	public String getTrainCode() {
		return trainCode;
	}

	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getTrainDate() {
		return trainDate;
	}

	public void setTrainDate(String trainDate) {
		this.trainDate = trainDate;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getFromStationCode() {
		return fromStationCode;
	}

	public void setFromStationCode(String fromStationCode) {
		this.fromStationCode = fromStationCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public String getToStationCode() {
		return toStationCode;
	}

	public void setToStationCode(String toStationCode) {
		this.toStationCode = toStationCode;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}

	public String getFormStationNo() {
		return formStationNo;
	}

	public void setFormStationNo(String formStationNo) {
		this.formStationNo = formStationNo;
	}

	public String getToStationNo() {
		return toStationNo;
	}

	public void setToStationNo(String toStationNo) {
		this.toStationNo = toStationNo;
	}

	public String getBuss_seat() {
		return buss_seat;
	}

	public void setBuss_seat(String buss_seat) {
		this.buss_seat = buss_seat;
	}

	public String getBest_seat() {
		return best_seat;
	}

	public void setBest_seat(String best_seat) {
		this.best_seat = best_seat;
	}

	public String getOne_seat() {
		return one_seat;
	}

	public void setOne_seat(String one_seat) {
		this.one_seat = one_seat;
	}

	public String getTwo_seat() {
		return two_seat;
	}

	public void setTwo_seat(String two_seat) {
		this.two_seat = two_seat;
	}

	public String getVag_sleeper() {
		return vag_sleeper;
	}

	public void setVag_sleeper(String vag_sleeper) {
		this.vag_sleeper = vag_sleeper;
	}

	public String getSoft_sleeper() {
		return soft_sleeper;
	}

	public void setSoft_sleeper(String soft_sleeper) {
		this.soft_sleeper = soft_sleeper;
	}

	public String getHard_sleeper() {
		return hard_sleeper;
	}

	public void setHard_sleeper(String hard_sleeper) {
		this.hard_sleeper = hard_sleeper;
	}

	public String getSoft_seat() {
		return soft_seat;
	}

	public void setSoft_seat(String soft_seat) {
		this.soft_seat = soft_seat;
	}

	public String getHard_seat() {
		return hard_seat;
	}

	public void setHard_seat(String hard_seat) {
		this.hard_seat = hard_seat;
	}

	public String getNone_seat() {
		return none_seat;
	}

	public void setNone_seat(String none_seat) {
		this.none_seat = none_seat;
	}

	public String getOther_seat() {
		return other_seat;
	}

	public void setOther_seat(String other_seat) {
		this.other_seat = other_seat;
	}

	public String getMmStr() {
		return mmStr;
	}

	public void setMmStr(String mmStr) {
		this.mmStr = mmStr;
	}

	public String getTrainno4() {
		return trainno4;
	}

	public void setTrainno4(String trainno4) {
		this.trainno4 = trainno4;
	}

	public String getYpInfoDetail() {
		return ypInfoDetail;
	}

	public void setYpInfoDetail(String ypInfoDetail) {
		this.ypInfoDetail = ypInfoDetail;
	}

	public String getSingle_round_type() {
		return single_round_type;
	}

	public void setSingle_round_type(String single_round_type) {
		this.single_round_type = single_round_type;
	}

	public String getFromStationName() {
		return fromStationName;
	}

	public void setFromStationName(String fromStationName) {
		this.fromStationName = fromStationName;
	}

	public String getToStationName() {
		return toStationName;
	}

	public void setToStationName(String toStationName) {
		this.toStationName = toStationName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrainQueryInfo [trainCode=");
		builder.append(trainCode);
		builder.append(", trainNo=");
		builder.append(trainNo);
		builder.append(", trainDate=");
		builder.append(trainDate);
		builder.append(", fromStation=");
		builder.append(fromStation);
		builder.append(", fromStationCode=");
		builder.append(fromStationCode);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", toStation=");
		builder.append(toStation);
		builder.append(", toStationCode=");
		builder.append(toStationCode);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", takeTime=");
		builder.append(takeTime);
		builder.append(", formStationNo=");
		builder.append(formStationNo);
		builder.append(", toStationNo=");
		builder.append(toStationNo);
		builder.append(", buss_seat=");
		builder.append(buss_seat);
		builder.append(", best_seat=");
		builder.append(best_seat);
		builder.append(", one_seat=");
		builder.append(one_seat);
		builder.append(", two_seat=");
		builder.append(two_seat);
		builder.append(", vag_sleeper=");
		builder.append(vag_sleeper);
		builder.append(", soft_sleeper=");
		builder.append(soft_sleeper);
		builder.append(", hard_sleeper=");
		builder.append(hard_sleeper);
		builder.append(", soft_seat=");
		builder.append(soft_seat);
		builder.append(", hard_seat=");
		builder.append(hard_seat);
		builder.append(", none_seat=");
		builder.append(none_seat);
		builder.append(", mmStr=");
		builder.append(mmStr);
		builder.append(", trainno4=");
		builder.append(trainno4);
		builder.append(", ypInfoDetail=");
		builder.append(ypInfoDetail);
		builder.append(", single_round_type=");
		builder.append(single_round_type);
		builder.append("]");
		return builder.toString();
	}

}
