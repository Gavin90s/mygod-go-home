package org.app.ticket.bean;

/**
 * 查询火车实体类
 * 
 * @Title: OrderRequest.java
 * @Description: org.app.ticket.bean
 * @Package org.app.ticket.bean
 * @author hncdyj123@163.com
 * @date 2012-9-26
 * @version V1.0
 * 
 */
public class OrderRequest {
	// 是否包含学生票
	private String includeStudent = "00";
	// 始发站查询码
	private String from_station_telecode;
	// 查询时间区间段
	private String start_time_str;
	// 到站查询码

	private String to_station_telecode;
	// 出发日期
	private String train_date;
	// 查询日期
	private String query_date;
	// 火车编号
	private String train_no;
	// 查询类型
	private String trainClass = "QB#D#Z#T#K#QT#";
	// 查询码
	private String trainPassType = "QB";

	private String seatTypeAndNum = "";

	private String from;

	private String to;

	public String getIncludeStudent() {
		return includeStudent;
	}

	public void setIncludeStudent(String includeStudent) {
		this.includeStudent = includeStudent;
	}

	public String getFrom_station_telecode() {
		return from_station_telecode;
	}

	public void setFrom_station_telecode(String from_station_telecode) {
		this.from_station_telecode = from_station_telecode;
	}

	public String getStart_time_str() {
		return start_time_str;
	}

	public void setStart_time_str(String start_time_str) {
		this.start_time_str = start_time_str;
	}

	public String getTo_station_telecode() {
		return to_station_telecode;
	}

	public void setTo_station_telecode(String to_station_telecode) {
		this.to_station_telecode = to_station_telecode;
	}

	public String getTrain_date() {
		return train_date;
	}

	public void setTrain_date(String train_date) {
		this.train_date = train_date;
	}

	public String getQuery_date() {
		return query_date;
	}

	public void setQuery_date(String query_date) {
		this.query_date = query_date;
	}

	public String getTrain_no() {
		return train_no;
	}

	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}

	public String getTrainClass() {
		return trainClass;
	}

	public void setTrainClass(String trainClass) {
		this.trainClass = trainClass;
	}

	public String getTrainPassType() {
		return trainPassType;
	}

	public void setTrainPassType(String trainPassType) {
		this.trainPassType = trainPassType;
	}

	public String getSeatTypeAndNum() {
		return seatTypeAndNum;
	}

	public void setSeatTypeAndNum(String seatTypeAndNum) {
		this.seatTypeAndNum = seatTypeAndNum;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
