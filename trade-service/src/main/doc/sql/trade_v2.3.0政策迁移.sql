--首先插入在商品表不在政策表的政策：
insert into t_order_strategy(order_id,merch_id,channel_id,strategy_id,price)
SELECT
	m.order_id,
	m.root_merch_id,
	m.channel_id,
	m.strategy_id,
	m.price
FROM
	t_order_merch m
LEFT JOIN t_order_strategy s
on m.order_id=s.order_id
where s.order_id is null
and m.strategy_id>0;

--然后获取真实的政策价格和返利信息
update trade.t_order_strategy s
set s.advice_price=(select i.advice_price/100 from product.strategy_info i where i.id=s.strategy_id),
s.settlement_price=(select i.settlement_price/100 from product.strategy_info i where i.id=s.strategy_id),
s.rebate_method=(select i.rebate_method from product.strategy_info i where i.id=s.strategy_id),
s.rebate_settlement=(select r.rebate_settlement 
from product.strategy_info i,product.rebate_info r where i.id=s.strategy_id and i.id=r.strategy_id limit 1),
s.after_rebate_amount=(select count(r.rebate_amount) /100
from product.strategy_info i,product.rebate_info r where i.id=s.strategy_id and i.id=r.strategy_id )
where s.advice_price=0;

--sku name
update trade.t_order_merch m
set sku_name=(select s.sku_name from product.product_sku  s where s.id=m.product_id);