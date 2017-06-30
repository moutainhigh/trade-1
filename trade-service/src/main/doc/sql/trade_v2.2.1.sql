CREATE TABLE `t_order_export` (
  `export_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_name` varchar(128) NOT NULL COMMENT '文件名',
  `param` text NOT NULL COMMENT '查询条件',
  `export_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '文件导出状态：0：导出中，1：导出成功,2:导出失败',
  `err_msg` varchar(128) NOT NULL DEFAULT '' COMMENT '失败原因',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`export_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单导出文件记录';

ALTER TABLE t_order_remarks CHANGE remark remark varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单描述';  