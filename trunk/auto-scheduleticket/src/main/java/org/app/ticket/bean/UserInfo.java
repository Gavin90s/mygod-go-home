/**************************************************
 * Filename: UserInfo.java
 * Version: v1.0
 * CreatedDate: 2011-11-27
 * Copyright (C) 2011 By cafebabe.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 *
 * If you would like to negotiate alternate licensing terms, you may do
 * so by contacting the author: talentyao@foxmail.com
 ***************************************************/

package org.app.ticket.bean;

import java.io.Serializable;

/**
 * 
 * @Title: UserInfo.java
 * @Description: org.app.ticket.bean
 * @Package org.app.ticket.bean
 * @author hncdyj123@163.com
 * @date 2012-9-29
 * @version V1.0
 * 
 */
public class UserInfo implements Serializable {
	/** 字段注释 */
	private static final long serialVersionUID = 1L;

	private String cardID;
	private String name;
	private String phone;

	private String seatType = "1"; // 座位类型
	private String tickType = "1"; // 车票类型
	private String cardType = "1"; // 证件类型
	private String idMode = "Y";

	public UserInfo() {
	}

	public UserInfo(String cardID, String name, String phone) {
		this.cardID = cardID;
		this.name = name;
		this.phone = phone;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public String getTickType() {
		return tickType;
	}

	public void setTickType(String tickType) {
		this.tickType = tickType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIdMode() {
		return idMode;
	}

	public void setIdMode(String idMode) {
		this.idMode = idMode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getText() {
		StringBuilder builder = new StringBuilder();
		builder.append(seatType).append(",").append("0,").append(tickType).append(",").append(getSimpleText()).append(",").append(phone).append(",").append(idMode);
		return builder.toString();
	}

	public String getSimpleText() {
		StringBuilder builder = new StringBuilder();
		builder.append(name).append(",").append(cardType).append(",").append(cardID);
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfo [ID=").append(cardID).append(", name=").append(name).append(", phone=").append(phone).append(", rangDate=").append(", startDate=").append(", seatType=")
		        .append(seatType).append(", tickType=").append(tickType).append(", cardType=").append(cardType).append(", idMode=").append(idMode).append("]");
		return builder.toString();
	}

}
