


ALTER TABLE `t_order`
MODIFY COLUMN `order_source`  tinyint(1) NOT NULL DEFAULT 0 COMMENT ' ������Դ 0. ��ͨ���� 1. ԤԼ�� 2. �ؼ�Ʊ 3. ��Ʊ' AFTER `order_level`;