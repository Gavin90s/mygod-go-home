package org.app.ticket.test;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.app.ticket.constants.Constants;
import org.app.ticket.core.ClientCore;

public class ImageTest {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException {
		Constants.BIGIPSERVEROTSWEB_VALUE = "2631139594.62495.0000";
		Constants.JSESSIONID_VALUE = "E326226420AA495EB8A51924FA4C66CF";
		// 第四步 获取提交订单信息时候获取验证码
		ClientCore.getPassCode(Constants.GET_SUBMITURL_PASSCODE, System.getProperty("user.dir") + "\\image\\" + "passcode-submit.jpg");
	}

}
