package com.pzj.trade.order.model;

import java.io.Serializable;

/**
 * 订单填单项模型.
 * @author CHJ
 *
 */
public class FilledModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 组*/
	private String group;
	/** 键*/
	private String attr_key;
	/** 值*/
	private String attr_value;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAttr_key() {
		return attr_key;
	}

	public void setAttr_key(String attr_key) {
		this.attr_key = attr_key;
	}

	public String getAttr_value() {
		return attr_value;
	}

	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FilledModel [group=");
		builder.append(group);
		builder.append(", attr_key=");
		builder.append(attr_key);
		builder.append(", attr_value=");
		builder.append(attr_value);
		builder.append("]");
		return builder.toString();
	}

}
