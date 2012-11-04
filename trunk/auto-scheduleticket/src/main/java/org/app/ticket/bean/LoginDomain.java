package org.app.ticket.bean;

/**
 * 登录实体类
 * 
 * @Title: LoginDomain.java
 * @Description: org.app.ticket.bean
 * @Package org.app.ticket.bean
 * @author hncdyj123@163.com
 * @date 2012-10-23
 * @version V1.0
 * 
 */
public class LoginDomain {
	private String loginRand;
	private String user_name;
	private String nameErrorFocus;
	private String passwordErrorFocus;
	private String randCode;
	private String randErrorFocus;
	private String refundFlag;
	private String refundLogin;
	private String password;

	public LoginDomain() {

	}

	public LoginDomain(String loginRand, String user_name, String randCode, String refundFlag, String refundLogin, String password) {
		super();
		this.loginRand = loginRand;
		this.user_name = user_name;
		this.randCode = randCode;
		this.refundFlag = refundFlag;
		this.refundLogin = refundLogin;
		this.password = password;
	}

	public LoginDomain(String loginRand, String user_name, String randCode, String randErrorFocus, String refundFlag, String refundLogin, String password) {
		super();
		this.loginRand = loginRand;
		this.user_name = user_name;
		this.randCode = randCode;
		this.randErrorFocus = randErrorFocus;
		this.refundFlag = refundFlag;
		this.refundLogin = refundLogin;
		this.password = password;
	}

	public String getLoginRand() {
		return loginRand;
	}

	public void setLoginRand(String loginRand) {
		this.loginRand = loginRand;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getNameErrorFocus() {
		return nameErrorFocus;
	}

	public void setNameErrorFocus(String nameErrorFocus) {
		this.nameErrorFocus = nameErrorFocus;
	}

	public String getPasswordErrorFocus() {
		return passwordErrorFocus;
	}

	public void setPasswordErrorFocus(String passwordErrorFocus) {
		this.passwordErrorFocus = passwordErrorFocus;
	}

	public String getRandCode() {
		return randCode;
	}

	public void setRandCode(String randCode) {
		this.randCode = randCode;
	}

	public String getRandErrorFocus() {
		return randErrorFocus;
	}

	public void setRandErrorFocus(String randErrorFocus) {
		this.randErrorFocus = randErrorFocus;
	}

	public String getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}

	public String getRefundLogin() {
		return refundLogin;
	}

	public void setRefundLogin(String refundLogin) {
		this.refundLogin = refundLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
