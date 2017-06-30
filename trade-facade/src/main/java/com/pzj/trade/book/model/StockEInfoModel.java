package com.pzj.trade.book.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约单创建，编辑异常信息类
 * 
 * @author Administrator
 * @version $Id: BookEInfoModel.java, v 0.1 2017年6月19日 下午2:10:11 Administrator Exp $
 */
public class StockEInfoModel implements Serializable {

	/**
	* @apiDefine StockEInfoModel  StockEInfoModel 库存错误信息
	* 
	* @apiParam (StockEInfoModel) {Long} productId  产品id 
	* 
	* @apiParam (StockEInfoModel) {Long} stockId 库存id
	* 
	* @apiParam (StockEInfoModel) {String} stockName 库存规则名称
	* 
	* @apiParam (StockEInfoModel) {Long} stockRuleId  库存规则id
	* 
	* @apiParam (StockEInfoModel) {Integer} stockType 库存类型（  1、单一库存 2、每日库存）
	* 
	* @apiParam (StockEInfoModel) {Integer} remainNum  剩余库存数 
	* 
	* @apiParam (StockEInfoModel) {Date} travelDate 出游日期
	* 
	*/

	/**
	* 产品id
	*/
	private Long productId;
	/**
	 * 库存id
	 */
	private Long stockId;

	/**
	 * 库存规则名称
	 */
	private String stockName;
	/**
	 * 库存规则id
	 */
	private Long stockRuleId;
	/**
	 * 库存类型（  1、单一库存 2、每日库存）({@linkplain com.pzj.core.stock.enums.StockRuleTypeEnum})
	 */
	private Integer stockType;
	/**
	 * 剩余库存数
	 */
	private Integer remainNum;
	/**
	 * 出游日期
	 */
	private Date travelDate;

	/**
	 * 预约单异常信息
	 */
	private String tradeErrorMsg;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Long getStockRuleId() {
		return stockRuleId;
	}

	public void setStockRuleId(Long stockRuleId) {
		this.stockRuleId = stockRuleId;
	}

	public Integer getStockType() {
		return stockType;
	}

	public void setStockType(Integer stockType) {
		this.stockType = stockType;
	}

	public Integer getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(Integer remainNum) {
		this.remainNum = remainNum;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getTradeErrorMsg() {
		return tradeErrorMsg;
	}

	public void setTradeErrorMsg(String tradeErrorMsg) {
		this.tradeErrorMsg = tradeErrorMsg;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

}
