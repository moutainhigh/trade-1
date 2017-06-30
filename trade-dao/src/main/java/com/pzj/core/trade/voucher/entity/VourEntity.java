package com.pzj.core.trade.voucher.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 凭证基本信息.
 * @author YRJ
 * @author DongChunfu
 */
public class VourEntity implements Serializable {

	private static final long serialVersionUID = -8863654254889164713L;

	/**核销基础信息ID*/
	private long voucher_id;

	/**交易号*/
	private String transaction_id;

	private String voucher_content;

	/**供应商ID*/
	private long supplier_id;

	/**凭证状态*/
	private int voucher_state;

	/**产品线*/
	private int voucher_category;

	/**游玩开始时间*/
	private Date start_time;
	/**游玩结束时间 */
	private Date expire_time;
	/**实际检票时间*/
	private Date checkedTime;

	/**是否需要账号操作 */
	private boolean needAccoutHandel = false;

	/**核销数量*/
	private int checkedNum;

	/**核销版本*/
	private int version;

	public long getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(final long voucher_id) {
		this.voucher_id = voucher_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(final String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getVoucher_content() {
		return voucher_content;
	}

	public void setVoucher_content(final String voucher_content) {
		this.voucher_content = voucher_content;
	}

	public long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(final long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public int getVoucher_state() {
		return voucher_state;
	}

	public void setVoucher_state(final int voucher_state) {
		this.voucher_state = voucher_state;
	}

	public int getVoucher_category() {
		return voucher_category;
	}

	public void setVoucher_category(final int voucher_category) {
		this.voucher_category = voucher_category;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(final Date start_time) {
		this.start_time = start_time;
	}

	public Date getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(final Date expire_time) {
		this.expire_time = expire_time;
	}

	public int getCheckedNum() {
		return checkedNum;
	}

	public void setCheckedNum(final int checkedNum) {
		this.checkedNum = checkedNum;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public Date getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(final Date checkedTime) {
		this.checkedTime = checkedTime;
	}

	public boolean needAccoutHandel() {
		return needAccoutHandel;
	}

	public void setNeedAccoutHandel(final boolean needAccoutHandel) {
		this.needAccoutHandel = needAccoutHandel;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("VourEntity [voucher_id=");
		builder.append(voucher_id);
		builder.append(", transaction_id=");
		builder.append(transaction_id);
		builder.append(", voucher_content=");
		builder.append(voucher_content);
		builder.append(", supplier_id=");
		builder.append(supplier_id);
		builder.append(", voucher_state=");
		builder.append(voucher_state);
		builder.append(", voucher_category=");
		builder.append(voucher_category);
		builder.append(", start_time=");
		builder.append(start_time);
		builder.append(", expire_time=");
		builder.append(expire_time);
		builder.append(", needAccoutHandel=");
		builder.append(needAccoutHandel);
		builder.append(", checkedNum=");
		builder.append(checkedNum);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}
