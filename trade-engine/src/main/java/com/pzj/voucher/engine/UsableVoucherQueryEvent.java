package com.pzj.voucher.engine;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.confirm.exception.VoucherIsNotExitException;
import com.pzj.core.trade.voucher.entity.BasicQueryModel;
import com.pzj.core.trade.voucher.read.VoucherConfirmReadMapper;
import com.pzj.core.trade.voucher.read.VoucherReadMapper;
import com.pzj.framework.toolkit.TimeHelper;
import com.pzj.voucher.common.ExecuteResult;
import com.pzj.voucher.common.VoucherStateEnum;
import com.pzj.voucher.entity.VoucherConfirm;
import com.pzj.voucher.entity.VoucherEntity;

/**
 * 可用凭证的查询事件.
 * @author YRJ
 *
 */
@Component(value = "usableVoucherQueryEvent")
public class UsableVoucherQueryEvent {

	private final static Logger logger = LoggerFactory.getLogger(UsableVoucherQueryEvent.class);

	@Resource(name = "voucherReadMapper")
	private VoucherReadMapper voucherReadMapper;

	@Resource(name = "voucherConfirmReadMapper")
	private VoucherConfirmReadMapper voucherConfirmReadMapper;

	public List<VoucherEntity> queryUsableVoucher(final Long supplierId, final String voucherContent) {
		final BasicQueryModel queryModel = assembyQueryModel(supplierId, voucherContent);
		final List<VoucherEntity> vouchers = voucherReadMapper.queryUsableVoucherBySupplierId(queryModel);
		if (vouchers == null) {
			logger.warn("供应商[" + supplierId + "], 核销码[" + voucherContent + "], 游玩日期[" + TimeHelper.getTime(queryModel.getStart_time(), "yyyy-MM-dd HH:mm:ss")
					+ "]. 当前无可用的核销凭证.");
			throw new VoucherIsNotExitException(ExecuteResult.NOTFOUNDVOUCHER, "当前供应商没有可用的凭证");
		}

		final List<VoucherEntity> results = new ArrayList<VoucherEntity>();
		//因为没记录确认凭证记录的ID，暂时使用这种方式查询
		for (final VoucherEntity bv : vouchers) {
			if (!(bv.getVoucherState().intValue() == VoucherStateEnum.AVAILABLE.getValue() || bv.getVoucherState().intValue() == VoucherStateEnum.VERIFICATION
					.getValue())) {
				continue;
			}

			final List<VoucherConfirm> vcList = assembyVoucherConfirm(bv.getVoucherId(), bv.getVoucherState());
			bv.setVoucherConfirmList(vcList);
			results.add(bv);
		}
		return results;
	}

	/**
	 * 构建查询模型.
	 * @param supplierId
	 * @param voucherContent
	 * @return
	 */
	private BasicQueryModel assembyQueryModel(final Long supplierId, final String voucherContent) {
		final BasicQueryModel queryModel = new BasicQueryModel();
		queryModel.setSupplier_id(supplierId);
		queryModel.setVoucherContent(voucherContent);
		queryModel.setStart_time(new Timestamp(System.currentTimeMillis()));
		return queryModel;
	}

	private List<VoucherConfirm> assembyVoucherConfirm(final Long voucherId, final int voucherState) {
		final List<VoucherConfirm> vcList = this.voucherConfirmReadMapper.queryVoucherConfimList(voucherId);
		if (vcList == null) {
			return vcList;
		}
		if (voucherState == VoucherStateEnum.AVAILABLE.getValue()) {
			return vcList;
		}

		//如果是已核销就检查、confirm的检票次数(如果是-99 就是无限检票)
		boolean isAvailable = false; //判断confirm是否可用
		for (final VoucherConfirm vc : vcList) {
			if (vc.getMaxUseTimes().intValue() == -99 || vc.getUsedTimes().intValue() < vc.getMaxUseTimes().intValue()) {
				isAvailable = true;
			}
		}
		if (isAvailable) {
			return vcList;
		}
		return null;
	}
}
