ALTER TABLE `t_order_merchrefund_flow`
ADD COLUMN `apply_merch_status`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '退款申请时订单商品对应的状态' AFTER `merch_id`;