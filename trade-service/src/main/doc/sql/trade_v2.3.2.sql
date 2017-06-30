-- t_order_merch
ALTER TABLE `t_order_merch`
ADD COLUMN `verification_type`  tinyint(1) NOT NULL DEFAULT 1 COMMENT '核销方式 :1：按订单2：按规格3：按份数' AFTER `vour_type`;

-- t_order_strategy
ALTER TABLE `t_order_strategy`
ADD COLUMN `interval_day`  tinyint(1) NOT NULL DEFAULT -1 COMMENT '解冻间隔天数（周期解冻默认-1；次日解冻1）' AFTER `rebate_method`;


ALTER TABLE `t_book`
ADD COLUMN `supplier_id`  bigint(20) NOT NULL COMMENT 'saas供应商id' AFTER `reseller_id`;


--数据迁移，老数据没有供应商id的处理
update t_book t set t.supplier_id = (select u.supplier_id from core_deploment.sys_user u where u.id = t.operator_id);


