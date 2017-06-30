CREATE TABLE `t_book` (
  `book_id` bigint(20) NOT NULL COMMENT '预订单id',
  `src_book_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '原始预约单id',
  `transaction_id` bigint(20) NOT NULL COMMENT '交易ID，原始预约单和最新预约单所用的整个交易ID',
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
  `update_time` bigint(18) DEFAULT NULL COMMENT '最新更新时间'
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;