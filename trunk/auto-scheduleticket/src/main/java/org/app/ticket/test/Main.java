package org.app.ticket.test;

import java.util.ArrayList;
import java.util.List;

import org.app.ticket.bean.OrderRequest;
import org.app.ticket.bean.TrainQueryInfo;
import org.app.ticket.bean.UserInfo;
import org.app.ticket.constants.Constants;
import org.app.ticket.core.ClientCore;
import org.app.ticket.util.DateUtil;

public class Main {
	public static void main(String[] arg0) throws Exception {
		Constants.BIGIPSERVEROTSWEB_VALUE = "2111045898.48160.0000";
		Constants.JSESSIONID_VALUE = "040EA32EAABFEC7154775B304FBE3D5F";
		// 第一步 获取token和列车信息
		ClientCore.getToken();
		System.out.println("TOKEN = " + Constants.TOKEN);
		OrderRequest req = new OrderRequest();
		req.setFrom("深圳");
		req.setTo("常德");
		req.setTrain_date("2012-11-19");
		req.setQuery_date(DateUtil.getCurDate());
		List<TrainQueryInfo> trainQueryInfoList = ClientCore.queryTrain(req);
		for (TrainQueryInfo s : trainQueryInfoList) {
			System.out.println(s);
		}
		// 第二步 提交预定车次信息(获取重定向地址中的URL和LEFTTICKETSTR)
		ClientCore.submitOrderRequest(trainQueryInfoList.get(1), req);

		System.out.println("NEW = " + Constants.TOKEN + "\n" + Constants.LEFTTICKETSTR);

		// TODO 获取火车票数量

		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		UserInfo userInfo = new UserInfo("430703199012266454", "杨杰", "13714389492");
		userInfos.add(userInfo);

		// 获取火车票数量
		ClientCore.getQueueCount(Constants.GET_URL_GETQUEUECOUNT, req, userInfos, trainQueryInfoList.get(1));

		// SubmitThread sb = new SubmitThread(null,trainQueryInfoList,
		// userInfos, req, null);
		//sb.start();
	}
}
