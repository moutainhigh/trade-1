package com.pzj.core.trade.sms.engine.model;

/**
 * 商品的规格参数
 * @author kangzl
 *
 */
public class MerchStandardModel {

	/** 规格名称 */
	private String standardName;
	/**  规格数量*/
	private int standardNum;

	public int getStandardNum() {
		return standardNum;
	}

	public void setStandardNum(int standardNum) {
		this.standardNum = standardNum;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}
}
