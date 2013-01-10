package org.app.ticket.logic;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.app.ticket.bean.OrderRequest;
import org.app.ticket.bean.TrainQueryInfo;
import org.app.ticket.bean.UserInfo;
import org.app.ticket.constants.Constants;
import org.app.ticket.core.ClientCore;
import org.app.ticket.core.MainWin;
import org.app.ticket.msg.ResManager;
import org.app.ticket.util.StringUtil;
import org.app.ticket.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Title: TicketThread.java
 * @Description: 订票线程类
 * @Package org.app.ticket.logic
 * @author hncdyj123@163.com
 * @date 2013-1-9
 * @version V1.0
 * 
 */
public class TicketThread extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(TicketThread.class);

	private List<TrainQueryInfo> trainQueryInfoList;

	private List<UserInfo> userInfos;

	private OrderRequest req;

	private MainWin mainWin;

	private TrainQueryInfo trainQueryInfo;

	private boolean isSuccess = false;

	private int sum = 0;

	public TicketThread() {

	}

	public TicketThread(List<UserInfo> userInfos, OrderRequest req, MainWin mainWin) {
		this.userInfos = userInfos;
		this.req = req;
		this.mainWin = mainWin;
	}

	@Override
	public void run() {
		mainWin.getStartButton().setText(ResManager.getString("RobotTicket.btn.stop"));
		while (!isSuccess) {
			mainWin.isRunThread = true;
			if (mainWin.isStopRun) {
				mainWin.showMsg("停止线程成功!");
				mainWin.isStopRun = false;
				mainWin.isRunThread = false;
				break;
			}
			try {
				// 查询火车信息
				trainQueryInfoList = ClientCore.queryTrain(req);
				if (trainQueryInfoList.size() == 0) {
					mainWin.showMsg("请查看乘车日期是否输入正确!");
					mainWin.isRunThread = false;
					break;
				}

				trainQueryInfoList = ToolUtil.isSellPoint(trainQueryInfoList);
				// 判断是否到了放票时间点
				if (trainQueryInfoList.size() == 0) {
					mainWin.showMsg("您所要求预定的城市还未到放票时间点!");
					mainWin.isRunThread = false;
					break;
				}
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "火车信息\n");
				if (trainQueryInfoList.size() > 0) {
					for (TrainQueryInfo t : trainQueryInfoList) {
						mainWin.messageOut.setText(mainWin.messageOut.getText() + t.toString() + "\n");
					}
				}
				// 获取火车信息
				trainQueryInfo = (new AutoGetTrainInfo(trainQueryInfoList, mainWin, userInfos)).getSeattrainQueryInfo();
				if (trainQueryInfo == null) {
					mainWin.showMsg("指定列车和非指定列车均无票,请通过其它途径购买或稍后在尝试!");
					mainWin.isRunThread = false;
					mainWin.getStartButton().setText(ResManager.getString("RobotTicket.btn.start"));
					break;
				}
				// 第二步 提交预定车次信息(获取重定向地址中的URL和LEFTTICKETSTR)
				ClientCore.submitOrderRequest(trainQueryInfo, req);
				if (mainWin.isAutoCode.isSelected()) {

				}
				final JDialog randcodeDialog = new JDialog(mainWin, "输入验证码", true);
				randcodeDialog.setSize(200, 150);
				randcodeDialog.setLocationRelativeTo(mainWin);
				randcodeDialog.setResizable(false);

				JLabel l_randcode = new JLabel("请输入验证码:", JLabel.CENTER);

				final JTextField t_randcode = new JTextField(10);
				final JButton btn_randcode = new JButton("");
				String path = mainWin.submitUrl;
				ClientCore.getPassCode(Constants.GET_SUBMITURL_PASSCODE, path);
				btn_randcode.setIcon(ToolUtil.getImageIcon(path));

				btn_randcode.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String path = mainWin.submitUrl;
							ClientCore.getPassCode(Constants.GET_SUBMITURL_PASSCODE, path);
							btn_randcode.setIcon(ToolUtil.getImageIcon(path));
							logger.debug("获取订单验证码----");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});

				JPanel p_randcode = new JPanel();
				p_randcode.setLayout(new FlowLayout());
				p_randcode.add(t_randcode);
				p_randcode.add(btn_randcode);

				JButton btn_confirm = new JButton("提交");
				JPanel p_confirm = new JPanel();
				mainWin.messageOut.setText(mainWin.messageOut.getText() + "第" + (++sum) + "次提交订单.\n");

				btn_confirm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ev) {
						String msg = "";
						String randcode = t_randcode.getText();
						if (randcode == null || randcode.trim().length() != 4) {
							t_randcode.setText("");
							t_randcode.grabFocus();
						} else {
							try {
								if (trainQueryInfo != null) {
									// 检查订单
									msg = ClientCore.confirmSingleForQueueOrder(trainQueryInfo, req, userInfos, randcode, Constants.POST_URL_CHECKORDERINFO);
									logger.debug("最后输出消息:" + randcode + "----------" + msg);
									if (msg.contains("验证码")) {
										mainWin.messageOut.setText(mainWin.messageOut.getText() + "验证码错误！\n");
									} else {
										// 提交订单
										msg = ClientCore.confirmSingleForQueueOrder(trainQueryInfo, req, userInfos, randcode, Constants.POST_URL_CONFIRMSINGLEFORQUEUEORDER);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (msg.contains("Y")) {
								isSuccess = true;
								mainWin.isRunThread = false;
								mainWin.showMsg("订票成功!");
								// 订票成功后输出cookie
								mainWin.messageOut.setText(mainWin.messageOut.getText() + "Cookie:[" + Constants.JSESSIONID + "=" + Constants.JSESSIONID_VALUE + ";" + Constants.BIGIPSERVEROTSWEB + "=" + Constants.BIGIPSERVEROTSWEB_VALUE + "]\n");
								mainWin.getStartButton().setText(ResManager.getString("RobotTicket.btn.start"));
							}
							randcodeDialog.setVisible(false);
							randcodeDialog.dispose();
						}
					}
				});

				p_confirm.add(btn_confirm);
				Container container = randcodeDialog.getContentPane();
				container.setLayout(new GridLayout(3, 1));
				container.add(l_randcode);
				container.add(p_randcode);
				container.add(p_confirm);
				randcodeDialog.setVisible(true);
				logger.debug("线程休眠时间为:" + ResManager.getByKey("sleeptime"));
				// 休眠线程1S
				sleep(Integer.parseInt(StringUtil.isEmptyString(ResManager.getByKey("sleeptime")) ? "1000" : ResManager.getByKey("sleeptime")));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mainWin.getStartButton().setText(ResManager.getString("RobotTicket.btn.start"));
	}
}
