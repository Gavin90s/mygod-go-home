package org.app.ticket.logic;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

import org.app.ticket.autoimg.OCR;
import org.app.ticket.bean.LoginDomain;
import org.app.ticket.bean.OrderRequest;
import org.app.ticket.bean.TrainQueryInfo;
import org.app.ticket.bean.UserInfo;
import org.app.ticket.constants.Constants;
import org.app.ticket.core.ClientCore;
import org.app.ticket.core.MainWin;
import org.app.ticket.msg.ResManager;
import org.app.ticket.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger logger = LoggerFactory.getLogger(SubmitThread.class);

	private MainWin mainWin;

	private boolean islogin = false;

	private String tessPath;

	private List<TrainQueryInfo> trainQueryInfoList;

	private List<UserInfo> userInfos;

	private OrderRequest req;

	private TrainQueryInfo trainQueryInfo;

	private static boolean isSuccess = false;

	private int sum = 0;

	public SubmitThread() {

	}

	public SubmitThread(List<UserInfo> userInfos, OrderRequest req, MainWin mainWin) {
		this.userInfos = userInfos;
		this.req = req;
		this.mainWin = mainWin;
	}

	@Override
	public void run() {
		while (!isSuccess) {
			mainWin.getStartButton().setText(ResManager.getString("RobotTicket.btn.stop"));
			try {
				int sum = 0;
				LoginDomain login = null;
				if (!mainWin.loginAuto.isSelected()) {
					String loginRand = getLoginRand();
					login = new LoginDomain(loginRand, mainWin.username.getText(), mainWin.authcode.getText(), "Y", "N", mainWin.password.getText());
					String loginStr = ClientCore.Login(login);
					if (loginStr.contains("您最后一次登录时间为")) {
						islogin = true;
						mainWin.isLogin = true;
						mainWin.showMsg("登录成功！");
					} else {
						mainWin.showMsg("登录失败,请仔细坚持验证码！");
					}
				} else {
					ClientCore.getCookie();
					while (!islogin) {
						String url = Constants.GET_LOGINURL_PASSCODE + "&";
						double f = 0.0000000000000001f;
						Random random = new Random();
						f = random.nextDouble();
						url += f;
						System.out.println("url = " + url);
						ClientCore.getPassCode(url, mainWin.loginUrl);

						String loginRand = getLoginRand();

						logger.debug("-----------loginRand=" + loginRand);

						// 设置背景图片
						mainWin.code.setIcon(ToolUtil.getImageIcon(mainWin.loginUrl));

						// 识别验证码
						String valCode = new OCR().recognizeText(tessPath, new File(mainWin.loginUrl), "jpg");
						valCode = valCode.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "");

						logger.debug("-------------valCode" + valCode);

						// 设置识别到的验证码
						mainWin.authcode.setText(valCode);

						logger.debug("userName = " + mainWin.username.getText() + "|password = " + mainWin.password.getText());

						if (sum == 0) {
							login = new LoginDomain(loginRand, mainWin.username.getText(), valCode, "Y", "N", mainWin.password.getText());
						} else {
							login = new LoginDomain(loginRand, mainWin.username.getText(), valCode, "focus", "Y", "N", mainWin.password.getText());
						}

						++sum;
						String loginStr = ClientCore.Login(login);
						if (loginStr.contains("您最后一次登录时间为")) {
							islogin = true;
							mainWin.isLogin = true;
							break;
						}

						logger.debug("第" + sum + "次登录失败！");
						mainWin.messageOut.setText(mainWin.messageOut.getText() + "第" + sum + "次登录失败！\n");
					}
				}
				logger.debug("在第" + sum + "次终于登录成功了！");
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "在第" + sum + "次终于登录成功了！\n");
				mainWin.showMsg("登录成功！");

				while (true) {
					if (mainWin.isLogin) {
						// if (mainWin.isOnclick % 2 != 0) {
						// mainWin.getStartButton().setText(ResManager.getString("RobotTicket.btn.start"));
						// break;
						// }
						// 查询火车信息
						trainQueryInfoList = ClientCore.queryTrain(req);
						mainWin.messageOut.setText(mainWin.messageOut.getText() + "火车信息\n");
						if (trainQueryInfoList.size() > 0) {
							for (TrainQueryInfo t : trainQueryInfoList) {
								mainWin.messageOut.setText(mainWin.messageOut.getText() + t.toString() + "\n");
							}
						}
						// 获取火车信息
						trainQueryInfo = (new AutoGetTrainInfo(trainQueryInfoList, mainWin, userInfos)).getSeattrainQueryInfo();
						// 第二步 提交预定车次信息(获取重定向地址中的URL和LEFTTICKETSTR)
						ClientCore.submitOrderRequest(trainQueryInfo, req);

						// 第四步 获取提交订单信息时候获取验证码
						String path = mainWin.submitUrl;
						ClientCore.getPassCode(Constants.GET_SUBMITURL_PASSCODE, path);
						// 识别验证码
						String valCode = new OCR().recognizeText(tessPath, new File(path), "jpg");
						valCode = valCode.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "");
						System.out.println("-------------valCode = " + valCode);

						String msg = ClientCore.confirmSingleForQueueOrder(trainQueryInfo, req, userInfos, valCode, null);

						logger.debug("最后输出消息:" + valCode + "----------" + msg);
						if (msg.contains("验证码")) {
							mainWin.messageOut.setText(mainWin.messageOut.getText() + "验证码错误！\n");
						}
						if (msg.contains("Y")) {
							isSuccess = true;
							mainWin.showMsg("订票成功!");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String getLoginRand() throws KeyManagementException, NoSuchAlgorithmException {
		// 获取登录的loginRand
		String loginRand = ClientCore.loginAysnSuggest();
		if (loginRand.contains("loginRand")) {
			String[] l = loginRand.split(",");
			String[] t = l[0].split(":");
			loginRand = t[1].substring(1, t[1].length() - 1);
		}
		return loginRand;
	}
}
