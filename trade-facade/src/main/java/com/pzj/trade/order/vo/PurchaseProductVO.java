package com.pzj.trade.order.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 下单选购的商品.
 * @author YRJ
 *
 */
public class PurchaseProductVO implements Serializable {

	/** 产品ID 必填*/
	private long productId;

	/** 商品名称 必填 */
	@Deprecated
	private String productName;

	/** 商品大类 是否为票，是为票，否为通用产品 必填 必填*/
	@Deprecated
	private boolean isTicket = false;

	/** 商品团散 团:1 散：0' 必填 */
	@Deprecated
	private String product_varie;

	/** 商品数量 必填*/
	private int productNum;

	/** 是否为组合商品 */
	@Deprecated
	private boolean combined;

	/** 是否需要二次确认 */
	@Deprecated
	private boolean confirm;

	/** 代下单标志：1:不需要代下单；2：需要代下单,默认为1*/
	@Deprecated
	private int agent_flag;

	/** 结算价 */
	@Deprecated
	private double price;

	/** 渠道ID 必填*/
	@Deprecated
	private long channelId;

	/** 供应商ID 必填*/
	@Deprecated
	private long supplierId;

	/** 产品类别必填*/
	@Deprecated
	private int product_type;

	/** 出游开始日期*/
	private Date playDate;

	/** 商品服务属性 */
	private long voucherId;

	@Deprecated
	private List<PurchaseProductVO> products;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	@Deprecated
	public String getProductName() {
		return productName;
	}

	@Deprecated
	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Deprecated
	public String getProduct_varie() {
		return product_varie;
	}

	@Deprecated
	public void setProduct_varie(String product_varie) {
		this.product_varie = product_varie;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	@Deprecated
	public boolean isCombined() {
		return combined;
	}

	@Deprecated
	public void setCombined(boolean combined) {
		this.combined = combined;
	}

	@Deprecated
	public boolean isConfirm() {
		return confirm;
	}

	@Deprecated
	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	@Deprecated
	public long getChannelId() {
		return channelId;
	}

	@Deprecated
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	@Deprecated
	public long getSupplierId() {
		return supplierId;
	}

	@Deprecated
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	@Deprecated
	public double getPrice() {
		return price;
	}

	@Deprecated
	public void setPrice(double price) {
		this.price = price;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	@Deprecated
	public List<PurchaseProductVO> getProducts() {
		return products;
	}

	@Deprecated
	public void setProducts(List<PurchaseProductVO> products) {
		this.products = products;
	}

	@Deprecated
	public int getProduct_type() {
		return product_type;
	}

	@Deprecated
	public void setProduct_type(int product_type) {
		this.product_type = product_type;
	}

	@Deprecated
	public boolean getIsTicket() {
		return isTicket;
	}

	@Deprecated
	public void setIsTicket(boolean isTicket) {
		this.isTicket = isTicket;
	}

	@Deprecated
	public int getAgent_flag() {
		return agent_flag;
	}

	@Deprecated
	public void setAgent_flag(int agent_flag) {
		this.agent_flag = agent_flag;
	}

	public Date getPlayDate() {
		return playDate;
	}

	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}

	private static final long serialVersionUID = 1L;
}
