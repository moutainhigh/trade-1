package com.pzj.core.trade.order.converter;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.exception.TradeException;
import com.pzj.core.trade.order.engine.common.TimeCalculator;
import com.pzj.core.trade.order.engine.event.VoucherQueryEvent;
import com.pzj.core.trade.query.entity.TicketSellerOrdersQueryModel;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.model.OperatorOrdersReqModel;

/**
 * 售票员订单列表查询参数转换器.
 * @author YRJ
 *
 */
@Component(value = "ticketSellerArgsValidator")
public class TicketSellerArgsConverter implements ObjectConverter<OperatorOrdersReqModel, Void, TicketSellerOrdersQueryModel> {

	@Resource(name = "voucherQueryEvent")
	private VoucherQueryEvent voucherQueryEvent;

	@Autowired
	private TimeCalculator timeCalculator;

	@Override
	public TicketSellerOrdersQueryModel convert(final OperatorOrdersReqModel reqModel, final Void y) {
		if (reqModel == null) {
			return null;
		}
		if (reqModel.getOperatorId() <= 0) {
			throw new TradeException(14001, "请填写售票员ID");
		}
		//1. 将查询条件转换为数据库查询模型.
		final TicketSellerOrdersQueryModel queryModel = convert(reqModel);

		{
			//2. 假定查询参数中存在二维码, 则获取对应的凭证信息.
			List<Long> voucherIds = null;
			if (!Check.NuNStrStrict(reqModel.getQrCode())) {
				voucherIds = voucherQueryEvent.queryVoucherByTypeAndStatus(reqModel.getQrCode());
			}
			queryModel.setVoucher_ids(voucherIds);
		}

		//orderForTicketSellerReadMapper.queryOrdersByScrollData(queryModel, reqModel.getPageIndex(), reqModel.getPageSize());
		return queryModel;
	}

	/**
	 * 售票员订单列表查询模型转换.
	 * @param reqModel
	 * @return
	 */
	private TicketSellerOrdersQueryModel convert(final OperatorOrdersReqModel reqModel) {
		final TicketSellerOrdersQueryModel queryModel = new TicketSellerOrdersQueryModel(reqModel.getTransactionId());
		queryModel.setOperator_id(reqModel.getOperatorId());
		queryModel.setOrder_status(reqModel.getOrderStatus());
		queryModel.setResellerIds(reqModel.getResellerIds());
		queryModel.setPay_way(reqModel.getPayWay());
		queryModel.setContact_mobile(reqModel.getContactMobile());
		queryModel.setContactee(reqModel.getContactee());
		queryModel.setGuide_ids(reqModel.getGuideIds());
		queryModel.setIdcard_no(reqModel.getIdcard());
		queryModel.setMerch_name(reqModel.getMerchName());
		queryModel.setMerch_state(reqModel.getMerchState());
		boolean isRefunding = !Check.NuNObject(reqModel.getMerchState())
				&& reqModel.getMerchState() == MerchStateEnum.REFUNDING.getState();
		if (isRefunding) {
			queryModel.setMerch_state(null);
			queryModel.setRefunding(true);
		}
		queryModel.setMerch_type(reqModel.getCategory());
		queryModel.setProduct_varie(reqModel.getProductVarie());
		{//根据下单日期计算查询条件
			final Date start_date = reqModel.getStartDate();
			if (!Check.NuNObject(start_date)) {
				final TimeCalculator.VisitTime startDate = timeCalculator.calculator(start_date);
				queryModel.setStart_date(startDate.getStartTime());
				queryModel.setEnd_date(startDate.getEndTime());
			}
			final Date end_date = reqModel.getEndDate();
			if (!Check.NuNObject(end_date)) {
				final TimeCalculator.VisitTime endDate = timeCalculator.calculator(end_date);
				queryModel.setEnd_date(endDate.getEndTime());
			}
		}
		{//根据出游日期计算查询条件
			queryModel.setStart_travel_time(reqModel.getTravelStartTime());
			queryModel.setEnd_travel_time(reqModel.getTravelEndTime());
		}

		return queryModel;
	}
}
