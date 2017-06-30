package com.pzj.trade.order.entity;

import java.util.List;

public class OrderPayEntity {
	/** 交易id */
	private String transactionId;
	/** 订单详情 */
	private List<PreOrder> childOrders;

	public final static class PreOrder {
		/** 订单 */
		private String orderId;
		/** 交易主体id */
		private long TransactionSubjectId;
		/** 交易对手id */
		private long CounterpartyId;
		/** 订单金额 */
		private double orderMoney;
		/** 货款支付金额 */
		private double partMoney;
		/** 订单级别 */
		private int orderLevel;

		/** 订单 */
		public String getOrderId() {
			return orderId;
		}

		/** 订单 */
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		/** 交易主体id */
		public long getTransactionSubjectId() {
			return TransactionSubjectId;
		}

		/** 交易主体id */
		public void setTransactionSubjectId(long transactionSubjectId) {
			TransactionSubjectId = transactionSubjectId;
		}

		/** 交易对手id */
		public long getCounterpartyId() {
			return CounterpartyId;
		}

		/** 交易对手id */
		public void setCounterpartyId(long counterpartyId) {
			CounterpartyId = counterpartyId;
		}

		/** 订单金额 */
		public double getOrderMoney() {
			return orderMoney;
		}

		/** 订单金额 */
		public void setOrderMoney(double orderMoney) {
			this.orderMoney = orderMoney;
		}

		/** 货款支付金额 */
		public double getPartMoney() {
			return partMoney;
		}

		/** 货款支付金额 */
		public void setPartMoney(double partMoney) {
			this.partMoney = partMoney;
		}

		/** 订单级别 */
		public int getOrderLevel() {
			return orderLevel;
		}

		/** 订单级别 */
		public void setOrderLevel(int orderLevel) {
			this.orderLevel = orderLevel;
		}

		@Override
		public String toString() {
			return "PreOrder [orderId=" + orderId + ", TransactionSubjectId=" + TransactionSubjectId
					+ ", CounterpartyId=" + CounterpartyId + ", orderMoney=" + orderMoney + ", partMoney=" + partMoney
					+ ", orderLevel=" + orderLevel + "]";
		}

	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public List<PreOrder> getChildOrders() {
		return childOrders;
	}

	public void setChildOrders(List<PreOrder> childOrders) {
		this.childOrders = childOrders;
	}

	@Override
	public String toString() {
		return "OrderPayEntity [transactionId=" + transactionId + ", childOrders=" + childOrders + "]";
	}

}
