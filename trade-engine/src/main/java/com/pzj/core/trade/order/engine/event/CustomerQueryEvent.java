package com.pzj.core.trade.order.engine.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pzj.base.entity.SysUser;
import com.pzj.base.service.sys.IUserService;

/**
 * 用户查询事件.
 * @author YRJ
 *
 */
@Component(value = "customerQueryEvent")
public class CustomerQueryEvent {

	private final static Logger logger = LoggerFactory.getLogger(CustomerQueryEvent.class);

	@Autowired
	private IUserService userService;

	public SysUser queryCustomerById(long customerId) {
		SysUser user = null;
		try {
			user = userService.getById(customerId);
		} catch (Throwable e) {
			logger.error("查询分销商信息失败, userId: " + (customerId), e);
		}
		return user;
	}
}
