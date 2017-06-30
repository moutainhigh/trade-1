package com.pzj.trade.book.model;

import java.io.Serializable;
import java.util.List;

public class BookProductModel implements Serializable {

	/**  */
	private static final long serialVersionUID = -7975188495603970572L;

	/**
	 * @apiDefine BookProductModel  BookProductModel 预订单产品信息模型
	 * 
	 * @apiParam (BookProductModel) {long} productId  产品id  必填
	 * @apiParam (BookProductModel) {long} scenicId  景区id  必填
	 * @apiParam (BookProductModel) {String} productName  产品规格名称
	 * @apiParam (BookProductModel) {long} screeningsId  场次id
	 * @apiParam (BookProductModel) {long} areaId  区域id
	 * @apiParam (BookProductModel) {int} bugNum  产品购买数量  必填,必须大于0
	 * @apiParam (BookProductModel) {int} price  特价票免票产品价格
	 * @apiParam (BookProductModel) {long} strategyRelationId  产品政策关系id  必填
	 * @apiParam (BookProductModel) {Long} strategyId 政策id 必填
	 * @apiParam (BookProductModel) {long} parentUserId  父级供应商id 必填
	 * @apiParam (BookProductModel) {long} subUserId  子级供应商id 必填
	 * @apiParam (BookProductModel) {List} seats  座位 Long
	 * @apiParam (BookProductModel) {List} tourists  游客信息 TouristModel
	 * 
	 * 
	 */

	private long strategyId;

	private long strategyRelationId;

	private long parentUserId;

	private long subUserId;

	private long productId;

	private long scenicId;

	private int buyNum;

	private List<Long> seats;

	private int price;

	private String productName;

	private long screeningsId;

	private long areaId;

	public long getStrategyRelationId() {
		return strategyRelationId;
	}

	public void setStrategyRelationId(long strategyRelationId) {
		this.strategyRelationId = strategyRelationId;
	}

	public long getParentUserId() {
		return parentUserId;
	}

	public void setParentUserId(long parentUserId) {
		this.parentUserId = parentUserId;
	}

	public long getSubUserId() {
		return subUserId;
	}

	public void setSubUserId(long subUserId) {
		this.subUserId = subUserId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public List<Long> getSeats() {
		return seats;
	}

	public void setSeats(List<Long> seats) {
		this.seats = seats;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getScreeningsId() {
		return screeningsId;
	}

	public void setScreeningsId(long screeningsId) {
		this.screeningsId = screeningsId;
	}

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public long getScenicId() {
		return scenicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	public long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(long strategyId) {
		this.strategyId = strategyId;
	}

}
