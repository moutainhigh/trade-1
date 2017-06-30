package com.pzj.core.trade.voucher.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.trade.order.engine.event.VoucherQueryEvent;
import com.pzj.framework.context.Result;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.toolkit.TimeHelper;
import com.pzj.voucher.common.ExecuteResult;
import com.pzj.voucher.engine.UsableVoucherQueryEvent;
import com.pzj.voucher.engine.VoucherBusiness;
import com.pzj.voucher.engine.VoucherExtendEngine;
import com.pzj.voucher.entity.ExtendVoucher;
import com.pzj.voucher.entity.VoucherEntity;
import com.pzj.voucher.entity.VoucherResponseModel;
import com.pzj.voucher.service.VoucherService;
import com.pzj.voucher.vo.InitVoucherVo;

@Service(value = "voucherService")
@Transactional(propagation = Propagation.SUPPORTS)
public class VoucherServiceImpl implements VoucherService {

	private final static Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

	@Autowired
	private VoucherBusiness voucherBusiness;

	@Resource(name = "voucherQueryEvent")
	private VoucherQueryEvent voucherQueryEvent;

	@Resource(name = "usableVoucherQueryEvent")
	private UsableVoucherQueryEvent usableVoucherQueryEvent;

	@Override
	@Deprecated
	public Result<VoucherEntity> createVoucher(final InitVoucherVo initVoucherVo) {
		return null;
	}

	@Override
	public ExecuteResult<List<VoucherEntity>> queryVoucherInfo(final Long supplierId, final String voucherContent) {
		logger.info("查询可用凭证参数, 供应商ID: [" + supplierId + "], 核销码: [" + voucherContent + "], 游玩日期: [" + TimeHelper.getTime(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "]");
		try {
			final List<VoucherEntity> vouchers = usableVoucherQueryEvent.queryUsableVoucher(supplierId, voucherContent);
			return new ExecuteResult<List<VoucherEntity>>(ExecuteResult.SUCCESS, "OK", vouchers);
		} catch (final Throwable e) {
			logger.error(
					"查询可用凭证失败, 供应商ID: [" + supplierId + "], 核销码: [" + voucherContent + "], 游玩日期: [" + TimeHelper.getTime(new Date(), "yyyy-MM-dd HH:mm:ss")
							+ "]", e);
			return new ExecuteResult<List<VoucherEntity>>(ExecuteResult.EXCEPTION, "凭证查询失败");
		}
	}

	@Override
	public ExecuteResult<List<VoucherEntity>> queryVoucherByParam(final VoucherEntity baseVoucher) {
		logger.info("查询凭证信息, voucherId: [" + baseVoucher.getVoucherId() + "]");
		List<VoucherEntity> vouchers = null;
		try {
			vouchers = this.voucherBusiness.queryVoucherByParam(baseVoucher);
		} catch (final Throwable e) {
			logger.error("查询凭证信息失败, voucherId: [" + baseVoucher.getVoucherId() + "]", e);
			return new ExecuteResult<List<VoucherEntity>>(ExecuteResult.EXCEPTION, "凭证查询失败");
		}

		logger.info("查询凭证信息, voucherId: [" + baseVoucher.getVoucherId() + "], result: " + JSONConverter.toJson(vouchers));
		return new ExecuteResult<List<VoucherEntity>>(ExecuteResult.SUCCESS, "OK", vouchers);
	}

	@Override
	@Deprecated
	public ExecuteResult<VoucherEntity> updateVoucher(final Long voucherId, final String name, final String mobile, final String cardId) {
		return null;
	}

	@Resource(name = "voucherExtendEngine")
	private VoucherExtendEngine voucherExtendEngine;

	@Override
	public ExecuteResult<List<ExtendVoucher>> queryVoucherProductInfo(final Long voucherId) {
		final ExecuteResult<List<ExtendVoucher>> result = new ExecuteResult<List<ExtendVoucher>>(ExecuteResult.SUCCESS, "OK");
		try {
			final List<ExtendVoucher> extVours = voucherExtendEngine.queryExtendVoucherByVoucherId(voucherId);
			result.setData(extVours);
		} catch (final Exception e) {
			e.printStackTrace();
			result.setStateCode(ExecuteResult.EXCEPTION);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	@Deprecated
	public ExecuteResult<VoucherEntity> checkOneCardVoucherBatch(final Long productId, final Long supplierId, final List<String> voucherContentList,
			final Date date) {
		return new ExecuteResult<VoucherEntity>();
	}

	@Override
	@Deprecated
	public Result<ArrayList<String>> checkIdCardBuyable(final long prodId, final long supplierId, final List<String> idCards, final Date date) {
		return new Result<ArrayList<String>>();
	}

	@Override
	public ExecuteResult<VoucherResponseModel> queryVoucherBasicById(final Long voucherId) {
		if (voucherId == null || voucherId.intValue() < 1) {
			return new ExecuteResult<VoucherResponseModel>();
		}
		logger.info("查询凭证基本信息, voucherId: [" + voucherId + "]");
		try {
			final VoucherResponseModel respModel = voucherQueryEvent.queryBasicVoucherById(voucherId);
			if (respModel == null) {
				logger.warn("查询凭证基本信息失败, voucherId: [" + voucherId + "], 凭证不存在.");
			}

			return new ExecuteResult<VoucherResponseModel>(ExecuteResult.SUCCESS, "OK", respModel);
		} catch (final Throwable e) {
			logger.error("查询凭证基本信息失败, voucherId: [" + voucherId + "]", e);
			return new ExecuteResult<VoucherResponseModel>(ExecuteResult.EXCEPTION, "凭证查询失败.");
		}
	}

}
