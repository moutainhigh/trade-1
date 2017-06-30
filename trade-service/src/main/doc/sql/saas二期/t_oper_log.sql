CREATE TABLE `t_oper_log` (
  `log_id` bigint(20) NOT NULL COMMENT '日志主键',
  `order_id` bigint(20) NOT NULL COMMENT '业务主键，一般为交易ID',
  `operator` bigint(20) NOT NULL COMMENT '操作人ID',
  `event` varchar(32) NOT NULL COMMENT '事件类型：book_create:预约单创建 book_update:预约单更新 book_audit:预约单审核通过 book_refuse:预约单审核拒绝 book_cancel:预约单取消',
  `context` text NOT NULL COMMENT '内容',
  `create_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `prev` smallint(1) DEFAULT NULL COMMENT '操作前状态',
  `next` smallint(1) DEFAULT NULL COMMENT '操作后状态',
  PRIMARY KEY (`log_id`),
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 日志操作表';