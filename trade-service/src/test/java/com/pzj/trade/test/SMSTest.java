package com.pzj.trade.test;

import com.pzj.commons.utils.SMSSender;

public class SMSTest {

	public static void main(String[] args) {
		String sms = "测试短信,kangzl发送";
		SMSSender.sendSMS("13439944655", sms);
		SMSSender.sendSMS("18600576560", sms);

	}

}
