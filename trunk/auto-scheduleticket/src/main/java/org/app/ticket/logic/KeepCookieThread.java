package org.app.ticket.logic;

import org.app.ticket.core.ClientCore;
import org.app.ticket.msg.ResManager;
import org.app.ticket.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Title: KeepCookieThread.java
 * @Description: 保持cookie线程
 * @Package org.app.ticket.logic
 * @author hncdyj123@163.com
 * @date 2013-1-9
 * @version V1.0
 * 
 */
public class KeepCookieThread extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(KeepCookieThread.class);

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			while (true) {
				logger.debug("cookie Every time start time interval for " + Integer.parseInt(StringUtil.isEmptyString(ResManager.getByKey("keepcookietime")) ? "20" : ResManager.getByKey("keepcookietime")) + "min");
				this.sleep(1000 * 60 * Integer.parseInt(StringUtil.isEmptyString(ResManager.getByKey("sleeptime")) ? "20" : ResManager.getByKey("keepcookietime")));
				ClientCore.getCookie();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
