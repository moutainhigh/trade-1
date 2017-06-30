package com.pzj.core.trade.order.engine.model;

import java.math.BigDecimal;
import java.util.Date;

import com.pzj.core.strategy.common.global.StrategyGlobal;
import com.pzj.core.strategy.common.global.StrategyGlobal.StrategyTypeEnum;
import com.pzj.trade.order.common.SalePortEnum;

/**
 * 商品模型.
 * @author YRJ
 *
 */
public class MerchModel implements Cloneable {

	/** 商品ID */
	private long prodId;

	/** 购买数量 */
	private int buyNum;

	/** 商品名称 */
	private String prodName;

	/** 商品类型 */
	private int prodType;

	/**
	 * 商品团散标识.
	 * <ul>
	 * <li>1. 团</li>
	 * <li>0. 散</li>
	 * </ul>
	 */
	private int prodVarie;

	/** 商品单价 */
	private double price;

	/** 供应商ID*/
	private long supplierId;

	/** 渠道ID */
	private long channelId;

	/** 凭证ID */
	private long voucherId;

	/**
	 * 是否需要代下单. 默认为: 1.
	 * <ul>
	 * <li>1. 不需要</li>
	 * <li>2. 需要</li>
	 * </ul>
	 */
	private int agent;

	/** 凭证类型 */
	private int vourType;

	/** 清算类型 */
	private int clearType;

	/** 一证一票 */
	private int oneVote;

	/**
	 * 是否为对接
	 * <ul>
	 * <li>1. 是</li>
	 * <li>0. 非</li>
	 * </ul>
	 */
	private int isDock;

	/**
	 * 是否需要二次确认
	 * <ul>
	 * <li>1. 不需要</li>
	 * <li>2. 需要</li>
	 * <li>3. 已确认</li>
	 * </ul>
	 */
	private int needConfirm;

	/** 
	 * 是否需要填写游玩日期
	 * <ul>
	 * <li>true: 需要</li>
	 * <li>false: 不需要</li>
	 * </ul>
	 */
	private boolean isPlayTime;

	/** 游玩有效期 */
	private long effecTime;

	/** 订单有效期 */
	private Date expireTime;

	/** 产品可用开始日期 */
	private Date useStartDate;

	/** 产品可用结束日期 */
	private Date useEndDate;

	/** 产品销售开始日期 */
	private Date saleStartDate;

	/** 产品销售结束日期 */
	private Date saleEndDate;

	/** 游玩开始时间点 */
	private Date playDate;

	/** 游玩结束时间点 */
	//private Date playEndTime;

	/** 分销商政策 */
	private StrategyModel resellerStrategy;

	/** 供应商政策 */
	private StrategyModel supplierStrategy;

	public MerchModel(long productId) {
		this.prodId = productId;
	}

	public MerchModel(long productId, Date playDate) {
		this.prodId = productId;
		this.playDate = playDate;
	}

	public long getProdId() {
		return prodId;
	}

	public void setProdId(long prodId) {
		this.prodId = prodId;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getProdType() {
		return prodType;
	}

	public void setProdType(int prodType) {
		this.prodType = prodType;
	}

	public int getProdVarie() {
		return prodVarie;
	}

	public void setProdVarie(int prodVarie) {
		this.prodVarie = prodVarie;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public BigDecimal getPrice(StrategyModel strategy) {
		if (SalePortEnum.isMicShop(strategy.getSale_port()) && strategy.getStrategyType() != StrategyTypeEnum.GYDMF.getType()) {
			return new BigDecimal(strategy.getAdvicePrice());
		}
		//后返取建议零售价
		if (strategy.getRebateType() == StrategyGlobal.RebateMethodEnum.End.getId()) {
			return new BigDecimal(strategy.getAdvicePrice());
		}
		//其他取结算价
		return new BigDecimal(strategy.getSettlementPrice());
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public int getAgent() {
		return agent;
	}

	public void setAgent(int agent) {
		this.agent = agent;
	}

	public int getVourType() {
		return vourType;
	}

	public void setVourType(int vourType) {
		this.vourType = vourType;
	}

	public int getClearType() {
		return clearType;
	}

	public void setClearType(int clearType) {
		this.clearType = clearType;
	}

	public int getOneVote() {
		return oneVote;
	}

	public void setOneVote(int oneVote) {
		this.oneVote = oneVote;
	}

	public int getIsDock() {
		return isDock;
	}

	public void setIsDock(int isDock) {
		this.isDock = isDock;
	}

	public int getNeedConfirm() {
		return needConfirm;
	}

	public void setNeedConfirm(int needConfirm) {
		this.needConfirm = needConfirm;
	}

	public boolean getIsPlayTime() {
		return isPlayTime;
	}

	public void setIsPlayTime(boolean isPlayTime) {
		this.isPlayTime = isPlayTime;
	}

	public long getEffecTime() {
		return effecTime;
	}

	public void setEffecTime(long effecTime) {
		this.effecTime = effecTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getUseStartDate() {
		return useStartDate;
	}

	public void setUseStartDate(Date useStartDate) {
		this.useStartDate = useStartDate;
	}

	public Date getUseEndDate() {
		return this.useEndDate;
	}

	public void setUseEndDate(Date useEndDate) {
		this.useEndDate = useEndDate;
	}

	public Date getSaleStartDate() {
		return saleStartDate;
	}

	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}

	public Date getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	public Date getPlayDate() {
		return playDate;
	}

	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}

	//	public Date getPlayEndTime() {
	//		return playEndTime;
	//	}
	//
	//	public void setPlayEndTime(Date playEndTime) {
	//		this.playEndTime = playEndTime;
	//	}

	public StrategyModel getResellerStrategy() {
		return resellerStrategy;
	}

	public void setResellerStrategy(StrategyModel resellerStrategy) {
		this.resellerStrategy = resellerStrategy;
	}

	public StrategyModel getSupplierStrategy() {
		return supplierStrategy;
	}

	public void setSupplierStrategy(StrategyModel supplierStrategy) {
		this.supplierStrategy = supplierStrategy;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (prodId ^ (prodId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MerchModel other = (MerchModel) obj;
		if (prodId != other.prodId)
			return false;
		return true;
	}

}
