package com.pzj.trade.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印门票结果商品模型
 * @author GLG
 */
public class OrderMerchConfirmsRespModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/**商品id	  */
	private String merchId;
	/**检票点id*/
	private String checkPoint;
	/**检票时间*/
	private Date checkedTime;
	/**是否已检1、是 2、否*/
	private Integer isChecked;

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}

	public Date getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(Date checkedTime) {
		this.checkedTime = checkedTime;
	}

	public Integer getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

}
