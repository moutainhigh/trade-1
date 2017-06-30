package com.pzj.core.trade.refund.engine.model;

/**
 * 退款商品模型.
 *
 * @author YRJ
 *
 */
public class RefundMerchModel {

	private String merchId;

	private String rootMerchId;

	private String merchName;

	private long productId;

	private int merchState;

	private int totalNum;

	private int refundNum;

	private int checkedNum;

	private int applyNum;

	private double price;

	private double totalAmount;

	private long voucherId;

	private String orderId;

	private String pOrderId;

	private int isRefunding;

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(final String merchId) {
		this.merchId = merchId;
	}

	public String getRootMerchId() {
		return rootMerchId;
	}

	public void setRootMerchId(final String rootMerchId) {
		this.rootMerchId = rootMerchId;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(final String merchName) {
		this.merchName = merchName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(final long productId) {
		this.productId = productId;
	}

	public int getMerchState() {
		return merchState;
	}

	public void setMerchState(final int merchState) {
		this.merchState = merchState;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(final int totalNum) {
		this.totalNum = totalNum;
	}

	public int getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(final int refundNum) {
		this.refundNum = refundNum;
	}

	public int getCheckedNum() {
		return checkedNum;
	}

	public void setCheckedNum(final int checkedNum) {
		this.checkedNum = checkedNum;
	}

	public int getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(final int applyNum) {
		this.applyNum = applyNum;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	/**
	 * 普通可退数量.
	 *
	 * @return
	 */
	public int generalRefundavailable() {
		return totalNum - checkedNum - refundNum;
	}

	/**
	 * 普通已消费可退数量.
	 *
	 * @return
	 */
	public int generalConsumedRefundavailable() {
		return checkedNum;
	}

	/**
	 * 强制可退款数量
	 *
	 * @return
	 */
	public int forceRefundavailable() {
		return totalNum - refundNum;
	}

	/**
	 * 是否为部分退.
	 * <p>
	 * 当且仅当, 可退余量 > 0时, 代表为部分退.
	 * </p>
	 *
	 * @return
	 */
	public boolean portion() {
		return (generalRefundavailable() - applyNum) > 0;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(final double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(final long voucherId) {
		this.voucherId = voucherId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getpOrderId() {
		return pOrderId;
	}

	public void setpOrderId(final String pOrderId) {
		this.pOrderId = pOrderId;
	}

	public int getIsRefunding() {
		return isRefunding;
	}

	public void setIsRefunding(final int isRefunding) {
		this.isRefunding = isRefunding;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((merchId == null) ? 0 : merchId.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RefundMerchModel other = (RefundMerchModel) obj;
		if (merchId == null) {
			if (other.merchId != null) {
				return false;
			}
		}
		if (!merchId.equals(other.merchId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder tostr = new StringBuilder(RefundMerchModel.class.getSimpleName());
		tostr.append("[");
		tostr.append("merchId=").append(merchId);
		tostr.append(", productId=").append(productId);
		tostr.append(", orderId=").append(orderId);
		tostr.append(", voucherId=").append(voucherId);
		tostr.append(", merchState=").append(merchState);
		// tostr.append(", force=").append(force);
		tostr.append(", totalNum=").append(totalNum);
		tostr.append(", refundNum=").append(refundNum);
		tostr.append(", checkedNum=").append(checkedNum);
		tostr.append(", applyNum=").append(applyNum);
		tostr.append(", totalAmount=").append(totalAmount);
		tostr.append("]");
		return tostr.toString();
	}

	public static final RefundMerchModel newInstance() {
		return new RefundMerchModel();
	}
}
