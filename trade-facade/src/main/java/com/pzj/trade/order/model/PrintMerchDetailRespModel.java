package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印门票结果商品模型
 * @author GLG
 */
public class PrintMerchDetailRespModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/**商品id	  */
	private String merchId;
	/**商品名称	  */
	private String merchName;
	/**产品id  */
	private long productId;
	/** 商品总数 */
	private int totalNum;
	/**出游日期  */
	private Date travelTime;
	/** 场次 */
	private String screening;
	/**区域  */
	private String region;
	/** 座位 */
	private String seats;
	/**二维码  */
	private String qrCode;

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public Date getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Date travelTime) {
		this.travelTime = travelTime;
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

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PrintMerchDetailRespModel [merchId=");
		builder.append(merchId);
		builder.append(", merchName=");
		builder.append(merchName);
		builder.append(", productId=");
		builder.append(productId);
		builder.append(", totalNum=");
		builder.append(totalNum);
		builder.append(", travelTime=");
		builder.append(travelTime);
		builder.append(", screening=");
		builder.append(screening);
		builder.append(", region=");
		builder.append(region);
		builder.append(", seats=");
		builder.append(seats);
		builder.append(", qrCode=");
		builder.append(qrCode);
		builder.append("]");
		return builder.toString();
	}

}
