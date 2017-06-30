/** table: t_order */

ALTER TABLE `t_order`
ADD COLUMN `agent_flag`  tinyint(1) NOT NULL DEFAULT '1' COMMENT '代下单标志：1:不需要代下单；2：需要代下单；3已经代下单' AFTER `p_order_id`,
ADD COLUMN `twice_confirm_time`  timestamp NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '二次确认时间' AFTER `need_confirm`;
ALTER TABLE `t_order`
MODIFY COLUMN `order_status`  int(4) NOT NULL DEFAULT 1 COMMENT '订单状态.1: 待付款; 10: 已支付; 20: 已取消; 30: 已退款; 40: 已完成' AFTER `twice_confirm_time`,
MODIFY COLUMN `need_confirm`  tinyint(1) NOT NULL COMMENT '是否需要确认. 1: 不需要; 2: 需要;3:已确认' AFTER `reseller_agent_id`;


/** table: t_order_merch */
alter table t_order_merch add index inx_rmerch_id(root_merch_id);
alter table t_order_merch add index inx_transaction_id(transaction_id);
alter table t_order_merch add COLUMN vour_type tinyint(1) not null default 0 comment '凭证类型. 0: 联系人信息; 1: 游客身份证; 2: 二维码';
alter table t_order_merch add COLUMN effec_time bigint(11) not null default 0 comment '游玩有效期';
alter table t_order_merch add COLUMN clear_type tinyint(1) not null default 1 comment '商品清算类型. 1: 核销自动清算; 2: 核销之后固定时间核销';
ALTER TABLE `t_order_merch`
MODIFY COLUMN `merch_state`  int(4) NOT NULL COMMENT '订单商品状态. 0:可消费; 1: 已消费; 3: 已退款; -1: 不可用;4:待确认；5：已完成' AFTER `transaction_id`;

alter table `t_order_merch` modify column `merch_state`  int(4) not null comment '订单商品状态. 0:可消费; 1: 已消费; 3: 已退款; -1: 不可用;4:待确认；5：已完成' after `transaction_id`;
alter table `t_order_merch` add column `purchase_price`  decimal(10,4) null comment '采购价格,报表用' after `price`;


alter table t_order_merch add COLUMN is_refunding tinyint(1) not null default 0 comment '商品是否存在退款中的 0否  1是';

/** table: t_order_merchrefund_flow */
alter table `t_order_merchrefund_flow` add COLUMN  `refund_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1供应商 2分销商';
alter table t_order_merchrefund_flow change column merchandise_id merch_id varchar(20) not null;
alter table `t_order_merchrefund_flow` modify `refund_state` tinyint(1) default 1 COMMENT '退款状态(1，正在提现 2，成功提现 3，提现失 败)';
alter table `t_order_merchrefund_flow` modify `refund_audit_state` tinyint(1) default 8 COMMENT '退款第三方审核状态(8，正在提现 9，成功提 现 7，提现失败)';
alter table t_order_merchrefund_flow add index inx_order_id(order_id);
alter table t_order_merchrefund_flow add index inx_merch_Id(merch_id);

create table `t_merch_clean_relation` (
  `clean_id` bigint(20) unsigned not null auto_increment comment '主键ID',
  `order_id` varchar(20) not null default '' comment '订单ID',
  `merch_id` varchar(20) not null default '' comment '商品ID',
  `effec_time` bigint(12) not null default '0' comment '预期清算时间',
  `clean_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '清算类别. 1: 正常清算; 2: 逾期清算',
  `clean_state` tinyint(1) not null default '0' comment '清算状态',
  `is_manual` tinyint(1) not null default '0' comment '是否可手动清算.0: 不可;1: 可手动.只有清算规则为固定时间规则时, 此值有效.',
  `clean_count` tinyint(1) not null default '0' comment '清算次数, 达到6次之后报警人工干预',
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  `clean_time` timestamp not null default '0000-00-00 00:00:00' comment '清算时间',
  primary key (`clean_id`)
) engine=InnoDB default charset=utf8 comment='商品清算关系表';

/** table: t_merch_clean_relation */
alter table t_merch_clean_relation add index inx_order_id(order_id);
alter table t_merch_clean_relation add index inx_merch_id(merch_id);

CREATE TABLE `t_freeze_flow` (
  `freeze_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `receive_type` tinyint(1) DEFAULT NULL COMMENT '收款类型. 1: 付款; 2: 退票退款',
  `payer_id` bigint(20) DEFAULT NULL COMMENT '订单的支付者ID',
  `order_id` varchar(20) DEFAULT NULL COMMENT '订单id',
  `sign_id` varchar(50) DEFAULT NULL COMMENT '支付冻结唯一流水号',
  `freeze_state` int(2) DEFAULT NULL COMMENT '冻结状态 1：未完成  2：成功，3失败',
  `balance_amount` decimal(10,4) DEFAULT NULL COMMENT '余额支付金额',
  `third_amount` decimal(10,4) DEFAULT NULL COMMENT '第三方支付金额',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`freeze_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2635 DEFAULT CHARSET=utf8;

create table `t_mftour_code` (
  `code_id` bigint(20) not null auto_increment comment '主键ID',
  `mf_code` varchar(8) not null default '000000' comment '魔方码',
  `transaction_id` varchar(20) not null default '0' comment '事务ID',
  `order_id` varchar(20) not null comment '订单ID',
  `merch_id` varchar(20) not null comment '商品ID',
  `supplier_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '供应商ID',
  `code_state`  tinyint(1) not null default 0 comment '验证状态. 0:待验证; 1: 已验证; 2: 已过期',
  `source` tinyint(1) not null default 1 comment '验证来源. 1:扫码验证; 2: 平台手动验证',
  `create_time` timestamp not null default current_timestamp comment '码生成时间',
  `update_time` timestamp not null default '0000-00-00 00:00:00' comment '更新时间',
  primary key (`code _id`)
) engine=InnoDB default charset=utf8 comment='核销码, 魔方码';

/** table: t_voucher_dock_info */
create table `t_voucher_dock_info` (
  `id` bigint(20) not null auto_increment,
  `transaction_id` varchar(36) default null comment '核销ID',
  `external_order_id` varchar(36) default null comment '第三方订单号',
  `auxiliary_code` varchar(255) default null comment '第三方订单辅助码',
  primary key (`id`)
) engine=InnoDB default charset=utf8;

/** table: t_mftour_code */
alter table t_mftour_code add index inx_mf_code(mf_code);

/** table: t_order_refund_merch */
drop table `t_order_refund_merch`;

/** table: t_voucher_confirm */
alter table t_voucher_confirm add index inx_voucher_id(voucher_id);

/** table: t_voucher_base */
alter table t_voucher_base add column voucher_type int default null comment '核销类型(1: 自动 2: 手动; 3: 固定时间)' ;
alter table t_voucher_base add column exp_voucher_time datetime default null comment '预计核销时间' ;
alter table t_voucher_base add column voucher_time datetime default null comment '首次核销时间' ;
alter table t_voucher_base add column is_overdue tinyint default "0"comment '是否逾期核销(设置， 默认0：不是逾期，1是逾期 )' ;
alter table t_voucher_base add column check_start_time varchar(10) default null comment '检票时间段:开始时间，格式=小时:分钟 ； 例如10:23' ;
alter table t_voucher_base add column check_end_time varchar(10) default null comment '检票时间段:结束时间，格式=小时:分钟 ； 例如10:23' ;
/** table: tbl_biz_refund_log */
ALTER TABLE `tbl_biz_refund_log` ADD COLUMN `auditor_id`  bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '审核人ID' AFTER `bank_amount`;
ALTER TABLE `tbl_biz_refund_log` ADD COLUMN `auditor_name`  varchar(30) NOT NULL default '' COMMENT '审核人姓名' AFTER `auditor_id`;