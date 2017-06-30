package com.pzj.core.trade.confirm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.confirm.common.AutoConfirmEnum;
import com.pzj.core.trade.confirm.common.ConfirmVersionEnum;
import com.pzj.core.trade.confirm.engine.OverdueMerchQueryEngine;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.confirm.model.OverdueConfirmReqModel;
import com.pzj.trade.confirm.response.OverdueConfirmRespModel;
import com.pzj.trade.confirm.service.ConfirmQueryService;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.read.MerchReadMapper;

@Service("confirmQueryService")
public class ConfirmQueryServiceImpl implements ConfirmQueryService {

	private static final Logger logger = LoggerFactory.getLogger(ConfirmQueryServiceImpl.class);

	@Autowired
	private MerchReadMapper merchReadMapper;

	@Resource(name = "overdueMerchQueryEngine")
	private OverdueMerchQueryEngine overdueMerchQueryEngine;

	@Override
	public Result<ArrayList<Long>> queryNotConfirmMerchOfAck() {
		final List<Long> voucherlist = merchReadMapper.queryMerchVoucherForOverdueOfAck();
		final Result<ArrayList<Long>> result = new Result<ArrayList<Long>>();
		result.setData(new ArrayList<Long>(voucherlist));
		return result;
	}

	@Override
	public Result<OverdueConfirmRespModel> overdueMerches(final OverdueConfirmReqModel reqModel, final ServiceContext context) {
		if (Check.NuNObject(reqModel)) {
			return new Result<OverdueConfirmRespModel>(10801, "参数为空.");
		}
		final List<MerchEntity> overdueMerches = overdueMerchQueryEngine.doEngine();
		logger.debug("overdue merches in DB :{}.", overdueMerches);

		final OverdueConfirmRespModel canAutoConfirmMerches = filterCanAutoConfirmMerches(overdueMerches);
		return new Result<>(canAutoConfirmMerches);

	}

	private OverdueConfirmRespModel filterCanAutoConfirmMerches(final List<MerchEntity> overdueMerches) {

		final OverdueConfirmRespModel canAutoConfirmMerches = new OverdueConfirmRespModel();
		final Set<Long> voucherIds = new HashSet<Long>(4);

		for (final MerchEntity overdueMerch : overdueMerches) {
			final int version = overdueMerch.getVersion();
			overdueMerch.getAuto_confirm();

			if (ConfirmVersionEnum.NEW.getVersion() == version) {
				final int autoConfirm = overdueMerch.getAuto_confirm();
				if (AutoConfirmEnum.AUTOMATIC.getType() == autoConfirm) {
					voucherIds.add(overdueMerch.getVoucher_id());
				}
			}

			if (ConfirmVersionEnum.OLD.getVersion() == version) {
				voucherIds.add(overdueMerch.getVoucher_id());
			}
		}
		logger.debug("can auto confirm vouchers:{}.", voucherIds);
		if (!Check.NuNCollections(voucherIds)) {
			canAutoConfirmMerches.setVoucherIds(voucherIds);
		}
		return canAutoConfirmMerches;
	}
}
