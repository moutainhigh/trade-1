package com.pzj.voucher.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 创建核销时产品信息
 * 
 * @author Mf-CX05
 * @version $Id: VoucherProductInfoVO.java, v 0.1 2016年8月9日 下午6:12:07 Mf-CX05 Exp $
 */
public class VoucherProductInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 产品ID
	 */
	private Long productId;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 产品数量
	 */
	private Integer productNum;

	/**
	 * 产品的开始时间
	 * 对于门票产品是游玩开始时间
	 * 对于剧场演艺产品是演出开始时间
	 */
	private Date startTime;

	/**
	 * 产品的开始时间
	 * 对于门票产品是游玩结束时间
	 * 对于剧场演艺产品是演出结束时间
	 */
	private Date endTime;

	/**
	 * 此字段主要是针对门票和演艺使用
	 * 这个Map的存储是这样的，主要是存储检票点信息，包含检票点ID，和最大检票次数，最大检票次数为-99为无限次进入
	 * 例如
	 * {
	 *  "child_product_id_1" : 1,
	 *  "child_product_id_2" : 1,
	 *  "child_product_id_3" : -99,
	 * }
	 */
	private Map<Long, Integer> childProductMap;

	/**  演艺信息  */
	/** 场次 */
	private String screening;

	/** 区域  */
	private String region;

	/** 座位号 */
	private String seatNumbers;

	/**
	 * 库存ID
	 */
	private Long stockId;

	/**
	 * 产品组Id
	 */
	//private Long               productGroupId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public Map<Long, Integer> getChildProductMap() {
		return childProductMap;
	}

	public void setChildProductMap(Map<Long, Integer> childProductMap) {
		this.childProductMap = childProductMap;
	}

	public String getScreening() {
		return screening;
	}

	public void setScreening(String screening) {
		this.screening = screening;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSeatNumbers() {
		return seatNumbers;
	}

	public void setSeatNumbers(String seatNumbers) {
		this.seatNumbers = seatNumbers;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	//    public Long getProductGroupId() {
	//        return productGroupId;
	//    }
	//
	//    public void setProductGroupId(Long productGroupId) {
	//        this.productGroupId = productGroupId;
	//    }

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
