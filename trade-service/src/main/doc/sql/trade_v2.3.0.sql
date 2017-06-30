-- t_order
alter table t_order drop column category;
alter table t_order drop column sett_mode;
alter table t_order drop column channel_type;
alter table t_order drop column supplier_agent_id;
alter table t_order drop column reseller_agent_id;

ALTER TABLE `t_order`
ADD COLUMN `order_level`  tinyint(1) NOT NULL COMMENT '订单等级，初始供应商为1，各级递增' AFTER `p_order_id`,
ADD COLUMN `order_source`  tinyint(1) NOT NULL DEFAULT 0 COMMENT ' 订单来源 0. 普通订单 1. 预约单 2. 免票 3. 特价票' AFTER `order_level`,
ADD COLUMN `ticket_office_id`  bigint(20) NOT NULL DEFAULT 0 COMMENT '售票点' AFTER `order_source`;
ALTER TABLE `t_order` ADD COLUMN `pay_state`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '付款锁. 0: 未支付; 1: 已锁定,2:支付成功' AFTER `settlement_price`;
alter table t_order add column pay_way tinyint(1) NOT NULL COMMENT '支付方式. 0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付.';
alter table t_order add column guider varchar(20) not null default '#' comment '导游' after guide_id;
alter table t_order add column depart varchar(32) not null default '#' comment '部门' after travel_depart_id;
alter table t_order add column `version` int(4) NOT NULL DEFAULT '0' COMMENT '版本' after pay_state;
ALTER TABLE `t_order` ADD COLUMN `cancel_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '订单自动取消时间' AFTER `idcard_no`;
ALTER TABLE `t_order` MODIFY COLUMN `twice_confirm_time`  timestamp NULL DEFAULT NULL COMMENT '二次确认时间' AFTER `need_confirm`;

-- t_order
update t_order set pay_way = 0 where third_pay_type = 0; -- 纯余额
update t_order set pay_way = 1 where third_pay_type = 1; -- 支付宝
update t_order set pay_way = 2 where third_pay_type = 2; -- 微信
update t_order set pay_way = 4 where third_pay_type = 4; -- 混合支付

-- 更新子订单级别为1, 主订单级别为0.
update t_order set order_level = 1 where order_id != transaction_id;
update t_order set order_level = 2 where order_id = transaction_id;

--初始化订单的可自动取消时间
update t_order set cancel_time=create_time + 30 interval MINUTE where order_status=1;
-- t_order_merch
ALTER TABLE `t_order_merch`
ADD COLUMN `sku_name`  varchar(100) NOT NULL COMMENT '规格名' AFTER `merch_name`;
ALTER TABLE `t_order_merch`
ADD COLUMN `supplier_id`  bigint(20) NOT NULL DEFAULT 0 COMMENT '原始供应商ID' AFTER `p_product_id`;
ALTER TABLE `t_order_merch`
ADD COLUMN `auto_confirm` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否自动核销：0否1是' AFTER `vour_type`;
ALTER TABLE `t_order_merch`
ADD COLUMN `refunding_num` int(11) DEFAULT '0' COMMENT '退款中数量' AFTER `refund_num`;
ALTER TABLE `t_order_merch`
ADD COLUMN `version` int(4) DEFAULT '0' COMMENT '版本' AFTER `expire_time`;
ALTER TABLE `t_order_merch`
MODIFY COLUMN `channel_id`  bigint(20) NOT NULL DEFAULT 0 COMMENT '渠道ID' AFTER `p_product_id`;
ALTER TABLE `t_order_merch`
ADD COLUMN `show_start_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'  COMMENT '演艺开始时间' AFTER `expire_time`,
ADD COLUMN `show_end_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'  COMMENT '演艺结束时间' AFTER `show_start_time`;

-- t_order_strategy
ALTER TABLE `t_order_strategy`
ADD COLUMN `advice_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '政策建议零售价',
ADD COLUMN   `settlement_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '政策结算价',
ADD COLUMN   `rebate_method` tinyint(1) NOT NULL DEFAULT '1' COMMENT '返利方式（1：前返   2：后返）',
ADD COLUMN   `rebate_settlement` tinyint(1) NOT NULL DEFAULT '1' COMMENT '结算方式（1即时返/2周期返）',
ADD COLUMN   `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付单价 前返取政策结算价，后返取政策建议零售价',
ADD COLUMN   `after_rebate_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '后返金额';

--订单游客表
CREATE TABLE `t_order_tourist` (
  `tourist_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(20) NOT NULL COMMENT '订单ID',
  `merch_id` varchar(20) NOT NULL COMMENT '商品ID',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '游客姓名',
  `idcard` varchar(20) NOT NULL DEFAULT '' COMMENT '游客身份证号',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `name_spell` varchar(64) NOT NULL DEFAULT '' COMMENT '姓名拼音',
  `other` varchar(255) NOT NULL DEFAULT '' COMMENT '其他',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`tourist_id`),
  KEY `idx_merch_id` (`merch_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='订单游客表';

-- t_voucher_base 此SQL必需在更新t_voucher_base表之前执行.
insert into t_order_tourist (
	order_id, merch_id,`name`,idcard,mobile
) (
	select
		m.transaction_id as order_id, m.merch_id, v.contact_name as `name`, if(voucher_content_type = 3, voucher_content, '') as idcard, v.contact_mobile as mobile
	from
		t_voucher_base v, t_order_merch m
	where
		m.voucher_id = v.voucher_id
);

update t_refund_apply t1
set order_id = (
	select
		distinct t2.order_id
	from
		t_order_merchrefund_flow t2
	where
		t1.refund_id = t2.refund_id and t2.refund_type=2
);

-- t_order_merchrefund_flow
alter table t_order_merchrefund_flow drop column refund_state;
alter table t_order_merchrefund_flow drop column refund_audit_state;
alter table t_order_merchrefund_flow drop column is_party;
alter table t_order_merchrefund_flow drop column is_force;
alter table t_order_merchrefund_flow modify column refund_type  tinyint(1) NOT NULL DEFAULT 0 COMMENT '1供应商 2分销商' AFTER merch_id;
alter table t_order_merchrefund_flow modify column refund_rule_type  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '退款规则类型（0,未使用规则;1,时间点前规则;2,时间点后规则）' AFTER refund_type;


alter table t_voucher_confirm add column `confirm_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '核销时间';

CREATE TABLE `t_oper_log` (
  `log_id` bigint(20) NOT NULL COMMENT '日志主键',
  `order_id` varchar(20) NOT NULL COMMENT '业务主键，一般为交易ID/预约单ID',
  `operator` bigint(20) NOT NULL COMMENT '操作人ID',
  `prev` smallint(1) DEFAULT NULL COMMENT '操作前状态',
  `next` smallint(1) DEFAULT NULL COMMENT '操作后状态',
  `event` varchar(32) NOT NULL COMMENT '事件类型：book_create:预约单创建 book_update:预约单更新 book_audit:预约单审核通过 book_refuse:预约单审核拒绝 book_cancel:预约单取消',
  `context` text NOT NULL COMMENT '内容',
  `create_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 日志操作表';

CREATE TABLE `t_book` (
  `book_id` varchar(20) NOT NULL COMMENT '预订单id',
  `src_book_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '原始预约单id',
  `transaction_id` varchar(20) NOT NULL COMMENT '交易ID，原始预约单和最新预约单所用的整个交易ID',
  `operator_id` bigint(20) NOT NULL COMMENT '操作人id',
  `reseller_id` bigint(20) NOT NULL COMMENT '预定/免票特价票分销商Id',
  `travel_date` bigint(18) NOT NULL COMMENT '游玩时间',
  `book_status` tinyint(1) NOT NULL COMMENT '状态（1：待出票 2：已出票 0：已取消 3：待审核 4：已拒绝）',
  `book_type` tinyint(1) NOT NULL COMMENT '类型（1：预约单 2：特价票 3：免票 4：预约前置订单）',
  `book_date` bigint(18) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `delivery_code` varchar(20) NOT NULL DEFAULT ' ' COMMENT '提货码',
  `book_detail` text NOT NULL COMMENT '产品信息,填写项,联系人json串',
  `spu_id` bigint(20) NOT NULL COMMENT '产品组ID',
  `total_num` int(11) DEFAULT NULL COMMENT '商品总个数',
  `total_amount` int(11) DEFAULT NULL COMMENT '预订单总价格',
  `update_time` bigint(18) DEFAULT NULL COMMENT '最新更新时间',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约表';


ALTER TABLE `t_order_strategy`
ADD INDEX `idx_order_id` (`order_id`) ;

ALTER TABLE `t_order_remarks`
ADD INDEX `idx_order_id` (`order_id`) ;



ALTER TABLE `t_freeze_flow`
ADD COLUMN `third_content`  varchar(255) NULL COMMENT '第三方支付返回值详情' AFTER `third_amount`;



ALTER TABLE `t_order_merchrefund_flow`
ADD COLUMN `apply_merch_status`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '退款申请时订单商品对应的状态' AFTER `merch_id`;


ALTER TABLE `t_refund_apply_info`
ADD COLUMN `oper_type`  tinyint(1) NOT NULL DEFAULT 1 COMMENT '0 申请的相关信息 1审核操作的相关信息' AFTER `info_id`,
MODIFY COLUMN `reason`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '退款原因',
MODIFY COLUMN `info_id`  bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '信息表ID';


ALTER TABLE `t_refund_apply`
ADD COLUMN `transaction_id`  varchar(20) NOT NULL DEFAULT '' COMMENT '销售订单ID' AFTER `refund_id`,
ADD COLUMN `apply_sale_order_status`  tinyint(2) NOT NULL DEFAULT 10 COMMENT '退款申请时主订单的状态' AFTER `sale_order_id`;

ALTER TABLE `t_refund_oper_log`
MODIFY COLUMN `log_id`  bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键';

--重要字段放前面
ALTER TABLE `t_order`
MODIFY COLUMN `order_status`  int(4) NOT NULL DEFAULT 1 COMMENT '订单状态.1: 待付款; 10: 已支付; 20: 已取消; 30: 已退款; 40: 已完成' AFTER `p_order_id`,
MODIFY COLUMN `pay_way`  tinyint(1) NOT NULL COMMENT '支付方式. 0: 纯余额; 1. 支付宝; 2. 微信; 4: 混合支付; 5: 现金; 6: 后付.' AFTER `order_status`,
MODIFY COLUMN `pay_state`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '付款锁. 0: 未支付; 1: 已锁定,2:支付成功' AFTER `pay_way`,
MODIFY COLUMN `sale_port`  tinyint(1) NULL DEFAULT 1 COMMENT '销售端口（1：线下窗口、2：二维码微店、3：PC分销端、4：导游APP、5：商户APP、6：导游微店、7：商户微店、8：OTA、9：APP）' AFTER `order_source`,
MODIFY COLUMN `reseller_id`  bigint(20) NOT NULL COMMENT '分销商ID' AFTER `sale_port`,
MODIFY COLUMN `supplier_id`  bigint(20) NOT NULL COMMENT '供应商ID' AFTER `reseller_id`;

ALTER TABLE `t_order_merch`
MODIFY COLUMN `merch_state`  int(4) NOT NULL COMMENT '订单商品状态. 0:可消费; 1: 已消费; 3: 已退款; -1: 不可用;4:待确认；5：已完成' AFTER `transaction_id`,
MODIFY COLUMN `merch_type`  int(4) NULL DEFAULT NULL COMMENT '产品类型/** 订单类型. 1: 景区; 2: 票; 3: 住宿; 4: 小交通; 5: 特产;6:一日游 */' AFTER `merch_state`,
MODIFY COLUMN `vour_type`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '凭证类型. 0: 未凭证; 1: 联系人信息; 2: 魔方码  3:身份证' AFTER `merch_type`,
MODIFY COLUMN `voucher_id`  bigint(20) NOT NULL COMMENT '服务属性ID' AFTER `vour_type`;

ALTER TABLE `t_order_extend_attr`
MODIFY COLUMN `attr_value`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值' AFTER `attr_key`;




