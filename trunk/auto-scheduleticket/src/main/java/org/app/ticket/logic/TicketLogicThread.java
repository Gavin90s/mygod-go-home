package org.app.ticket.logic;

import org.app.ticket.core.MainWin;

/**
 * 订票逻辑线程
 * 
 * @Title: TicketLogicThread.java
 * @Description: org.app.ticket.logic
 * @Package org.app.ticket.logic
 * @author hncdyj123@163.com
 * @date 2012-9-29
 * @version V1.0
 * 
 */
public class TicketLogicThread extends Thread {

	private MainWin mainWin;
	private AutoGetTrainInfo autoGetTrainInfo;

	public TicketLogicThread() {

	}

	public TicketLogicThread(MainWin mainWin, AutoGetTrainInfo autoGetTrainInfo) {
		this.mainWin = mainWin;
		this.autoGetTrainInfo = autoGetTrainInfo;
	}

	@Override
	public void run() {
		super.run();
	}

}
