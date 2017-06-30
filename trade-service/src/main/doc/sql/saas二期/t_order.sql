-- t_order
alter table t_order drop column category;
alter table t_order drop column sett_mode;
alter table t_order drop column channel_type;
alter table t_order drop column supplier_agent_id;
alter table t_order drop column reseller_agent_id;

ALTER TABLE `t_order`
ADD COLUMN `order_level`  tinyint(1) NOT NULL COMMENT '订单等级，初始供应商为1，各级递增' AFTER `p_order_id`,
ADD COLUMN `order_source`  varchar(1) NOT NULL DEFAULT 0 COMMENT ' 订单来源 0. 普通订单 1. 预约单 2. 免票 3. 特价票' AFTER `order_level`,
ADD COLUMN `ticket_office_id`  bigint(20) NOT NULL DEFAULT 0 COMMENT '售票点' AFTER `order_source`;
ALTER TABLE `t_order` ADD COLUMN `pay_state`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '付款锁. 0: 未支付; 1: 已锁定,2:支付成功' AFTER `settlement_price`;
alter table t_order add column pay_way tinyint(1) NOT NULL COMMENT '支付方式. 0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付.';
alter table t_order add column guider varchar(20) not null default '#' comment '导游' after guide_id;
alter table t_order add column depart varchar(32) not null default '#' comment '部门' after travel_depart_id;
alter table t_order add column `version` int(4) NOT NULL DEFAULT '0' COMMENT '版本' after pay_state;
ALTER TABLE `t_order` ADD COLUMN `cancel_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '订单自动取消时间' AFTER `idcard_no`;
ALTER TABLE `t_order` MODIFY COLUMN `twice_confirm_time`  timestamp NULL DEFAULT NULL COMMENT '二次确认时间' AFTER `need_confirm`;
