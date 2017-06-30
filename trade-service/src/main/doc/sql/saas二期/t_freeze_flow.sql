ALTER TABLE `t_freeze_flow`
ADD COLUMN `third_content`  varchar(255) NULL COMMENT '第三方支付返回值详情' AFTER `third_amount`;