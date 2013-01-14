package org.app.ticket.logic;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.app.ticket.autoimg.OCR;
import org.app.ticket.bean.LoginDomain;
import org.app.ticket.constants.Constants;
import org.app.ticket.core.ClientCore;
import org.app.ticket.core.MainWin;
import org.app.ticket.util.StringUtil;
import org.app.ticket.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Title: LoginThread.java
 * @Description: org.app.ticket.core
 * @Package org.app.ticket.core
 * @author hncdyj123@163.com
 * @date 2012-11-7
 * @version V1.0
 * 
 */
public class LoginThread extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(LoginThread.class);

	private MainWin mainWin;

	private boolean islogin = false;

	private String tessPath;

	public LoginThread() {

	}

	public LoginThread(MainWin mainWin, String tessPath) {
		this.mainWin = mainWin;
		this.tessPath = tessPath;
	}

	@Override
	public void run() {
		try {
			int sum = 0;
			LoginDomain login = null;
			if (!mainWin.loginAuto.isSelected()) {
				String loginRand = getLoginRand();
				while (StringUtil.isEmptyString(loginRand)) {
					loginRand = getLoginRand();
				}
				login = new LoginDomain(loginRand, mainWin.username.getText(), mainWin.authcode.getText(), "Y", "N", mainWin.password.getText());
				String loginStr = ClientCore.Login(login);
				if (loginStr.contains("欢迎您登录中国铁路客户服务中心网站")) {
					islogin = true;
					mainWin.isLogin = true;
					mainWin.showMsg("登录成功！");
					// 启动cookie保持线程
					new KeepCookieThread().start();
				} else {
					mainWin.showMsg("登录失败,请仔细检查验证码！");
					// 验证码错误 刷新验证码
					mainWin.initLoginImage();
				}
			} else {
				// 启动未获取到cookie的情况
				if (StringUtil.isEmptyString(Constants.JSESSIONID_VALUE) && StringUtil.isEmptyString(Constants.BIGIPSERVEROTSWEB_VALUE)) {
					ClientCore.getCookie();
				}
				while (!islogin) {
					String url = Constants.GET_LOGINURL_PASSCODE + "&";
					double f = 0.0000000000000001f;
					Random random = new Random();
					f = random.nextDouble();
					url += f;
					ClientCore.getPassCode(url, mainWin.loginUrl);

					String loginRand = getLoginRand();

					if (loginRand == null) {
						continue;
					}

					logger.debug("-----------loginRand=" + loginRand);
					// ToolUtil.filterImage(mainWin.loginUrl);
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
					if (loginStr.contains("欢迎您登录中国铁路客户服务中心网站")) {
						islogin = true;
						mainWin.isLogin = true;
						break;
					}

					logger.debug("第" + sum + "次登录失败！");
					mainWin.messageOut.setText(mainWin.messageOut.getText() + "第" + sum + "次登录失败！\n");
				}
				logger.debug("在第" + sum + "次终于登录成功了！");
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "在第" + sum + "次终于登录成功了！\n");
				mainWin.showMsg("登录成功！");
				// 启动cookie保持线程
				new KeepCookieThread().start();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
