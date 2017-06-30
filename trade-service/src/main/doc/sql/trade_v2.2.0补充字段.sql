ALTER TABLE `t_order_extend_attr`
MODIFY COLUMN `order_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL  COMMENT '采购订单ID' AFTER `transaction_id`;

#维护退款申请记录

delete from t_refund_apply where apply_id < 20;

#退款中的 改为 8 对接审核中
insert into t_refund_apply(
	refund_id,applier_id,init_party,is_party,is_force,refund_state,refund_audit_state)
SELECT 
	t.refund_id , 1000000000000 as applier_id ,1 as init_party,0 as is_party,0 as is_force,1 as refund_state,8 as refund_audit_state 
from 
	(SELECT DISTINCT  refund_id  from t_order_merchrefund_flow where refund_state=1 and create_time < '2017-01-13') t;

#已退款的 改为 9 审核结束
insert into t_refund_apply(refund_id,applier_id,init_party,is_party,is_force,refund_state,refund_audit_state) 
SELECT t.refund_id , 1000000000000 as applier_id ,1 as init_party,0 as is_party,0 as is_force,1 as refund_state,9 as refund_audit_state 
from ( SELECT DISTINCT  refund_id  from t_order_merchrefund_flow where refund_state=2 ) t;