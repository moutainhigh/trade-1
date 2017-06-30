package com.pzj.core.trade.refund.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.write.RefundApplyWriteMapper;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.merch.entity.RefundFlowEntity;
import com.pzj.trade.merch.write.MerchRefundFlowWriteMapper;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.mq.MQTagsEnum;
import com.pzj.trade.mq.MQUtil;

@Component
public class RefundApplyMQEngine {

	private static final Logger logger = LoggerFactory.getLogger(RefundApplyMQEngine.class);
	@Autowired
	private MerchRefundFlowWriteMapper merchRefundFlowWriteMapper;
	@Autowired
	private MerchWriteMapper merchWriteMapper;
	@Autowired
	private RefundApplyWriteMapper refundApplyWriteMapper;

	@Resource(name = "mqUtil")
	private MQUtil mqutil;

	public void sendRefundApplyMQ(String refundId, String saleOrderId) {

		MQRefundOrderModel orderModel = new MQRefundOrderModel();
		orderModel.setOrderId(saleOrderId);
		final List<RefundFlowEntity> refundFlows = merchRefundFlowWriteMapper.getOrderMerchRefund(saleOrderId, refundId);
		final RefundApplyEntity refundApply = refundApplyWriteMapper.getRefundApplyEntityByRefundId(refundId);
		orderModel.setRefundAuditStatus(refundApply.getRefundAuditState());
		orderModel.setApplierId(refundApply.getApplierId());
		orderModel.setRefundId(refundId);
		orderModel.setInitParty(refundApply.getInitParty());
		orderModel.setIsForce(refundApply.getIsForce());
		orderModel.setIsParty(refundApply.getIsParty());
		orderModel.setCreateTime(refundApply.getCreateTime().getTime());

		Map<String, RefundFlowEntity> flowsCache = new HashMap<String, RefundFlowEntity>();
		Map<Long, Set<String>> skuMerchCache = new HashMap<Long, Set<String>>();
		for (RefundFlowEntity flow : refundFlows) {
			flowsCache.put(flow.getMerch_id(), flow);
			MerchEntity merch = merchWriteMapper.getMerchByMerchId(flow.getMerch_id());
			if (Check.NuNCollections(skuMerchCache.get(merch.getProduct_id()))) {
				skuMerchCache.put(merch.getProduct_id(), new HashSet<String>());
			}
			skuMerchCache.get(merch.getProduct_id()).add(merch.getMerch_id());
		}
		for (Iterator<Entry<Long, Set<String>>> iterator = skuMerchCache.entrySet().iterator(); iterator.hasNext();) {
			Entry<Long, Set<String>> node = iterator.next();
			long skuId = node.getKey();
			int refundNum = 0;
			double refundPrice = 0;
			for (String merchId : node.getValue()) {
				RefundFlowEntity flow = flowsCache.get(merchId);
				refundNum += flow.getRefund_num();
				refundPrice = flow.getRefund_price();
			}
			MQRefundMerchModel merchModel = new MQRefundMerchModel();
			merchModel.setRefundNum(refundNum);
			merchModel.setRefundPrice(refundPrice);
			merchModel.setProductId(skuId);
			orderModel.getRefundMerches().add(merchModel);
		}
		String jsonStr = JSONConverter.toJson(orderModel);

		mqutil.send(MQTagsEnum.refundApply.getValue(), jsonStr);
	}

	@SuppressWarnings("unused")
	private static class MQRefundOrderModel {

		/*** 订单ID(分销商订单ID) */
		private String orderId;
		private List<MQRefundMerchModel> refundMerches = new ArrayList<MQRefundMerchModel>();
		/** * 退款标识*/
		private String refundId;
		/*** 退款申请人ID*/
		private long applierId;
		/** * 退款发起方、
		 * <li>1,分销商</li><li>2,大平台</li> <li>3,确认拒绝</li>*/
		private int initParty;
		/** 部分退款
		 *  <li>0,整单退</li> <li>1,部分退</li>
		 */
		private int isParty;
		/** 强制退款
		 * <li>0,普通退</li> <li>1,强制退</li>
		 */
		private int isForce;
		/** 退款审核状态
		 * <li>1,平台待审核</li> <li>2,财务待审核</li> <li>8,对接审核中</li><li>9,审核完毕</li> <li>7,拒绝退款</li>
		 */
		private int refundAuditStatus;
		/**
		 * 申请时间（毫秒）
		 */
		private long createTime;
		private List<MQRefundMerchModel> merches = new ArrayList<MQRefundMerchModel>();

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public List<MQRefundMerchModel> getRefundMerches() {
			return refundMerches;
		}

		public int getRefundAuditStatus() {
			return refundAuditStatus;
		}

		public void setRefundAuditStatus(int refundAuditStatus) {
			this.refundAuditStatus = refundAuditStatus;
		}

		public long getApplierId() {
			return applierId;
		}

		public void setApplierId(long applierId) {
			this.applierId = applierId;
		}

		public int getInitParty() {
			return initParty;
		}

		public void setInitParty(int initParty) {
			this.initParty = initParty;
		}

		public int getIsParty() {
			return isParty;
		}

		public void setIsParty(int isParty) {
			this.isParty = isParty;
		}

		public int getIsForce() {
			return isForce;
		}

		public void setIsForce(int isForce) {
			this.isForce = isForce;
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}

		public String getRefundId() {
			return refundId;
		}

		public void setRefundId(String refundId) {
			this.refundId = refundId;
		}

		public void setMerches(List<MQRefundMerchModel> merches) {
			this.merches = merches;
		}
	}

	@SuppressWarnings("unused")
	private static class MQRefundMerchModel {
		private long productId;
		private int refundNum;
		private double refundPrice;

		public long getProductId() {
			return productId;
		}

		public void setProductId(long productId) {
			this.productId = productId;
		}

		public int getRefundNum() {
			return refundNum;
		}

		public void setRefundNum(int refundNum) {
			this.refundNum = refundNum;
		}

		public double getRefundPrice() {
			return refundPrice;
		}

		public void setRefundPrice(double refundPrice) {
			this.refundPrice = refundPrice;
		}
	}
}
