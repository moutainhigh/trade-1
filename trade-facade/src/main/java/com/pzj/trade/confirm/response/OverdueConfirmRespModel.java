package com.pzj.trade.confirm.response;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 逾期核销响应模型.
 * @author YRJ
 *
 */
public class OverdueConfirmRespModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<Long> voucherIds = new HashSet<Long>(4);

	public OverdueConfirmRespModel() {
		super();
	}

	public Set<Long> getVoucherIds() {
		return voucherIds;
	}

	public void setVoucherIds(final Set<Long> voucherIds) {
		this.voucherIds = voucherIds;
	}

}
