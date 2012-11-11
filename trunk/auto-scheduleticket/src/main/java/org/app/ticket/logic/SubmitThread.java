package org.app.ticket.logic;

import java.io.File;
import java.util.List;

import org.app.ticket.autoimg.OCR;
import org.app.ticket.bean.OrderRequest;
import org.app.ticket.bean.TrainQueryInfo;
import org.app.ticket.bean.UserInfo;
import org.app.ticket.constants.Constants;
import org.app.ticket.core.ClientCore;
import org.app.ticket.core.MainWin;

/**
 * 提交订票线程
 * 
 * @Title: SubmitThread.java
 * @Description: org.app.ticket.logic
 * @Package org.app.ticket.logic
 * @author hncdyj123@163.com
 * @date 2012-11-7
 * @version V1.0
 * 
 */
public class SubmitThread extends Thread {

	private List<TrainQueryInfo> trainQueryInfoList;

	private List<UserInfo> userInfos;

	private OrderRequest req;

	private MainWin mainWin;

	private String tessPath;

	public static boolean isSuccess = false;

	public SubmitThread() {

	}

	public SubmitThread(String tessPath, List<TrainQueryInfo> trainQueryInfoList, List<UserInfo> userInfos, OrderRequest req, MainWin mainWin) {
		this.tessPath = tessPath;
		this.trainQueryInfoList = trainQueryInfoList;
		this.userInfos = userInfos;
		this.req = req;
		this.mainWin = mainWin;
	}

	@Override
	public void run() {
		String msg = "";
		try {
			while (!isSuccess) {
				if (!msg.contains("Y")) {
					// 第四步 获取提交订单信息时候获取验证码
					ClientCore.getPassCode(Constants.GET_SUBMITURL_PASSCODE, System.getProperty("user.dir") + "\\image\\" + "passcode-submit.jpg");
					// 识别验证码
					String valCode = new OCR().recognizeText(tessPath, new File("D:\\Workspace\\auto-scheduleticket\\image\\passcode-submit.jpg"), "jpg");
					valCode = valCode.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "");
					System.out.println("-------------valCode = " + valCode);
					msg = ClientCore.confirmSingleForQueueOrder(trainQueryInfoList.get(1), req, userInfos, valCode);
					System.out.println("最后输出消息:" + valCode + "----------" + msg);
					sleep(5000);
				} else {
					isSuccess = true;
					mainWin.showMsg("订票成功！");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
