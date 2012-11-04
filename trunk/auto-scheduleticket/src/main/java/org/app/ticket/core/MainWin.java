package org.app.ticket.core;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import org.app.ticket.bean.OrderRequest;
import org.app.ticket.bean.TrainQueryInfo;
import org.app.ticket.bean.UserInfo;
import org.app.ticket.constants.Constants;
import org.app.ticket.logic.AutoGetTrainInfo;
import org.app.ticket.msg.ResManager;
import org.app.ticket.util.StringUtil;
import org.app.ticket.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主窗口类
 * 
 * @Title: MainWin.java
 * @Description: org.app.ticket.core
 * @Package org.app.ticket.core
 * @author hncdyj123@163.com
 * @date 2012-9-27
 * @version V1.0
 * 
 */
public class MainWin {
	private static final Logger logger = LoggerFactory.getLogger(MainWin.class);
	private JFrame frame;

	/************** 登录相关 *********************/
	public JTextField username;
	public JPasswordField password;
	public JLabel code;
	private JButton loginBtn;
	public JTextField authcode;
	public JCheckBox loginAuto;

	/************** 导入session *****************/
	private JTextField jSession;
	private JTextField bigWeb;
	private JButton impSession;

	/************** 联系人 *****************/
	private JTextField linkman1_name;
	private JTextField linkman1_cardNo;
	private JTextField linkman1_mobile;

	private JTextField linkman2_name;
	private JTextField linkman2_cardNo;
	private JTextField linkman2_mobile;

	private JTextField linkman3_name;
	private JTextField linkman3_cardNo;
	private JTextField linkman3_mobile;

	/************** 配置相关 *****************/
	private JCheckBox boxkTwoSeat;
	private JCheckBox hardSleePer;
	private JCheckBox isAutoCode;
	private JTextField passCode;
	private JFormattedTextField txtStartDate;
	private JTextField formCode;
	private JTextField toCode;
	private JLabel passCodeImage;
	private JButton startButton;
	private JCheckBox allAuto;

	/************* 输出相关 ****************/
	public JTextArea messageOut;

	/************** 业务逻辑相关的变量 ****************/
	private List<TrainQueryInfo> trainQueryInfo;
	private AutoGetTrainInfo autoGetTrainInfo = null;

	// 当前jar包所在的路径
	private static String path;
	private MainWin mainWin = null;
	// 登录验证码路径
	public String url;

	// 静态构造块
	static {
		// 获取当前jar所在的路径
		path = System.getProperty("java.class.path");
		int firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1;
		int lastIndex = path.lastIndexOf(File.separator) + 1;
		path = path.substring(firstIndex, lastIndex);
	}

	/**
	 * 初始化界面
	 */
	public void initLayout() {
		frame = new JFrame(ResManager.getString("RobotTicket.main.msg"));
		ImageIcon ico = ResManager.createImageIcon("logo.jpg");
		frame.setIconImage(ico.getImage());
		frame.setBounds(50, 50, 670, 640);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		ToolTipManager.sharedInstance().setInitialDelay(0);
		frame.getContentPane().setLayout(null);

		/****************** 登录控件相关 **********************/
		JPanel panel_o = new JPanel();
		panel_o.setBounds(10, 12, 650, 54);
		frame.getContentPane().add(panel_o);
		panel_o.setLayout(null);
		panel_o.setBorder(new TitledBorder(ResManager.getString("RobotTicket.panel.userinfo")));

		JLabel label_o = new JLabel(ResManager.getString("RobotTicket.label.user_name"));
		label_o.setBounds(10, 26, 40, 15);
		panel_o.add(label_o);
		label_o.setHorizontalAlignment(SwingConstants.RIGHT);

		username = new JTextField();
		username.setToolTipText(ResManager.getString("RobotTicket.label.user_name"));
		username.setBounds(60, 23, 100, 21);
		panel_o.add(username);
		username.setColumns(10);

		JLabel label_o1 = new JLabel(ResManager.getString("RobotTicket.label.password"));
		label_o1.setBounds(170, 26, 40, 15);
		panel_o.add(label_o1);
		label_o1.setHorizontalAlignment(SwingConstants.RIGHT);

		password = new JPasswordField();
		password.setToolTipText(ResManager.getString("RobotTicket.label.password"));
		password.setBounds(220, 23, 100, 21);
		panel_o.add(password);
		password.setColumns(10);

		code = new JLabel();
		code.setBounds(340, 20, 60, 20);
		panel_o.add(code);
		code.setHorizontalAlignment(SwingConstants.RIGHT);

		authcode = new JTextField();
		authcode.setToolTipText(ResManager.getString("RobotTicket.label.codename"));
		authcode.setBounds(410, 23, 40, 21);
		panel_o.add(authcode);
		authcode.setColumns(10);

		loginAuto = new JCheckBox(ResManager.getString("RobotTicket.label.isAutoCode"));
		loginAuto.setBounds(430, 26, 110, 15);
		panel_o.add(loginAuto);
		loginAuto.setHorizontalAlignment(SwingConstants.RIGHT);

		loginBtn = new JButton();
		loginBtn.setText(ResManager.getString("RobotTicket.btn.login"));
		loginBtn.setBounds(560, 18, 65, 28);
		panel_o.add(loginBtn);
		loginBtn.addActionListener(new LoginBtn());

		/****************** session导入相关 **********************/
		JPanel panel = new JPanel();
		panel.setBounds(10, 70, 650, 54);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(ResManager.getString("RobotTicket.panel.session")));

		JLabel label = new JLabel(ResManager.getString("RobotTicket.label.jsession"));
		label.setBounds(10, 26, 60, 15);
		panel.add(label);
		label.setHorizontalAlignment(SwingConstants.RIGHT);

		jSession = new JTextField();
		jSession.setToolTipText(ResManager.getString("RobotTicket.label.jsession"));
		jSession.setBounds(78, 23, 200, 21);
		panel.add(jSession);
		jSession.setColumns(10);

		JLabel label_1 = new JLabel(ResManager.getString("RobotTicket.label.BIGipServerotsweb"));
		label_1.setBounds(278, 26, 120, 15);
		panel.add(label_1);
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);

		bigWeb = new JTextField();
		bigWeb.setToolTipText(ResManager.getString("RobotTicket.label.BIGipServerotsweb"));
		bigWeb.setBounds(408, 23, 130, 21);
		panel.add(bigWeb);
		bigWeb.setColumns(10);

		impSession = new JButton();
		impSession.setText(ResManager.getString("RobotTicket.btn.import"));
		impSession.setBounds(560, 18, 65, 28);
		panel.add(impSession);
		impSession.addActionListener(new ImpSession());

		/****************** 联系人相关 **********************/
		JPanel panel2 = new JPanel();
		panel2.setBounds(10, 140, 650, 210);
		frame.getContentPane().add(panel2);
		panel2.setLayout(null);
		panel2.setBorder(new TitledBorder(ResManager.getString("RobotTicket.panel.linkmaninfo")));

		JPanel panel3 = new JPanel();
		panel3.setBounds(20, 20, 610, 54);
		panel2.add(panel3);
		panel3.setLayout(null);
		panel3.setBorder(new TitledBorder(ResManager.getString("RobotTicket.panel.linkman1")));

		JLabel label_2 = new JLabel(ResManager.getString("RobotTicket.label.username"));
		label_2.setBounds(55, 26, 30, 15);
		panel3.add(label_2);
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman1_name = new JTextField();
		linkman1_name.setToolTipText(ResManager.getString("RobotTicket.label.username"));
		linkman1_name.setBounds(95, 23, 40, 21);
		panel3.add(linkman1_name);
		linkman1_name.setColumns(10);

		JLabel label_3 = new JLabel(ResManager.getString("RobotTicket.label.cardno"));
		label_3.setBounds(155, 26, 50, 15);
		panel3.add(label_3);
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman1_cardNo = new JTextField();
		linkman1_cardNo.setToolTipText(ResManager.getString("RobotTicket.label.cardno"));
		linkman1_cardNo.setBounds(215, 23, 150, 21);
		panel3.add(linkman1_cardNo);
		linkman1_cardNo.setColumns(10);

		JLabel label_4 = new JLabel(ResManager.getString("RobotTicket.label.mobilephone"));
		label_4.setBounds(375, 26, 40, 15);
		panel3.add(label_4);
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman1_mobile = new JTextField();
		linkman1_mobile.setToolTipText(ResManager.getString("RobotTicket.label.mobilephone"));
		linkman1_mobile.setBounds(425, 23, 100, 21);
		panel3.add(linkman1_mobile);
		linkman1_mobile.setColumns(10);

		JPanel panel4 = new JPanel();
		panel4.setBounds(20, 80, 610, 54);
		panel2.add(panel4);
		panel4.setLayout(null);
		panel4.setBorder(new TitledBorder(ResManager.getString("RobotTicket.panel.linkman2")));

		JLabel label_5 = new JLabel(ResManager.getString("RobotTicket.label.username"));
		label_5.setBounds(55, 26, 30, 15);
		panel4.add(label_5);
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman2_name = new JTextField();
		linkman2_name.setToolTipText(ResManager.getString("RobotTicket.label.username"));
		linkman2_name.setBounds(95, 23, 40, 21);
		panel4.add(linkman2_name);
		linkman2_name.setColumns(10);

		JLabel label_6 = new JLabel(ResManager.getString("RobotTicket.label.cardno"));
		label_6.setBounds(155, 26, 50, 15);
		panel4.add(label_6);
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman2_cardNo = new JTextField();
		linkman2_cardNo.setToolTipText(ResManager.getString("RobotTicket.label.cardno"));
		linkman2_cardNo.setBounds(215, 23, 150, 21);
		panel4.add(linkman2_cardNo);
		linkman2_cardNo.setColumns(10);

		JLabel label_7 = new JLabel(ResManager.getString("RobotTicket.label.mobilephone"));
		label_7.setBounds(375, 26, 40, 15);
		panel4.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman2_mobile = new JTextField();
		linkman2_mobile.setToolTipText(ResManager.getString("RobotTicket.label.mobilephone"));
		linkman2_mobile.setBounds(425, 23, 100, 21);
		panel4.add(linkman2_mobile);
		linkman2_mobile.setColumns(10);

		JPanel panel5 = new JPanel();
		panel5.setBounds(20, 140, 610, 54);
		panel2.add(panel5);
		panel5.setLayout(null);
		panel5.setBorder(new TitledBorder(ResManager.getString("RobotTicket.panel.linkman3")));

		JLabel label_8 = new JLabel(ResManager.getString("RobotTicket.label.username"));
		label_8.setBounds(55, 26, 30, 15);
		panel5.add(label_8);
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman3_name = new JTextField();
		linkman3_name.setToolTipText(ResManager.getString("RobotTicket.label.username"));
		linkman3_name.setBounds(95, 23, 40, 21);
		panel5.add(linkman3_name);
		linkman3_name.setColumns(10);

		JLabel label_9 = new JLabel(ResManager.getString("RobotTicket.label.cardno"));
		label_9.setBounds(155, 26, 50, 15);
		panel5.add(label_9);
		label_9.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman3_cardNo = new JTextField();
		linkman3_cardNo.setToolTipText(ResManager.getString("RobotTicket.label.cardno"));
		linkman3_cardNo.setBounds(215, 23, 150, 21);
		panel5.add(linkman3_cardNo);
		linkman3_cardNo.setColumns(10);

		JLabel label_10 = new JLabel(ResManager.getString("RobotTicket.label.mobilephone"));
		label_10.setBounds(375, 26, 40, 15);
		panel5.add(label_10);
		label_10.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman3_mobile = new JTextField();
		linkman3_mobile.setToolTipText(ResManager.getString("RobotTicket.label.mobilephone"));
		linkman3_mobile.setBounds(425, 23, 100, 21);
		panel5.add(linkman3_mobile);
		linkman3_mobile.setColumns(10);

		/****************** 配置相关 **********************/
		JPanel panel6 = new JPanel();
		panel6.setBounds(10, 350, 650, 108);
		frame.getContentPane().add(panel6);
		panel6.setLayout(null);
		panel6.setBorder(new TitledBorder(ResManager.getString("RobotTicket.panel.configuration")));

		boxkTwoSeat = new JCheckBox(ResManager.getString("RobotTicket.label.boxkTwoSeat"));
		boxkTwoSeat.setBounds(5, 26, 100, 15);
		panel6.add(boxkTwoSeat);
		boxkTwoSeat.setHorizontalAlignment(SwingConstants.RIGHT);

		hardSleePer = new JCheckBox(ResManager.getString("RobotTicket.label.hardSleePer"));
		hardSleePer.setBounds(100, 26, 100, 15);
		panel6.add(hardSleePer);
		hardSleePer.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel label_13 = new JLabel(ResManager.getString("RobotTicket.label.passCode"));
		label_13.setBounds(180, 26, 60, 13);
		panel6.add(label_13);
		label_13.setHorizontalAlignment(SwingConstants.RIGHT);

		passCode = new JTextField();
		passCode.setToolTipText(ResManager.getString("RobotTicket.label.passCode"));
		passCode.setBounds(255, 23, 60, 21);
		panel6.add(passCode);
		passCode.setColumns(10);

		passCodeImage = new JLabel();
		passCodeImage.setBounds(325, 23, 60, 20);
		passCodeImage.setText(ResManager.getString("RobotTicket.label.passCodeImage"));
		panel6.add(passCodeImage);

		isAutoCode = new JCheckBox(ResManager.getString("RobotTicket.label.isAutoCode"));
		isAutoCode.setBounds(365, 26, 120, 15);
		panel6.add(isAutoCode);
		isAutoCode.setHorizontalAlignment(SwingConstants.RIGHT);

		allAuto = new JCheckBox(ResManager.getString("RobotTicket.label.allAutoCode"));
		allAuto.setBounds(500, 26, 100, 15);
		panel6.add(allAuto);
		isAutoCode.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel label_15 = new JLabel(ResManager.getString("RobotTicket.label.txtStartDate"));
		label_15.setBounds(30, 70, 60, 13);
		panel6.add(label_15);
		label_15.setHorizontalAlignment(SwingConstants.RIGHT);

		MaskFormatter mf = null;
		try {
			mf = new MaskFormatter("####-##-##");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		txtStartDate = new JFormattedTextField(mf);
		txtStartDate.setToolTipText(ResManager.getString("RobotTicket.label.txtStartDate"));
		txtStartDate.setBounds(100, 68, 84, 21);
		panel6.add(txtStartDate);
		txtStartDate.setColumns(10);

		JLabel label_16 = new JLabel(ResManager.getString("RobotTicket.label.formCode"));
		label_16.setBounds(200, 70, 40, 17);
		panel6.add(label_16);
		label_16.setHorizontalAlignment(SwingConstants.RIGHT);

		formCode = new JTextField();
		formCode.setToolTipText(ResManager.getString("RobotTicket.label.formCode"));
		formCode.setBounds(250, 70, 60, 21);
		panel6.add(formCode);
		formCode.setColumns(10);

		JLabel label_17 = new JLabel(ResManager.getString("RobotTicket.label.toCode"));
		label_17.setBounds(320, 68, 60, 17);
		panel6.add(label_17);
		label_17.setHorizontalAlignment(SwingConstants.RIGHT);

		toCode = new JTextField();
		toCode.setToolTipText(ResManager.getString("RobotTicket.label.toCode"));
		toCode.setBounds(390, 68, 60, 21);
		panel6.add(toCode);
		toCode.setColumns(10);

		startButton = new JButton();
		startButton.setText(ResManager.getString("RobotTicket.btn.start"));
		startButton.setBounds(540, 64, 70, 28);
		panel6.add(startButton);
		startButton.addActionListener(new StartButton());

		/****************** 信息输出相关 **********************/
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 460, 640, 145);
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollPane);

		messageOut = new JTextArea();
		scrollPane.setViewportView(messageOut);
		messageOut.setText(ResManager.getString("RobotTicket.textarea.messageOut"));
		messageOut.setEditable(false);
		messageOut.setLineWrap(true);

		// JScrollPane scroll = new JScrollPane(messageOut);
		// scroll.setBounds(15, 460, 640, 145);
		// scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// frame.add(scroll);

		// messageOut = new JTextArea();
		// messageOut.setText(ResManager.getString("RobotTicket.textarea.messageOut"));
		// messageOut.setEditable(false);

	}

	public static void main(String[] arg0) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainWin window = new MainWin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {
			ResManager.initProperties("E:\\config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MainWin() {
		// 初始话布局
		initLayout();
		// 初始化登录验证码
		url = initLoginImage();
		this.mainWin = this;
	}

	// 初始化登录验证码
	public String initLoginImage() {
		String url = "";
		try {
			url = path + "image" + File.separator;
			File file = new File(url);
			if (!file.exists()) {
				file.mkdirs();
			}
			url += "passcode-login.jpg";
			// 获取cookie
			ClientCore.getCookie();
			ClientCore.getPassCode(Constants.GET_LOGINURL_PASSCODE, url);
			code.setIcon(ToolUtil.getImageIcon(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	// 登录按钮onclick事件监听
	class LoginBtn implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			if (ResManager.getString("RobotTicket.btn.login").equals(btn.getText())) {
				List<String> list = ToolUtil.validateWidget(username, password, authcode);
				if (list.size() > 0) {
					String msg = "";
					for (int i = 0; i < list.size(); i++) {
						msg += (i == list.size() - 1 ? list.get(i) : list.get(i) + ",");
					}
					showMsg(msg + "不能为空！");
					return;
				}
				ToolUtil.login(mainWin);
			}
		}
	}

	// 导入按钮onclick事件监听
	class ImpSession implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			if (ResManager.getString("RobotTicket.btn.import").equals(btn.getText())) {
				Constants.BIGIPSERVEROTSWEB_VALUE = bigWeb.getText().trim();
				Constants.JSESSIONID_VALUE = jSession.getText().trim();
				if (StringUtil.isEmptyString(toCode.getText().trim()) && StringUtil.isEmptyString(formCode.getText().trim()) && Constants.SYS_STRING_DATEFORMAT.equals(txtStartDate.getText().trim())) {
					showMsg("请输入发站&到站&乘车日期验证session是否为登录过！");
				} else {
					OrderRequest req = new OrderRequest();
					req.setFrom(formCode.getText().trim());
					req.setTo(toCode.getText().trim());
					req.setTrain_date(txtStartDate.getText().trim());
					try {
						trainQueryInfo = ClientCore.queryTrain(req);
						if (trainQueryInfo.size() > 0) {
							showMsg("导入session成功!");
							messageOut.setText(messageOut.getText() + "本次一共为您筛选到" + trainQueryInfo.size() + "趟列车信息\n");
							autoGetTrainInfo = getAutoGetTrainInfo();
							autoGetTrainInfo.trainQueryInfoClass();
						} else {
							showMsg("导入session失败,请仔细检查session!");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	// 开始按钮onclick事件监听
	class StartButton implements ActionListener {

		@SuppressWarnings("rawtypes")
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			if (ResManager.getString("RobotTicket.btn.start").equals(btn.getText())) {
				List list = getUserInfo();
				if (list.size() == 0) {
					showMsg("请输入联系人信息!");
				}
			}
		}

	}

	/**
	 * 显示消息
	 * 
	 * @param msg
	 */
	public void showMsg(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}

	/**
	 * 获取token和提交令牌
	 */
	protected void initParameter() {
		try {
			// 初始化TOKEN
			ClientCore.getToken();
			// 初始提交令牌
			ClientCore.confirmPassenger();
			if (StringUtil.isEmptyString(Constants.TOKEN) && StringUtil.isEmptyString(Constants.LEFTTICKETSTR)) {
				initParameter();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取乘车人
	 * 
	 * @return List<UserInfo>
	 */
	public List<UserInfo> getUserInfo() {
		List<UserInfo> list = new ArrayList<UserInfo>();
		if (!StringUtil.isEmptyString(linkman1_name.getText().trim()) && !StringUtil.isEmptyString(linkman1_cardNo.getText().trim())) {
			UserInfo userInfo = new UserInfo(linkman1_cardNo.getText().trim(), linkman1_name.getText().trim(), linkman1_mobile.getText().trim());
			list.add(userInfo);
		}
		if (!StringUtil.isEmptyString(linkman2_name.getText().trim()) && !StringUtil.isEmptyString(linkman2_cardNo.getText().trim())) {
			UserInfo userInfo = new UserInfo(linkman2_cardNo.getText().trim(), linkman2_name.getText().trim(), linkman2_mobile.getText().trim());
			list.add(userInfo);
		}
		if (!StringUtil.isEmptyString(linkman3_name.getText().trim()) && !StringUtil.isEmptyString(linkman3_cardNo.getText().trim())) {
			UserInfo userInfo = new UserInfo(linkman3_cardNo.getText().trim(), linkman3_name.getText().trim(), linkman3_mobile.getText().trim());
			list.add(userInfo);
		}
		return list;
	}

	/**
	 * 是否动车优先
	 * 
	 * @return
	 */
	public boolean isBoxkTwoSeat() {
		return boxkTwoSeat.isSelected();
	}

	/**
	 * 是否卧铺优先
	 * 
	 * @return
	 */
	public boolean isHardSleePer() {
		return hardSleePer.isSelected();
	}

	public AutoGetTrainInfo getAutoGetTrainInfo() {
		return autoGetTrainInfo = new AutoGetTrainInfo(trainQueryInfo, this);
	}
}