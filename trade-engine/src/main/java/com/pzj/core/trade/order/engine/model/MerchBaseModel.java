package com.pzj.core.trade.order.engine.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品模型.
 * @author chj
 *
 */
public class MerchBaseModel {

	/** 商品ID */
	private long prodId;

	/** 产品组ID */
	private long spuId;

	/**关联关系id*/
	private long pId;
	/**政策发布者id*/
	private long parentUserId;
	/***当前购买人的id*/
	private long subUserId;

	/** 购买数量 */
	private int buyNum;

	/** 商品名称 */
	private String prodName;

	/** sku名称 */
	private String skuName;

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

	/** 预约类型  */
	private double bookType;

	/** 供应商ID*/
	private long supplierId;

	/** 渠道ID */
	private long channelId;

	/** 凭证类型 */
	private int vourType;

	/**核销方式 :1：按订单2：按规格3：按份数*/
	private int verificationType;

	/**核销方式 :1：联系人按订单 2：魔方码按订单 3：魔方码按规格 4:魔方码按份数 5:游客身份证按份数.*/
	private int verificationVourType;

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

	/** 库存ID*/
	private long stockId;

	/** 库存规则ID*/
	private long stock_rule_id;

	private GainLimitModel gainLimitModel;

	/** 是否自动核销*/
	private int autoConfirm;

	/** 游客填单项配置：1不需要 2需要所有 3需要一个*/
	private int passengerType;

	/** 游玩结束时间点 */
	//private Date playEndTime;

	/** 各级政策 */
	private List<StrategyBaseModel> strategys = new ArrayList<StrategyBaseModel>();

	/** 景区检票点信息 */
	private List<CheckinPointModel> checkinPointModels;

	/** 景区检票点id 查询同检票点购买限制使用 */
	private List<Long> checkinPointIds;

	/** 景区id*/
	private long scenicId;

	/** 演艺开始时间*/
	private Date show_start_time;

	/** 演艺结束时间*/
	private Date show_end_time;

	/** 区域*/
	private String region;

	/** 场次*/
	private String ronda;

	/** 座位号*/
	List<Long> seats;

	/** 支付方式*/
	private int pay_way;

	/**
	 * Getter method for property <tt>stockId</tt>.
	 * 
	 * @return property value of stockId
	 */
	public long getStockId() {
		return stockId;
	}

	/**
	 * Setter method for property <tt>stockId</tt>.
	 * 
	 * @param stockId value to be assigned to property stockId
	 */
	public void setStockId(long stockId) {
		this.stockId = stockId;
	}

	/**
	 * Getter method for property <tt>prodId</tt>.
	 * 
	 * @return property value of prodId
	 */
	public long getProdId() {
		return prodId;
	}

	/**
	 * Setter method for property <tt>prodId</tt>.
	 * 
	 * @param prodId value to be assigned to property prodId
	 */
	public void setProdId(long prodId) {
		this.prodId = prodId;
	}

	/**
	 * Getter method for property <tt>buyNum</tt>.
	 * 
	 * @return property value of buyNum
	 */
	public int getBuyNum() {
		return buyNum;
	}

	/**
	 * Setter method for property <tt>buyNum</tt>.
	 * 
	 * @param buyNum value to be assigned to property buyNum
	 */
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	/**
	 * Getter method for property <tt>spuId</tt>.
	 * 
	 * @return property value of spuId
	 */
	public long getSpuId() {
		return spuId;
	}

	/**
	 * Setter method for property <tt>spuId</tt>.
	 * 
	 * @param spuId value to be assigned to property spuId
	 */
	public void setSpuId(long spuId) {
		this.spuId = spuId;
	}

	/**
	 * Getter method for property <tt>pId</tt>.
	 * 
	 * @return property value of pId
	 */
	public long getpId() {
		return pId;
	}

	/**
	 * Setter method for property <tt>pId</tt>.
	 * 
	 * @param pId value to be assigned to property pId
	 */
	public void setpId(long pId) {
		this.pId = pId;
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
	 * Getter method for property <tt>prodName</tt>.
	 * 
	 * @return property value of prodName
	 */
	public String getProdName() {
		return prodName;
	}

	/**
	 * Setter method for property <tt>prodName</tt>.
	 * 
	 * @param prodName value to be assigned to property prodName
	 */
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	/**
	 * Getter method for property <tt>skuName</tt>.
	 * 
	 * @return property value of skuName
	 */
	public String getSkuName() {
		return skuName;
	}

	/**
	 * Setter method for property <tt>skuName</tt>.
	 * 
	 * @param skuName value to be assigned to property skuName
	 */
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	/**
	 * Getter method for property <tt>prodType</tt>.
	 * 
	 * @return property value of prodType
	 */
	public int getProdType() {
		return prodType;
	}

	/**
	 * Setter method for property <tt>prodType</tt>.
	 * 
	 * @param prodType value to be assigned to property prodType
	 */
	public void setProdType(int prodType) {
		this.prodType = prodType;
	}

	/**
	 * Getter method for property <tt>prodVarie</tt>.
	 * 
	 * @return property value of prodVarie
	 */
	public int getProdVarie() {
		return prodVarie;
	}

	/**
	 * Setter method for property <tt>prodVarie</tt>.
	 * 
	 * @param prodVarie value to be assigned to property prodVarie
	 */
	public void setProdVarie(int prodVarie) {
		this.prodVarie = prodVarie;
	}

	/**
	 * Getter method for property <tt>price</tt>.
	 * 
	 * @return property value of price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Setter method for property <tt>price</tt>.
	 * 
	 * @param price value to be assigned to property price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Getter method for property <tt>supplierId</tt>.
	 * 
	 * @return property value of supplierId
	 */
	public long getSupplierId() {
		return supplierId;
	}

	/**
	 * Setter method for property <tt>supplierId</tt>.
	 * 
	 * @param supplierId value to be assigned to property supplierId
	 */
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * Getter method for property <tt>channelId</tt>.
	 * 
	 * @return property value of channelId
	 */
	public long getChannelId() {
		return channelId;
	}

	/**
	 * Setter method for property <tt>channelId</tt>.
	 * 
	 * @param channelId value to be assigned to property channelId
	 */
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	/**
	 * Getter method for property <tt>vourType</tt>.
	 * 
	 * @return property value of vourType
	 */
	public int getVourType() {
		return vourType;
	}

	/**
	 * Setter method for property <tt>vourType</tt>.
	 * 
	 * @param vourType value to be assigned to property vourType
	 */
	public void setVourType(int vourType) {
		this.vourType = vourType;
	}

	public int getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(int verificationType) {
		this.verificationType = verificationType;
	}

	public int getVerificationVourType() {
		return verificationVourType;
	}

	public void setVerificationVourType(int verificationVourType) {
		this.verificationVourType = verificationVourType;
	}

	/**
	 * Getter method for property <tt>isDock</tt>.
	 * 
	 * @return property value of isDock
	 */
	public int getIsDock() {
		return isDock;
	}

	/**
	 * Setter method for property <tt>isDock</tt>.
	 * 
	 * @param isDock value to be assigned to property isDock
	 */
	public void setIsDock(int isDock) {
		this.isDock = isDock;
	}

	/**
	 * Getter method for property <tt>needConfirm</tt>.
	 * 
	 * @return property value of needConfirm
	 */
	public int getNeedConfirm() {
		return needConfirm;
	}

	public boolean getIsPlayTime() {
		return isPlayTime;
	}

	public void setIsPlayTime(boolean isPlayTime) {
		this.isPlayTime = isPlayTime;
	}

	/**
	 * Setter method for property <tt>isPlayTime</tt>.
	 * 
	 * @param isPlayTime value to be assigned to property isPlayTime
	 */
	public void setPlayTime(boolean isPlayTime) {
		this.isPlayTime = isPlayTime;
	}

	/**
	 * Getter method for property <tt>expireTime</tt>.
	 * 
	 * @return property value of expireTime
	 */
	public Date getExpireTime() {
		return expireTime;
	}

	/**
	 * Setter method for property <tt>expireTime</tt>.
	 * 
	 * @param expireTime value to be assigned to property expireTime
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * Getter method for property <tt>useStartDate</tt>.
	 * 
	 * @return property value of useStartDate
	 */
	public Date getUseStartDate() {
		return useStartDate;
	}

	/**
	 * Setter method for property <tt>useStartDate</tt>.
	 * 
	 * @param useStartDate value to be assigned to property useStartDate
	 */
	public void setUseStartDate(Date useStartDate) {
		this.useStartDate = useStartDate;
	}

	/**
	 * Getter method for property <tt>useEndDate</tt>.
	 * 
	 * @return property value of useEndDate
	 */
	public Date getUseEndDate() {
		return useEndDate;
	}

	/**
	 * Setter method for property <tt>useEndDate</tt>.
	 * 
	 * @param useEndDate value to be assigned to property useEndDate
	 */
	public void setUseEndDate(Date useEndDate) {
		this.useEndDate = useEndDate;
	}

	/**
	 * Getter method for property <tt>saleStartDate</tt>.
	 * 
	 * @return property value of saleStartDate
	 */
	public Date getSaleStartDate() {
		return saleStartDate;
	}

	/**
	 * Setter method for property <tt>saleStartDate</tt>.
	 * 
	 * @param saleStartDate value to be assigned to property saleStartDate
	 */
	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}

	/**
	 * Getter method for property <tt>saleEndDate</tt>.
	 * 
	 * @return property value of saleEndDate
	 */
	public Date getSaleEndDate() {
		return saleEndDate;
	}

	/**
	 * Setter method for property <tt>saleEndDate</tt>.
	 * 
	 * @param saleEndDate value to be assigned to property saleEndDate
	 */
	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	/**
	 * Getter method for property <tt>playDate</tt>.
	 * 
	 * @return property value of playDate
	 */
	public Date getPlayDate() {
		return playDate;
	}

	/**
	 * Setter method for property <tt>playDate</tt>.
	 * 
	 * @param playDate value to be assigned to property playDate
	 */
	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}

	/**
	 * Getter method for property <tt>strategys</tt>.
	 * 
	 * @return property value of strategys
	 */
	public List<StrategyBaseModel> getStrategys() {
		return strategys;
	}

	/**
	 * Setter method for property <tt>strategys</tt>.
	 * 
	 * @param strategys value to be assigned to property strategys
	 */
	public void setStrategys(List<StrategyBaseModel> strategys) {
		this.strategys = strategys;
	}

	/**
	 * Setter method for property <tt>needConfirm</tt>.
	 * 
	 * @param needConfirm value to be assigned to property needConfirm
	 */
	public void setNeedConfirm(int needConfirm) {
		this.needConfirm = needConfirm;
	}

	/**
	 * Getter method for property <tt>bookType</tt>.
	 * 
	 * @return property value of bookType
	 */
	public double getBookType() {
		return bookType;
	}

	/**
	 * Setter method for property <tt>bookType</tt>.
	 * 
	 * @param bookType value to be assigned to property bookType
	 */
	public void setBookType(double bookType) {
		this.bookType = bookType;
	}

	/**
	 * Getter method for property <tt>gainLimitModel</tt>.
	 * 
	 * @return property value of gainLimitModel
	 */
	public GainLimitModel getGainLimitModel() {
		return gainLimitModel;
	}

	/**
	 * Setter method for property <tt>gainLimitModel</tt>.
	 * 
	 * @param gainLimitModel value to be assigned to property gainLimitModel
	 */
	public void setGainLimitModel(GainLimitModel gainLimitModel) {
		this.gainLimitModel = gainLimitModel;
	}

	/**
	 * Getter method for property <tt>checkinPointModels</tt>.
	 * 
	 * @return property value of checkinPointModels
	 */
	public List<CheckinPointModel> getCheckinPointModels() {
		return checkinPointModels;
	}

	/**
	 * Setter method for property <tt>checkinPointModels</tt>.
	 * 
	 * @param checkinPointModels value to be assigned to property checkinPointModels
	 */
	public void setCheckinPointModels(List<CheckinPointModel> checkinPointModels) {
		this.checkinPointModels = checkinPointModels;
	}

	/**
	 * Getter method for property <tt>show_start_time</tt>.
	 * 
	 * @return property value of show_start_time
	 */
	public Date getShow_start_time() {
		return show_start_time;
	}

	/**
	 * Setter method for property <tt>show_start_time</tt>.
	 * 
	 * @param show_start_time value to be assigned to property show_start_time
	 */
	public void setShow_start_time(Date show_start_time) {
		this.show_start_time = show_start_time;
	}

	/**
	 * Getter method for property <tt>show_end_time</tt>.
	 * 
	 * @return property value of show_end_time
	 */
	public Date getShow_end_time() {
		return show_end_time;
	}

	/**
	 * Setter method for property <tt>show_end_time</tt>.
	 * 
	 * @param show_end_time value to be assigned to property show_end_time
	 */
	public void setShow_end_time(Date show_end_time) {
		this.show_end_time = show_end_time;
	}

	/**
	 * Getter method for property <tt>autoConfirm</tt>.
	 * 
	 * @return property value of autoConfirm
	 */
	public int getAutoConfirm() {
		return autoConfirm;
	}

	/**
	 * Setter method for property <tt>autoConfirm</tt>.
	 * 
	 * @param autoConfirm value to be assigned to property autoConfirm
	 */
	public void setAutoConfirm(int autoConfirm) {
		this.autoConfirm = autoConfirm;
	}

	/**
	 * Getter method for property <tt>stock_rule_id</tt>.
	 * 
	 * @return property value of stock_rule_id
	 */
	public long getStock_rule_id() {
		return stock_rule_id;
	}

	/**
	 * Setter method for property <tt>stock_rule_id</tt>.
	 * 
	 * @param stock_rule_id value to be assigned to property stock_rule_id
	 */
	public void setStock_rule_id(long stock_rule_id) {
		this.stock_rule_id = stock_rule_id;
	}

	/**
	 * Getter method for property <tt>region</tt>.
	 * 
	 * @return property value of region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Setter method for property <tt>region</tt>.
	 * 
	 * @param region value to be assigned to property region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Getter method for property <tt>ronda</tt>.
	 * 
	 * @return property value of ronda
	 */
	public String getRonda() {
		return ronda;
	}

	/**
	 * Setter method for property <tt>ronda</tt>.
	 * 
	 * @param ronda value to be assigned to property ronda
	 */
	public void setRonda(String ronda) {
		this.ronda = ronda;
	}

	/**
	 * Getter method for property <tt>seats</tt>.
	 * 
	 * @return property value of seats
	 */
	public List<Long> getSeats() {
		return seats;
	}

	/**
	 * Setter method for property <tt>seats</tt>.
	 * 
	 * @param seats value to be assigned to property seats
	 */
	public void setSeats(List<Long> seats) {
		this.seats = seats;
	}

	/**
	 * Getter method for property <tt>pay_way</tt>.
	 * 
	 * @return property value of pay_way
	 */
	public int getPay_way() {
		return pay_way;
	}

	/**
	 * Setter method for property <tt>pay_way</tt>.
	 * 
	 * @param pay_way value to be assigned to property pay_way
	 */
	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}

	/**
	 * Getter method for property <tt>passengerType</tt>.
	 * 
	 * @return property value of passengerType
	 */
	public int getPassengerType() {
		return passengerType;
	}

	/**
	 * Setter method for property <tt>passengerType</tt>.
	 * 
	 * @param passengerType value to be assigned to property passengerType
	 */
	public void setPassengerType(int passengerType) {
		this.passengerType = passengerType;
	}

	/**
	 * Getter method for property <tt>scenicId</tt>.
	 * 
	 * @return property value of scenicId
	 */
	public long getScenicId() {
		return scenicId;
	}

	/**
	 * Setter method for property <tt>scenicId</tt>.
	 * 
	 * @param scenicId value to be assigned to property scenicId
	 */
	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	public List<Long> getCheckinPointIds() {
		return checkinPointIds;
	}

	public void setCheckinPointIds(List<Long> checkinPointIds) {
		this.checkinPointIds = checkinPointIds;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MerchBaseModel [prodId=");
		builder.append(prodId);
		builder.append(", buyNum=");
		builder.append(buyNum);
		builder.append(", prodName=");
		builder.append(prodName);
		builder.append(", skuName=");
		builder.append(skuName);
		builder.append(", prodType=");
		builder.append(prodType);
		builder.append(", prodVarie=");
		builder.append(prodVarie);
		builder.append(", price=");
		builder.append(price);
		builder.append(", supplierId=");
		builder.append(supplierId);
		builder.append(", channelId=");
		builder.append(channelId);
		builder.append(", vourType=");
		builder.append(vourType);
		builder.append(", isDock=");
		builder.append(isDock);
		builder.append(", needConfirm=");
		builder.append(needConfirm);
		builder.append(", isPlayTime=");
		builder.append(isPlayTime);
		builder.append(", expireTime=");
		builder.append(expireTime);
		builder.append(", useStartDate=");
		builder.append(useStartDate);
		builder.append(", useEndDate=");
		builder.append(useEndDate);
		builder.append(", saleStartDate=");
		builder.append(saleStartDate);
		builder.append(", saleEndDate=");
		builder.append(saleEndDate);
		builder.append(", playDate=");
		builder.append(playDate);
		builder.append(", strategys=");
		builder.append(strategys);
		builder.append("]");
		return builder.toString();
	}

}
