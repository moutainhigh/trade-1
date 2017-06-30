-- chaihj
CREATE TABLE `t_order_extend_attr` (
  `extend_attr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `transaction_id` varchar(20) NOT NULL COMMENT '交易ID',
  `order_id` varchar(20) NOT NULL COMMENT '订单ID',
  `attr_group` varchar(20) DEFAULT NULL COMMENT '填单项组',
  `attr_key` varchar(32) DEFAULT NULL COMMENT '键',
  `attr_value` varchar(128) DEFAULT NULL COMMENT '值',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`extend_attr_id`),
  KEY `inx_transaction_id` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单填单项表';

-- 采购订单配送信息表
CREATE TABLE t_delivery_detail
(
    id BIGINT(20) UNSIGNED PRIMARY KEY NOT NULL COMMENT '主键' AUTO_INCREMENT,
    order_id VARCHAR(20) NOT NULL COMMENT '采购订单ID',
    delivery_way TINYINT(1) DEFAULT '1' NOT NULL COMMENT '配送方式(1:上门自提, 2:快递配送)',
    express_company VARCHAR(128) COMMENT '快递公司',
    express_no VARCHAR(30) COMMENT '快递单号',
    create_time TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL COMMENT '创建时间'
);
CREATE INDEX Idx_order_id ON t_delivery_detail (order_id);