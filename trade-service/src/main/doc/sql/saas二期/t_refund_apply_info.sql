ALTER TABLE `t_refund_apply_info`
ADD COLUMN `oper_type`  tinyint(1) NOT NULL DEFAULT 1 COMMENT '0 申请的相关信息 1审核操作的相关信息' AFTER `info_id`,
MODIFY COLUMN `reason`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '退款原因',
MODIFY COLUMN `info_id`  bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '信息表ID';