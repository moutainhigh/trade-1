-- t_order_strategy
ALTER TABLE `t_order_strategy`
ADD COLUMN `affer_rebate_transferred_state`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '后返金额到账标识（0未到账,1已到帐）' AFTER `after_rebate_amount`,
ADD COLUMN `affer_rebate_transferred_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '后返金额到账时间点' AFTER `affer_rebate_transferred_state`;

