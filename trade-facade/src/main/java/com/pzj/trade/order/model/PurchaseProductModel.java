package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 下单选购的商品.
 * @author CHJ
 *
 */
public class PurchaseProductModel implements Serializable {

	private static final long serialVersionUID = 5458805615003061366L;

	/** 产品ID 必填*/
	private long productId;

	/** 商品数量 必填*/
	private int productNum;

	/** 游客姓名*/
	private String visitor_name;

	/** 游客身份证号*/
	private String visitor_idcaord_no;

	/** 出游开始日期*/
	private Date play_time;

	/** 产品组id*/
	private long product_group_id;

	/** 景区演艺对象，包括景区演艺属性*/
	private TicketModel ticketModel;

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

	public String getVisitor_name() {
		return visitor_name;
	}

	public void setVisitor_name(String visitor_name) {
		this.visitor_name = visitor_name;
	}

	public String getVisitor_idcaord_no() {
		return visitor_idcaord_no;
	}

	public void setVisitor_idcaord_no(String visitor_idcaord_no) {
		this.visitor_idcaord_no = visitor_idcaord_no;
	}

	public Date getPlay_time() {
		return play_time;
	}

	public void setPlay_time(Date play_time) {
		this.play_time = play_time;
	}

	public long getProduct_group_id() {
		return product_group_id;
	}

	public void setProduct_group_id(long product_group_id) {
		this.product_group_id = product_group_id;
	}

	public TicketModel getTicketModel() {
		return ticketModel;
	}

	public void setTicketModel(TicketModel ticketModel) {
		this.ticketModel = ticketModel;
	}

}
