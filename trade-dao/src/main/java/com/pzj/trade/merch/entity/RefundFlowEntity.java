package com.pzj.trade.merch.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品退款信息操作dao的entity
 *
 * @author kangzl
 *
 */
public class RefundFlowEntity implements Serializable {
	private static final long serialVersionUID = 5590504324467859290L;

	private Long flow_id;

	private String refund_id;

	private String order_id;

	private String merch_id;
	
	/**退款申请时的商品状态*/
	private int apply_merch_status;

	/** 退款商品数量 */
	private Integer refund_num;

	/** 退款金额，单价 */
	private Double refund_price;

	/**
	 * 退款类型
	 * 
	 * <li>1,供应商</li>
	 * <li>2,分销商</li>
	 */
	@Deprecated
	private Integer refund_type=1;

	private Date update_time;

	private Date create_time;

	/**
	 * <li>0,未使用规则</li>
	 * <li>1,时间点前规则</li>
	 * <li>2,时间点后规则</li>
	 */
	private int refund_rule_type;

	public Long getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(final Long flow_id) {
		this.flow_id = flow_id;
	}

	public String getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(final String refund_id) {
		this.refund_id = refund_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(final String order_id) {
		this.order_id = order_id;
	}

	public String getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(final String merch_id) {
		this.merch_id = merch_id;
	}

	public Integer getRefund_num() {
		return refund_num;
	}

	public void setRefund_num(final Integer refund_num) {
		this.refund_num = refund_num;
	}

	public Double getRefund_price() {
		return refund_price;
	}

	public void setRefund_price(final Double refund_price) {
		this.refund_price = refund_price;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(final Date update_time) {
		this.update_time = update_time;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(final Date create_time) {
		this.create_time = create_time;
	}

	@Deprecated
	public Integer getRefund_type() {
		return refund_type;
	}

	@Deprecated
	public void setRefund_type(final Integer refund_type) {
		this.refund_type = refund_type;
	}

	public int getRefund_rule_type() {
		return refund_rule_type;
	}

	public void setRefund_rule_type(final int refund_rule_type) {
		this.refund_rule_type = refund_rule_type;
	}

	/**
	 * 根据退款类型过滤退款流水
	 * 
	 * @param refundFlows
	 *            退款流水
	 * @param refundType
	 *            退款类型
	 * @return
	 */
	public static List<RefundFlowEntity> filterFlows(final List<RefundFlowEntity> refundFlows, final int refundType) {
		final List<RefundFlowEntity> filterCache = new ArrayList<RefundFlowEntity>();
		for (final RefundFlowEntity flow : refundFlows) {
			if (flow.getRefund_type() == refundType) {
				filterCache.add(flow);
			}
		}
		return filterCache;
	}

	public int getApply_merch_status() {
		return apply_merch_status;
	}

	public void setApply_merch_status(int apply_merch_status) {
		this.apply_merch_status = apply_merch_status;
	}

}
