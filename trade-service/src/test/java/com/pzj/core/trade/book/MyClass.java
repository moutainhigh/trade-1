package com.pzj.core.trade.book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyClass {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy K:m:s a", Locale.ENGLISH);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr = "May 31, 2017 12:00:00 AM";
		long dateLong = 1495790150433l;
		try {
			System.out.println("d1 ====== " + new Date().getTime());
			System.out.println("d2 ====== " + sdf.parse(dateStr).getTime());
			System.out.println("d3 ====== " + df.format(new Date(dateLong)));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}