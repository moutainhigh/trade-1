package com.pzj.trade.book.model;

import java.io.Serializable;

public class SparpreisQOrderRuleModel implements Serializable {

	/**  */
	private static final long serialVersionUID = 3471134448759795998L;

	/**
	 * @apiDefine SparpreisQOureyRuleModel  SparpreisQOureyRuleModel 特价票、免票查询排序规则
	 * 
	 * @apiParam (SparpreisQOureyRuleModel) {Integer} sparpreisOrderRuleC  排序字段
	 * @apiParam (SparpreisQOureyRuleModel) {Integer} sparpreisOrderRuleT 排序类型 
	 * 
	 */

	//排序字段((1:游玩时间，2:创建时间))
	private Integer sparpreisOrderRuleC;

	//排序类型(1:升序，2:降序)
	private Integer sparpreisOrderRuleT;

	public Integer getSparpreisOrderRuleC() {
		return sparpreisOrderRuleC;
	}

	public void setSparpreisOrderRuleC(Integer sparpreisOrderRuleC) {
		this.sparpreisOrderRuleC = sparpreisOrderRuleC;
	}

	public Integer getSparpreisOrderRuleT() {
		return sparpreisOrderRuleT;
	}

	public void setSparpreisOrderRuleT(Integer sparpreisOrderRuleT) {
		this.sparpreisOrderRuleT = sparpreisOrderRuleT;
	}

}
