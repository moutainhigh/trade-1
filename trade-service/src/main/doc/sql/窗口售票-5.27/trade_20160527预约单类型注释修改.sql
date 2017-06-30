


ALTER TABLE `t_order`
MODIFY COLUMN `order_source`  tinyint(1) NOT NULL DEFAULT 0 COMMENT ' 订单来源 0. 普通订单 1. 预约单 2. 特价票 3. 免票' AFTER `order_level`;