ALTER TABLE `t_refund_apply`
ADD COLUMN `transaction_id`  varchar(20) NOT NULL DEFAULT '' COMMENT '交易ID' AFTER `refund_id`;