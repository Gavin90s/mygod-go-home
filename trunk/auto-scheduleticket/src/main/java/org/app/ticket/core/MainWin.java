package org.app.ticket.core;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import org.app.ticket.logic.KeepCookieThread;
import org.app.ticket.logic.LoginThread;
import org.app.ticket.logic.SubmitThread;
import org.app.ticket.logic.TicketThread;
import org.app.ticket.msg.ResManager;
import org.app.ticket.util.DateUtil;
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
public class MainWin extends JFrame {
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
	public JCheckBox isAutoCode;
	private JFormattedTextField txtStartDate;
	private JTextField formCode;
	private JTextField toCode;
	private JButton startButton;

	/************* 输出相关 ****************/
	public JTextArea messageOut;

	/************** 业务逻辑相关的变量 ****************/
	// 存放列车信息
	private List<TrainQueryInfo> trainQueryInfo;
	// 存放用户信息
	private List<UserInfo> userInfoList;
	// 存放查询火车实体
	private OrderRequest req;
	// 项目根路径
	public static String path;
	// 类本身
	private MainWin mainWin = null;
	// 登录验证码路径
	public String loginUrl;
	// 提交订单验证码路径
	public String submitUrl;
	// 是否登录成功
	public static boolean isLogin = false;
	// 传入参数
	private static String tessPath = null;
	// 线程是否运行
	public boolean isRunThread = false;
	// 是否点击了停止按钮
	public boolean isStopRun = false;

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
		// 关闭窗口 保存相关用户信息
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// 保存用户实体
				try {
					ToolUtil.getUserInfo(path, "UI.dat", username, password, linkman1_name, linkman1_cardNo, linkman1_mobile, linkman2_name, linkman2_cardNo, linkman2_mobile, linkman3_name, linkman3_cardNo, linkman3_mobile);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

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
		username.setName("username");
		username.setToolTipText(ResManager.getString("RobotTicket.label.user_name"));
		username.setBounds(60, 23, 100, 21);
		panel_o.add(username);
		username.setColumns(10);

		JLabel label_o1 = new JLabel(ResManager.getString("RobotTicket.label.password"));
		label_o1.setBounds(170, 26, 40, 15);
		panel_o.add(label_o1);
		label_o1.setHorizontalAlignment(SwingConstants.RIGHT);

		password = new JPasswordField();
		password.setName("password");
		password.setToolTipText(ResManager.getString("RobotTicket.label.password"));
		password.setBounds(220, 23, 100, 21);
		panel_o.add(password);
		password.setColumns(10);

		code = new JLabel();
		code.setBounds(340, 20, 60, 20);
		code.setToolTipText("点我刷新验证码！");
		panel_o.add(code);
		code.setHorizontalAlignment(SwingConstants.RIGHT);
		code.addMouseListener(new codeClick());

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
		linkman1_name.setName("linkman1_name");
		linkman1_name.setToolTipText(ResManager.getString("RobotTicket.label.username"));
		linkman1_name.setBounds(95, 23, 40, 21);
		panel3.add(linkman1_name);
		linkman1_name.setColumns(10);

		JLabel label_3 = new JLabel(ResManager.getString("RobotTicket.label.cardno"));
		label_3.setBounds(155, 26, 50, 15);
		panel3.add(label_3);
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman1_cardNo = new JTextField();
		linkman1_cardNo.setName("linkman1_cardNo");
		linkman1_cardNo.setToolTipText(ResManager.getString("RobotTicket.label.cardno"));
		linkman1_cardNo.setBounds(215, 23, 150, 21);
		panel3.add(linkman1_cardNo);
		linkman1_cardNo.setColumns(10);

		JLabel label_4 = new JLabel(ResManager.getString("RobotTicket.label.mobilephone"));
		label_4.setBounds(375, 26, 40, 15);
		panel3.add(label_4);
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman1_mobile = new JTextField();
		linkman1_mobile.setName("linkman1_mobile");
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
		linkman2_name.setName("linkman2_name");
		linkman2_name.setToolTipText(ResManager.getString("RobotTicket.label.username"));
		linkman2_name.setBounds(95, 23, 40, 21);
		panel4.add(linkman2_name);
		linkman2_name.setColumns(10);

		JLabel label_6 = new JLabel(ResManager.getString("RobotTicket.label.cardno"));
		label_6.setBounds(155, 26, 50, 15);
		panel4.add(label_6);
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman2_cardNo = new JTextField();
		linkman2_cardNo.setName("linkman2_cardNo");
		linkman2_cardNo.setToolTipText(ResManager.getString("RobotTicket.label.cardno"));
		linkman2_cardNo.setBounds(215, 23, 150, 21);
		panel4.add(linkman2_cardNo);
		linkman2_cardNo.setColumns(10);

		JLabel label_7 = new JLabel(ResManager.getString("RobotTicket.label.mobilephone"));
		label_7.setBounds(375, 26, 40, 15);
		panel4.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman2_mobile = new JTextField();
		linkman2_mobile.setName("linkman2_mobile");
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
		linkman3_name.setName("linkman3_name");
		linkman3_name.setToolTipText(ResManager.getString("RobotTicket.label.username"));
		linkman3_name.setBounds(95, 23, 40, 21);
		panel5.add(linkman3_name);
		linkman3_name.setColumns(10);

		JLabel label_9 = new JLabel(ResManager.getString("RobotTicket.label.cardno"));
		label_9.setBounds(155, 26, 50, 15);
		panel5.add(label_9);
		label_9.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman3_cardNo = new JTextField();
		linkman3_cardNo.setName("linkman3_cardNo");
		linkman3_cardNo.setToolTipText(ResManager.getString("RobotTicket.label.cardno"));
		linkman3_cardNo.setBounds(215, 23, 150, 21);
		panel5.add(linkman3_cardNo);
		linkman3_cardNo.setColumns(10);

		JLabel label_10 = new JLabel(ResManager.getString("RobotTicket.label.mobilephone"));
		label_10.setBounds(375, 26, 40, 15);
		panel5.add(label_10);
		label_10.setHorizontalAlignment(SwingConstants.RIGHT);

		linkman3_mobile = new JTextField();
		linkman3_mobile.setName("linkman3_mobile");
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
		hardSleePer.setBounds(140, 26, 100, 15);
		panel6.add(hardSleePer);
		hardSleePer.setHorizontalAlignment(SwingConstants.RIGHT);

		isAutoCode = new JCheckBox(ResManager.getString("RobotTicket.label.isAutoCode"));
		isAutoCode.setBounds(420, 26, 120, 15);
		panel6.add(isAutoCode);
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
	}

	public static void main(String[] arg0) {
		// TODO
		tessPath = arg0[0];
		// tessPath = "D:\\Program Files\\Tesseract-OCR";
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
			// TODO
			File file = new File(path + "config.properties");
			// File file = new File("E:\\" + "config.properties");
			if (!file.exists()) {
				return;
			}
			ResManager.initProperties(path + "config.properties");
			// ResManager.initProperties("E:\\" + "config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MainWin() {
		// 初始话布局
		initLayout();
		// 获取cookie
		try {
			ClientCore.getCookie();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 初始化登录验证码
		initLoginImage();
		this.mainWin = this;
		try {
			ToolUtil.setUserInfo(path, "UI.dat", username, password, linkman1_cardNo, linkman1_name, linkman1_mobile, linkman2_cardNo, linkman2_name, linkman2_mobile, linkman3_cardNo, linkman3_name, linkman3_mobile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化界面赋值失败！");
		}
	}

	// 初始化登录验证码
	public void initLoginImage() {
		try {
			loginUrl = path + "image" + File.separator;
			File file = new File(loginUrl);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 启动未获取到cookie的情况
			if (StringUtil.isEmptyString(Constants.JSESSIONID_VALUE) && StringUtil.isEmptyString(Constants.BIGIPSERVEROTSWEB_VALUE)) {
				ClientCore.getCookie();
			}
			loginUrl += "passcode-login.jpg";
			submitUrl = path + "image" + File.separator + "passcode-submit.jpg";
			ClientCore.getPassCode(Constants.GET_LOGINURL_PASSCODE, loginUrl);
			code.setIcon(ToolUtil.getImageIcon(loginUrl));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 单击刷新验证码
	class codeClick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			initLoginImage();
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

	// 登录按钮onclick事件监听
	class LoginBtn implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			if (ResManager.getString("RobotTicket.btn.login").equals(btn.getText())) {
				List<String> list = null;
				if (loginAuto.isSelected()) {
					list = ToolUtil.validateWidget(username, password);
				} else {
					list = ToolUtil.validateWidget(username, password, authcode);
				}
				if (list.size() > 0) {
					String msg = "";
					for (int i = 0; i < list.size(); i++) {
						msg += (i == list.size() - 1 ? list.get(i) : list.get(i) + ",");
					}
					showMsg(msg + "不能为空！");
					return;
				}
				// 登录线程
				new LoginThread(mainWin, tessPath).start();
			}
		}
	}

	// 导入按钮onclick事件监听
	class ImpSession implements ActionListener {
		@SuppressWarnings("static-access")
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
							mainWin.isLogin = true;
							showMsg("导入session成功!");
							messageOut.setText(messageOut.getText() + "本次一共为您筛选到" + trainQueryInfo.size() + "趟列车信息\n");
							// 启动cookie保持线程
							new KeepCookieThread().start();
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
				// 未输入联系人
				if (list.size() == 0) {
					showMsg("请至少输入1位联系人信息!");
					return;
				}
				// 未登录
				if (!isLogin) {
					showMsg("请登录!");
					return;
				}
				// 验证控件是否输入
				List<String> msglist = ToolUtil.validateWidget(txtStartDate, formCode, toCode);
				if (msglist.size() > 0) {
					String msg = "";
					for (int i = 0; i < msglist.size(); i++) {
						msg += (i == msglist.size() - 1 ? msglist.get(i) : msglist.get(i) + ",");
					}
					showMsg(msg + "不能为空！");
					return;
				}

				// 获取列车查询实体
				getOrderRequest();
				if (isRunThread) {
					showMsg("订票线程已启动!");
					return;
				}
				if (loginAuto.isSelected() && isAutoCode.isSelected()) {
					new SubmitThread(userInfoList, req, mainWin).start();
				} else {
					new TicketThread(userInfoList, req, mainWin).start();
				}
			}
			if (ResManager.getString("RobotTicket.btn.stop").equals(btn.getText())) {
				isStopRun = true;
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
	 * 获取乘车人
	 * 
	 * @return List<UserInfo>
	 */
	public List<UserInfo> getUserInfo() {
		List<UserInfo> list = new ArrayList<UserInfo>();
		if (StringUtil.isEmptyString(linkman1_cardNo.getText().trim()) || !StringUtil.isEmptyString(linkman1_name.getText().trim())) {
			if (!StringUtil.isEmptyString(linkman1_mobile.getText().trim())) {
				UserInfo userInfo1 = new UserInfo(linkman1_cardNo.getText().trim(), linkman1_name.getText().trim(), linkman1_mobile.getText().trim());
				list.add(userInfo1);
			} else {
				UserInfo userInfo1 = new UserInfo(linkman1_cardNo.getText().trim(), linkman1_name.getText().trim());
				list.add(userInfo1);
			}
		}
		if (!StringUtil.isEmptyString(linkman2_cardNo.getText().trim()) || !StringUtil.isEmptyString(linkman2_name.getText().trim())) {
			if (!StringUtil.isEmptyString(linkman2_mobile.getText().trim())) {
				UserInfo userInfo2 = new UserInfo(linkman2_cardNo.getText().trim(), linkman2_name.getText().trim(), linkman2_mobile.getText().trim());
				list.add(userInfo2);
			} else {
				UserInfo userInfo2 = new UserInfo(linkman2_cardNo.getText().trim(), linkman2_name.getText().trim());
				list.add(userInfo2);
			}
		}
		if (!StringUtil.isEmptyString(linkman3_cardNo.getText().trim()) || !StringUtil.isEmptyString(linkman3_name.getText().trim())) {
			if (!StringUtil.isEmptyString(linkman3_name.getText().trim())) {
				UserInfo userInfo3 = new UserInfo(linkman3_cardNo.getText().trim(), linkman3_name.getText().trim(), linkman3_mobile.getText().trim());
				list.add(userInfo3);
			} else {
				UserInfo userInfo3 = new UserInfo(linkman3_cardNo.getText().trim(), linkman3_name.getText().trim());
				list.add(userInfo3);
			}
		}
		userInfoList = list;
		return list;
	}

	/**
	 * 获取列车实体
	 * 
	 * @return
	 */
	private OrderRequest getOrderRequest() {
		req = new OrderRequest();
		req.setFrom(formCode.getText().trim());
		req.setTo(toCode.getText().trim());
		req.setTrain_date(txtStartDate.getText().trim());
		req.setQuery_date(DateUtil.getCurDate());
		return req;
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

	public JButton getStartButton() {
		return startButton;
	}

}