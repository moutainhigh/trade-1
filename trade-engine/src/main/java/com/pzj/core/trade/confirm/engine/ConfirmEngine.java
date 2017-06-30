package com.pzj.core.trade.confirm.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.confirm.common.ConfirmTypeEnum;
import com.pzj.core.trade.confirm.common.ConfirmVersionEnum;
import com.pzj.core.trade.confirm.common.MfCodeStateEnum;
import com.pzj.core.trade.confirm.event.CheckDeliveryInfoEvent;
import com.pzj.core.trade.confirm.event.GetUsableVoucherEvent;
import com.pzj.core.trade.confirm.event.MerchConfirmLegalCheckEvent;
import com.pzj.core.trade.confirm.event.TicketPointVerifyEvent;
import com.pzj.core.trade.voucher.entity.VourEntity;
import com.pzj.core.trade.voucher.read.VoucherConfirmReadMapper;
import com.pzj.core.trade.voucher.write.VoucherConfirmWriteMapper;
import com.pzj.core.trade.voucher.write.VoucherWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.confirm.entity.ConfirmCodeEntity;
import com.pzj.trade.confirm.model.ConfirmModel;
import com.pzj.trade.confirm.write.ConfirmCodeWriteMapper;
import com.pzj.trade.merch.common.VourTypeEnum;
import com.pzj.trade.merch.entity.ConfirmBatchEntity;
import com.pzj.trade.merch.entity.ConfirmMerchEntity;
import com.pzj.trade.merch.entity.MerchNumEntity;
import com.pzj.trade.merch.write.MerchWriteMapper;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.DeliveryDetailEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.entity.OrderNumEntity;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;
import com.pzj.trade.order.write.DeliveryWriteMapper;
import com.pzj.trade.order.write.OrderWriteMapper;
import com.pzj.trade.withdraw.engine.TakenOrderEngine;
import com.pzj.voucher.common.VoucherStateEnum;
import com.pzj.voucher.entity.VoucherConfirm;

/**
 * 商品核销执行引擎.
 * @author YRJ
 * @author DongChunfu
 */
@Component(value = "confirmEngine")
public class ConfirmEngine {

	private final static Logger logger = LoggerFactory.getLogger(ConfirmEngine.class);

	/**特殊检票点，不做检票点校验限制*/
	private final static long SPECIAL_TICKET_POINT = -999;

	@Autowired
	private MerchWriteMapper merchWriteMapper;

	@Autowired
	private MerchReadMapper merchReadMapper;

	@Autowired
	private OrderWriteMapper orderWriteMapper;

	@Autowired
	private VoucherWriteMapper voucherWriteMapper;

	@Autowired
	private ConfirmCodeWriteMapper confirmCodeWriteMapper;

	@Autowired
	private OrderReadMapper orderReadMapper;

	@Autowired
	private DeliveryWriteMapper deliveryWriteMapper;

	@Resource(name = "merchConfirmLegalCheckEvent")
	private MerchConfirmLegalCheckEvent merchConfirmLegalCheckEvent;

	@Resource(name = "takenOrderEngine")
	private TakenOrderEngine takenOrderEngine;

	@Resource(name = "getUsableVoucherEvent")
	private GetUsableVoucherEvent getUsableVoucherEvent;

	@Autowired
	private VoucherConfirmReadMapper voucherConfirmReadMapper;

	@Resource(name = "ticketPointVerifyEvent")
	private TicketPointVerifyEvent ticketPointVerifyEvent;

	@Autowired
	private VoucherConfirmWriteMapper voucherConfirmWriteMapper;

	@Resource(name = "checkDeliveryInfoEvent")
	private CheckDeliveryInfoEvent checkDeliveryInfoEvent;

	@Transactional(value = "trade.transactionManager")
	public Result<VourEntity> confirm(final ConfirmModel reqModel) {

		//1： 查询待核销商品信息
		final long voucherId = reqModel.getVoucherId();
		final List<ConfirmMerchEntity> confirmMerches = merchWriteMapper.queryConfirmMerch((String) null, voucherId);

		//2： 根据商品状态判断是否支持核销
		merchConfirmLegalCheckEvent.isLegal(confirmMerches, voucherId);
		logger.info("voucher[{}], waiting confirm merches:{} ", voucherId, confirmMerches);

		//3：验证特产类商品配送信息
		final int merchType = confirmMerches.get(0).getMerch_type();
		checkDeliveryInfoEvent.check(reqModel.getDeliveryDetail(), merchType);

		//4:根据交易版本，核销类型获取可用的凭证
		final int tradeVersion = confirmMerches.get(0).getVersion();// 新老订单版本
		final int confirmType = reqModel.getType();
		final VourEntity voucher = getUsableVoucherEvent.getUseableVoucher(confirmType, voucherId, tradeVersion,
				confirmMerches);

		//5： 验证检票点合法性
		final List<VoucherConfirm> voucherConfirms = voucherConfirmReadMapper.queryVoucherConfimList(voucherId);
		final long ticketPoint = reqModel.getTicketPoint();
		ticketPointVerifyEvent.verify(voucher, confirmType, ticketPoint, voucherConfirms);

		final Date checkTime = confirmType == ConfirmTypeEnum.AUTOMATIC.getType()
				? new Date(voucher.getExpire_time().getTime() - 1 * 1000) : new Date();

		// 4.1:容错处理(已核销的商品返回成功)
		if (voucher.getVoucher_state() == VoucherStateEnum.VERIFICATION.getValue()) {
			if (!Check.NuNCollections(voucherConfirms)) {
				final Long checkPoint = isSpecialTicketPoint(ticketPoint) ? null : ticketPoint;
				voucherConfirmWriteMapper.updateVoucherConfirmUsedTimes(voucherId, null, checkPoint, 1, checkTime);
			}
			return new Result<VourEntity>(voucher);
		}

		//6:支持多次核销操作
		if (needConfirmMerch(voucherConfirms)) {
			voucher.setVersion(tradeVersion);
			final ConfirmIdentifying ci = updateConfirmData(reqModel, confirmMerches, voucher, voucherConfirms, checkTime);
			voucher.setNeedAccoutHandel(ci.isNeedAccoutHandel());
			voucher.setCheckedTime(checkTime);
			voucher.setCheckedNum(ci.getCheckedNum());
		}

		if (!Check.NuNCollections(voucherConfirms)) {
			final Long checkPoint = isSpecialTicketPoint(ticketPoint) ? null : ticketPoint;
			voucherConfirmWriteMapper.updateVoucherConfirmUsedTimes(voucherId, null, checkPoint, 1, checkTime);
		}

		return new Result<VourEntity>(voucher);
	}

	public OrderNumEntity getOrderConfirmInfo(final String transactionId) {
		return orderReadMapper.getOrderNumInfo(transactionId);
	}

	/**
	 * 根据订单ID查找需要核销的凭证信息
	 *
	 * @param orderIds
	 * @return
	 */
	public List<ConfirmBatchEntity> queryConfirmBatchRequireParam(final List<String> orderIds) {
		return merchReadMapper.getConfirmBatchRequireParam(orderIds);
	}

	private boolean isSpecialTicketPoint(final long ticketPoint) {
		return ticketPoint == SPECIAL_TICKET_POINT;
	}

	/**
	 * 是否需要唤醒商品核销动作
	 * @param confirmEntities 检票点信息
	 * @return
	 */
	private boolean needConfirmMerch(final List<VoucherConfirm> voucherConfirms) {
		if (Check.NuNCollections(voucherConfirms)) {
			return Boolean.TRUE;
		}

		for (final VoucherConfirm confirmEntity : voucherConfirms) {
			//当且仅当, 一次都没有核销过才唤醒商品核销动作.
			if (confirmEntity.getUsedTimes() > 0) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * 生成核销码参数.
	 * @param merch
	 * @param codes
	 */
	private void generateConfirmCodeEntity(final ConfirmMerchEntity merch, final List<ConfirmCodeEntity> codes,
			final Date checkTime, final VourEntity voucher) {
		if (VourTypeEnum.MFCODE.getVourType() == merch.getVour_type()) {
			final ConfirmCodeEntity confirmCode = new ConfirmCodeEntity();
			confirmCode.setOrder_id(merch.getOrder_id());
			confirmCode.setMerch_id(merch.getMerch_id());
			confirmCode.setMf_code(voucher.getVoucher_content());
			confirmCode.setCode_state(MfCodeStateEnum.CONFIRMED.getState());
			confirmCode.setCheckTime(checkTime);
			codes.add(confirmCode);
		}
	}

	/**
	 * 生成商品核销数量实体
	 * @param merch 待核销商品
	 * @return 核销数量实体
	 */
	private MerchNumEntity generateMerchNumEntity(final ConfirmMerchEntity merch) {
		final MerchNumEntity merchNum = new MerchNumEntity();
		merchNum.setMerchId(merch.getMerch_id());
		if (merch.getMerch_state() != MerchStateEnum.REFUNDED.getState()) {
			merchNum.setMerchState(MerchStateEnum.CONSUMED.getState());
		}
		merchNum.setTotalNum(merch.getTotal_num());
		merchNum.setCheckNum(merch.getTotal_num() - merch.getRefund_num());
		merchNum.setRefundNum(merch.getRefund_num());
		return merchNum;
	}

	/**
	 * 核销后更新商品、订单、配送信息、魔方码等状态
	 *
	 * @param reqModel 核销请求参数
	 * @param waitingConfirmMerches 待核销商品集合
	 * @param voucher 凭证信息
	 * @return
	 */
	private ConfirmIdentifying updateConfirmData(final ConfirmModel reqModel,
			final List<ConfirmMerchEntity> waitingConfirmMerches, final VourEntity voucher,
			final List<VoucherConfirm> voucherConfirms, final Date checkTime) {

		final List<MerchNumEntity> merchNums = new ArrayList<MerchNumEntity>();
		// 老版本订单数据修改信息以订单维度修改
		final Map<String, OrderEntity> oldOrderCache = new HashMap<String, OrderEntity>();//
		final OrderEntity newOrderCache = new OrderEntity();
		final List<ConfirmCodeEntity> codes = new ArrayList<ConfirmCodeEntity>();

		reqModel.getType();

		for (final ConfirmMerchEntity merch : waitingConfirmMerches) {
			final MerchNumEntity merchNum = generateMerchNumEntity(merch);
			if (merchNum.getCheckNum() <= 0) {
				continue;
			}
			final int merchState = voucher.getVersion() == ConfirmVersionEnum.NEW.getVersion()
					? MerchStateEnum.FINISHED.getState() : MerchStateEnum.CONSUMED.getState();
			merchNum.setMerchState(merchState);
			merchNum.setCheckTime(checkTime);
			merchNums.add(merchNum);
			// 生成老版本订单数据
			generateOldOrderEntity(merch, merchNum, oldOrderCache);
			// 生成新版本订单数据
			generateNewOrderEntity(merch, merchNum, newOrderCache);

			generateConfirmCodeEntity(merch, codes, checkTime, voucher);
		}

		// 1:根据merchId更新商品数量,状态及核销时间
		if (!Check.NuNCollections(merchNums)) {
			logger.debug("update merches info after confirm,voucherId:{}", voucher.getVoucher_id());
			merchWriteMapper.updateMerchStatusAndNumByMerchId(merchNums);
		}

		final ConfirmIdentifying ci = updateOrderData(oldOrderCache, newOrderCache, voucher.getVersion(),
				voucher.getTransaction_id(), checkTime);
		// 保存订单的配送信息（如果需要的话）
		final int merchType = waitingConfirmMerches.get(0).getMerch_type();
		if (checkDeliveryInfoEvent.isDeliveryMerch(merchType)) {
			final DeliveryDetailEntity deliveryDetailEntity = new DeliveryDetailEntity();
			BeanUtils.copyProperties(reqModel.getDeliveryDetail(), deliveryDetailEntity);
			deliveryWriteMapper.insertDeliveryDetail(deliveryDetailEntity);
		}

		if (!codes.isEmpty()) {
			confirmCodeWriteMapper.updateConfirmCodeStateByOrderAndMerchId(codes);
		}

		voucherWriteMapper.updateVouchConfirmStatusById(voucher.getVoucher_id(), VoucherStateEnum.VERIFICATION.getValue(),
				checkTime);

		// saas订单核销完毕，新增可提现记录
		if (ci.isNeedAccoutHandel()) {
			takenOrderEngine.insertDrawing(voucher.getTransaction_id());
		}
		return ci;
	}

	/**
	 * 生成订单在核销中需要修订的相关属性
	 * @param ordermap
	 * @param merch
	 * @param merchNum
	 */
	private void generateOldOrderEntity(final ConfirmMerchEntity merch, final MerchNumEntity merchNum,
			final Map<String, OrderEntity> orderCache) {

		final String orderId = merch.getOrder_id();
		OrderEntity orderEntity = orderCache.get("orderId");
		if (Check.NuNObject(orderEntity)) {
			orderEntity = new OrderEntity();
			orderCache.put(orderId, orderEntity);
		}
		orderEntity.setOrder_id(orderId);
		orderEntity.setTransaction_id(merch.getTransaction_id());
		final int checkNum = merchNum.getCheckNum();
		final double settlePrice = merch.getSettlement_price();
		orderEntity.setChecked_num(orderEntity.getChecked_num() + checkNum);
		orderEntity.setSettlement_price(orderEntity.getSettlement_price() + checkNum * settlePrice);
	}

	/**
	 * 生成订单在核销中需要修订的相关属性
	 * @param ordermap
	 * @param merch
	 * @param merchNum
	 */
	private void generateNewOrderEntity(final ConfirmMerchEntity merch, final MerchNumEntity merchNum,
			final OrderEntity newOrderCache) {
		final String transactionId = merch.getTransaction_id();
		newOrderCache.setTransaction_id(transactionId);
		final int checkNum = merchNum.getCheckNum();
		newOrderCache.setChecked_num(newOrderCache.getChecked_num() + checkNum);
	}

	private ConfirmIdentifying updateOrderData(final Map<String, OrderEntity> orderCache, final OrderEntity newOrderCache,
			final int version, final String transactionId, final Date checkTime) {
		final ConfirmIdentifying ci = new ConfirmIdentifying();

		final OrderNumEntity orderEntity = orderWriteMapper.getOrderNumEntityForUpdate(transactionId);
		if (version == ConfirmVersionEnum.OLD.getVersion()) {
			final ArrayList<OrderEntity> orderCacheValues = new ArrayList<>(orderCache.values());
			orderWriteMapper.updateOrderOfConsumer(orderCacheValues);
			ci.setNeedAccoutHandel(true);

		} else {
			//1:查询当前凭证对应的一级订单
			if (orderEntity.isAllChecked(newOrderCache.getChecked_num())) {
				newOrderCache.setOrder_status(OrderStatusEnum.Finished.getValue());
				newOrderCache.setConfirm_time(checkTime);
				ci.setNeedAccoutHandel(true);
				ci.setCheckedNum(orderEntity.getCheckNum() + newOrderCache.getChecked_num());
			}

			orderWriteMapper.newOrderConfrimFinishUpdateData(newOrderCache);
		}
		return ci;
	}

	public class ConfirmIdentifying {
		private boolean needAccoutHandel = false;
		private int checkedNum;

		public ConfirmIdentifying() {
			super();
		}

		public boolean isNeedAccoutHandel() {
			return needAccoutHandel;
		}

		public void setNeedAccoutHandel(final boolean needAccoutHandel) {
			this.needAccoutHandel = needAccoutHandel;
		}

		public void setCheckedNum(final int checkedNum) {
			this.checkedNum = checkedNum;
		}

		public int getCheckedNum() {
			return checkedNum;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("ConfirmIdentifying [needAccoutHandel=");
			builder.append(needAccoutHandel);
			builder.append(", checkedNum=");
			builder.append(checkedNum);
			builder.append("]");
			return builder.toString();
		}
	}
}
