-- 提现与订单对应关系表


CREATE TABLE `t_withdraw_order_flow` (
  `flow_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单提现退款流水Id',
  `order_id` varchar(20) NOT NULL COMMENT '提现对应的订单ID',
  `withdraw_id` bigint(20) NOT NULL COMMENT '提现ID',
  `refund_id` varchar(32) NOT NULL COMMENT '订单退款标识ID',
  `refund_money` double(9,3) NOT NULL,
  `flow_status` tinyint(2) NOT NULL COMMENT '流水状态（1 正在退款提现 2成功 3失败）',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`flow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
-- 提现与账户服务的唯一性验签ID
ALTER TABLE `t_cash_postal` ADD COLUMN `settlement_sign_id` varchar(32) NULL DEFAULT NULL COMMENT '提现与账户服务的唯一性验签' AFTER `postal_money`;
-- 退款审核状态备注修改
ALTER TABLE `t_order_merchrefund_flow` MODIFY COLUMN `refund_audit_state`  tinyint(1) NULL DEFAULT 8 COMMENT '退款第三方审核状态(8，退款中 9，退款成功 7，失败失败)' 