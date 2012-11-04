package org.app.ticket.core;

import java.io.File;
import java.util.List;

import org.app.ticket.autoimg.OCR;
import org.app.ticket.bean.OrderRequest;
import org.app.ticket.bean.TrainQueryInfo;
import org.app.ticket.bean.UserInfo;
import org.app.ticket.constants.Constants;
import org.app.ticket.util.StringUtil;

public class SubmitThread extends Thread {

	private List<TrainQueryInfo> trainQueryInfoList;

	private List<UserInfo> userInfos;

	private OrderRequest req;

	public static boolean isSuccess = false;

	public SubmitThread() {

	}

	public SubmitThread(List<TrainQueryInfo> trainQueryInfoList, List<UserInfo> userInfos, OrderRequest req) {
		this.trainQueryInfoList = trainQueryInfoList;
		this.userInfos = userInfos;
		this.req = req;
	}

	@Override
	public void run() {
		String msg = "";
		try {
			while (!isSuccess) {
				if (StringUtil.isEmptyString(msg) || msg.contains("输入的验证码不正确！")) {
					// 第四步 获取提交订单信息时候获取验证码
					ClientCore.getPassCode(Constants.GET_SUBMITURL_PASSCODE, System.getProperty("user.dir") + "\\image\\" + "passcode-submit.jpg");
					// 识别验证码
					String valCode = new OCR().recognizeText(new File("D:\\Workspace\\auto-scheduleticket\\image\\passcode-submit.jpg"), "jpg");
					valCode = valCode.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "");
					System.out.println("-------------valCode = " + valCode);
					msg = ClientCore.confirmSingleForQueueOrder(trainQueryInfoList.get(1), req, userInfos, valCode);
					System.out.println("最后输出消息:" + valCode + "----------" + msg);
					sleep(5000);
				} else {
					isSuccess = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
