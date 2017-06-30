package com.pzj.trade.refund.service;

import java.util.List;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.order.vo.RefundMerchandiseVO;
import com.pzj.trade.order.vo.RefundReqVo;

/**
 * 退款服务接口(待废弃)
 * 
 * @author kangzl
 *
 */
public interface RefundService {

	/**
	 * 退款审核通过接口
	 * 
	 * @param vo
	 * @param context
	 * @return
	 */
	@Deprecated
	Result<Boolean> refundConfirm(RefundReqVo vo, ServiceContext context);

	/**
	 * 退款回滚接口
	 * 
	 * @param vo
	 * @param context
	 * @return
	 */
	@Deprecated
	Result<Boolean> refundRollback(RefundReqVo vo, ServiceContext context);

	/**
	 * 退款已消费的商品,只提供支撑平台调用
	 * 
	 * @return
	 */
	@Deprecated
	Result<Boolean> refundConsumerMerch(String orderId, List<RefundMerchandiseVO> refundMerchandiseList, ServiceContext context);

	/**
	 * 订单全额退款
	 * 
	 * @param orderId
	 *            订单ID
	 * @param refundId
	 *            退款单号
	 * @return
	 */
	@Deprecated
	Result<Boolean> refundMoney(String orderId, ServiceContext context);

	/**
	 * 订单部分退款
	 * 
	 * @param orderId
	 *            订单ID
	 * @param refundMerchandiseList
	 *            退款商品集合
	 * @return
	 */
	@Deprecated
	Result<Boolean> refundMoney(String orderId, List<RefundMerchandiseVO> refundMerchandiseList, ServiceContext context);
}
