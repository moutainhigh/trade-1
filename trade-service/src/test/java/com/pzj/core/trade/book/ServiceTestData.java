package com.pzj.core.trade.book;

import java.util.List;

public class ServiceTestData {
	private static ServiceTestData util = new ServiceTestData();

	public <T extends Object> T createTestData(String filePath, Class<T> classType) {
		T result = JsonDataUtil.readObjectFromClasspath(filePath, classType);
		return result;
	}

	public <T extends Object> List<T> createTestDataList(String filePath, Class<T> classType) {
		List<T> result = JsonDataUtil.readListFromClasspath(filePath, classType);
		return result;
	}

	public static ServiceTestData getInstance() {
		return util;
	}

}
