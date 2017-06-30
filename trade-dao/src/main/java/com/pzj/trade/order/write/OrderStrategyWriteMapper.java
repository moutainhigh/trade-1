package com.pzj.trade.order.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.trade.order.entity.OrderStrategyEntity;
import com.pzj.trade.order.model.TransferredMerchStrategyModel;

public interface OrderStrategyWriteMapper {

	/**
	 * 保存政策.
	 * @param strategies
	 */
	@Deprecated
	void insertStrategy(@Param(value = "strategies") List<OrderStrategyEntity> strategies);

	/**saas1.1 新
	 * 保存政策.
	 * @param strategies
	 */
	void insertMultiStrategy(@Param(value = "strategies") List<OrderStrategyEntity> strategies);

	/**
	 * 修改政策信息中有关金额是到账的标识（后返）
	 * @param models
	 */
	void updateStrategyToAccountTransffered(@Param("models") List<TransferredMerchStrategyModel> models);

	OrderStrategyEntity getSingleOrderStrategy(@Param("orderId") String orderId, @Param("merchId") String merchId);
}
