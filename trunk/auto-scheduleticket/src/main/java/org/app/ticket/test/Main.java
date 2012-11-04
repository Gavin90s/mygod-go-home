package org.app.ticket.test;

import java.util.ArrayList;
import java.util.List;

import org.app.ticket.bean.OrderRequest;
import org.app.ticket.bean.TrainQueryInfo;
import org.app.ticket.bean.UserInfo;
import org.app.ticket.constants.Constants;
import org.app.ticket.core.ClientCore;
import org.app.ticket.core.SubmitThread;
import org.app.ticket.util.DateUtil;

public class Main {
	public static void main(String[] arg0) throws Exception {
		Constants.BIGIPSERVEROTSWEB_VALUE = "2748580106.22560.0000";
		Constants.JSESSIONID_VALUE = "E9CDC29BF33E6CADBBA6C5C10D0E0F68";
		// 第一步 获取token和列车信息
		ClientCore.getToken();
		OrderRequest req = new OrderRequest();
		req.setFrom("深圳");
		req.setTo("常德");
		req.setTrain_date("2012-11-15");
		req.setQuery_date(DateUtil.getCurDate());
		List<TrainQueryInfo> trainQueryInfoList = ClientCore.queryTrain(req);
		for (TrainQueryInfo s : trainQueryInfoList) {
			System.out.println(s);
		}
		// 第二步 提交预定车次信息(获取重定向地址中的URL和LEFTTICKETSTR)
		ClientCore.submitOrderRequest(trainQueryInfoList.get(1), req);

		// 第五步 LEFTTICKETSTR
		System.out.println(Constants.TOKEN + "\n" + Constants.LEFTTICKETSTR);

		// TODO 获取火车票数量
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		UserInfo userInfo = new UserInfo("430703199012266454", "杨杰", "13714389492");
		userInfos.add(userInfo);
		
		// 获取火车票数量
		ClientCore.getQueueCount(Constants.GET_URL_GETQUEUECOUNT,req,userInfos,trainQueryInfoList.get(1));
		
		SubmitThread sb = new SubmitThread(trainQueryInfoList, userInfos, req);
		sb.start();
		
//		String msg = ClientCore.confirmSingleForQueueOrder(trainQueryInfoList.get(1), req, userInfos, "5hvz");
//		System.out.println(msg);
		
		
	}
}
