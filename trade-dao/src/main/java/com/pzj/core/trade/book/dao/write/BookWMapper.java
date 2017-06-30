/*
 * TBookMapper.java
 
 * www.piaozhijia.coim
 */
package com.pzj.core.trade.book.dao.write;

import java.util.List;

import com.pzj.core.trade.book.dao.entity.BookEntity;

/**
 * Mapper接口.区域
 * @author 票之家
 */

public interface BookWMapper {

	/**新增*/
	int insert(BookEntity entity);

	/**批量新增*/
	int insertBatch(List<BookEntity> entitys);

	/**更新*/
	int updateByPrimaryKey(BookEntity entity);

	/**批量更新*/
	int updateBatchByPrimaryKey(List<BookEntity> entitys);

	/**下单成功后回调修改状态 */
	int callBackByTransaction_id(String transaction_id);

	/**根据主键删除*/
	int deleteByPrimaryKey(long bookId);

}
