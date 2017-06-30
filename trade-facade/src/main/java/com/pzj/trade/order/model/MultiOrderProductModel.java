package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.List;

/**
 * 下单选购的商品.
 * @author CHJ
 *
 */
public class MultiOrderProductModel implements Serializable {

	private static final long serialVersionUID = 5458805615003061366L;

	/**
	 * @apiDefine MultiOrderProductModel  MultiOrderProductModel 多级下单产品模型
	 * 
	 * @apiParam (MultiOrderProductModel) {long} productId  产品ID  必填
	 * @apiParam (MultiOrderProductModel) {int} productNum  商品数量  必填
	 * @apiParam (MultiOrderProductModel) {BigDecimal} price  商品数量  免票特价票必填
	 * @apiParam (MultiOrderProductModel) {long} strategyRelationId   政策产品关系id  必填
	 * @apiParam (MultiOrderProductModel) {long} scenicId  景区id  必填
	 * @apiParam (MultiOrderProductModel) {List} tourists  游客信息  TouristModel
	 * @apiParam (MultiOrderProductModel) {List} seats  座位号  必填  String
	 * 
	 */

	/** 产品ID 必填*/
	private long productId;

	/** 商品数量 必填*/
	private int productNum;

	/** 免票特价票必填(单位为分)*/
	private int price;

	/**关联关系id*/
	private long strategyRelationId;
	/**政策发布者id*/
	private long parentUserId;
	/***当前购买人的id*/
	private long subUserId;

	/** 景区id*/
	private long scenicId;

	/** 座位号*/
	List<Long> seats;
	/** 观演时间，毫秒值*/
	private long show_date;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	/**
	 * Getter method for property <tt>price</tt>.
	 * 
	 * @return property value of price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Setter method for property <tt>price</tt>.
	 * 
	 * @param price value to be assigned to property price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	public long getScenicId() {
		return scenicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	public List<Long> getSeats() {
		return seats;
	}

	public void setSeats(List<Long> seats) {
		this.seats = seats;
	}

	/**
	 * Getter method for property <tt>strategyRelationId</tt>.
	 * 
	 * @return property value of strategyRelationId
	 */
	public long getStrategyRelationId() {
		return strategyRelationId;
	}

	/**
	 * Setter method for property <tt>strategyRelationId</tt>.
	 * 
	 * @param strategyRelationId value to be assigned to property strategyRelationId
	 */
	public void setStrategyRelationId(long strategyRelationId) {
		this.strategyRelationId = strategyRelationId;
	}

	/**
	 * Getter method for property <tt>parentUserId</tt>.
	 * 
	 * @return property value of parentUserId
	 */
	public long getParentUserId() {
		return parentUserId;
	}

	/**
	 * Setter method for property <tt>parentUserId</tt>.
	 * 
	 * @param parentUserId value to be assigned to property parentUserId
	 */
	public void setParentUserId(long parentUserId) {
		this.parentUserId = parentUserId;
	}

	/**
	 * Getter method for property <tt>subUserId</tt>.
	 * 
	 * @return property value of subUserId
	 */
	public long getSubUserId() {
		return subUserId;
	}

	/**
	 * Setter method for property <tt>subUserId</tt>.
	 * 
	 * @param subUserId value to be assigned to property subUserId
	 */
	public void setSubUserId(long subUserId) {
		this.subUserId = subUserId;
	}

	/**
	 * Getter method for property <tt>show_date</tt>.
	 * 
	 * @return property value of show_date
	 */
	public long getShow_date() {
		return show_date;
	}

	/**
	 * Setter method for property <tt>show_date</tt>.
	 * 
	 * @param show_date value to be assigned to property show_date
	 */
	public void setShow_date(long show_date) {
		this.show_date = show_date;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MultiOrderProductModel [productId=");
		builder.append(productId);
		builder.append(", productNum=");
		builder.append(productNum);
		builder.append(", playDate=");
		builder.append(", price=");
		builder.append(price);
		builder.append(", rId=");
		builder.append(", scenicId=");
		builder.append(scenicId);
		builder.append(", tourists=");
		builder.append(", seats=");
		builder.append(seats);
		builder.append("]");
		return builder.toString();
	}

}
