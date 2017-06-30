### (t_order)
alter table `t_order`
drop column `category`,
drop column `sett_mode`,
drop column `channel_type`,
drop column `supplier_agent_id`,
drop column `reseller_agent_id`;



alter table `t_voucher_base`
drop column `voucher_content_image_url`,
drop column `extend_voucher_content`,
drop column `extend_voucher_content_type`,
drop column `extend_voucher_image_url`,
drop column `product_name`,
drop column `product_num`,
drop column `check_start_time`,
drop column `check_end_time`;
 
