
ALTER TABLE `t_order_merch`
ADD COLUMN `is_cleaned`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已清算 0：未清算，1：已清算' AFTER `is_refunding`;

ALTER TABLE `t_voucher_base`
MODIFY COLUMN `voucher_content_type`  int(10) NOT NULL COMMENT '1 身份证 2 魔方码、二维码 3 手机号' AFTER `voucher_content`;

ALTER TABLE `t_order_merch`
MODIFY COLUMN `vour_type`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '凭证类型. 0: 未凭证; 1: 联系人信息; 2: 魔方码  3:身份证' AFTER `merch_name`;

--更新老数据：将已清算的商品的是否清算字段更新为1
update trade.t_order_merch set is_cleaned = 1 where merch_state = 5;

