package com.pzj.core.trade.voucher.entity;

import java.sql.Timestamp;

/**
 * 凭证的时间信息.
 * @author YRJ
 *
 */
public class VoucherTimeEntity {

	private Timestamp start_time;

	private Timestamp expire_time;

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(Timestamp expire_time) {
		this.expire_time = expire_time;
	}
}
