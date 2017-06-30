package com.pzj.core.trade.refund.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.refund.entity.RefundApplyEntity;
import com.pzj.core.trade.refund.entity.RefundApplyQueryPageEntity;
import com.pzj.core.trade.refund.entity.RefundMerchRequiredEntity;
import com.pzj.core.trade.refund.model.RefundApplyQueryModel;

/**
 * 退款申请基础信息查询接口
 * 
 * @author DongChunfu
 * @date 2016年12月13日
 */
public interface RefundApplyReadMapper {

	/**
	 * 获取主订单对应的商品信息.
	 *
	 * @author DongChunfu
	 *
	 * @param orderId
	 *            销售订单ID
	 * @return 销售商品集
	 */
	List<RefundMerchRequiredEntity> getMerchsByOrderId(@Param("orderId") String orderId);

	/**
	 * 财务强制退款列表查询
	 * 
	 * @param apply
	 *            退款申请信息
	 * @return 退款ID集合
	 */
	List<String> pageQueryRefundApply(@Param(value = "apply") RefundApplyQueryPageEntity apply);

	/**
	 * 财务强制退款列表查询条数统计
	 * 
	 * @param apply
	 *            退款申请信息
	 * @return 总条数
	 */
	int countRefundApply(@Param(value = "apply") RefundApplyQueryPageEntity apply);

	/**
	 * 根据退款ID查询退款申请记录
	 * 
	 * @param refundId
	 *            退款ID
	 * @return
	 */
	RefundApplyEntity queryRefundApplyByRefundId(String refundId);

	/**
	 * 根据退款ID查询退款申请记录
	 * 
	 * @param refundId
	 *            退款ID
	 * @return
	 * @author guanluguang
	 */
	RefundApplyEntity queryRefundApplyByRefundIdByFlow(String refundId);

	/**
	 * 根据订单ID查询退款申请
	 * @param queryModel
	 * @return
	 */
	List<RefundApplyEntity> queryRefundApplyByOrderId(RefundApplyQueryModel queryModel);

	/**
	 * 根据merchId、refund_state查询退款数量
	 * 
	 * @return
	 * @author chaihuijian
	 */
	int queryRefundingNumByMerchId(@Param(value = "merch_id") String merch_id, @Param(value = "refund_state") int refund_state);
}
