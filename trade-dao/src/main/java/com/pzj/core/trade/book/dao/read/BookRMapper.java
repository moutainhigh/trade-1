/*
 * TBookMapper.java
 
 * www.piaozhijia.coim
 */
package com.pzj.core.trade.book.dao.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pzj.core.trade.book.dao.entity.BookEntity;
import com.pzj.core.trade.book.dao.entity.BookQueryEntity;

/**
 * Mapper接口.区域
 * @author 票之家
 */

public interface BookRMapper {

	/**
	 * 根据主键获取预约单详情
	 * 
	 * @param bookId
	 * @return
	 */
	BookEntity selectByBookId(String bookId);

	/**
	 * 根据主键获取有效的预约单详情(待出票，待审核的单据)
	 * 
	 * @param bookId
	 * @return
	 */
	BookEntity selectValidByBookId(String bookId);

	/**
	 * 根据预约单id获取其关联的前置订单信息
	 * 
	 * @param srcBookId 预约单id
	 * @return
	 */
	BookEntity selectBySrcBookId(String srcBookId);

	/**
	 * 根据预约单id获取其关联的有效的前置订单信息（待出票，待审核的单据）
	 * 
	 * @param srcBookId 预约单id
	 * @return
	 */
	BookEntity selectValidBySrcBookId(String srcBookId);

	/**
	 * 根据预约单/特价票，免票的查询参数查询预约单
	 */
	List<BookEntity> selectBooksByParam(@Param("bParam") BookQueryEntity bParam);

	/**
	 * 根据预约单/特价票，免票的查询参数预约单个数统计
	 * @return
	 */
	Integer countBooksByParam(@Param("bParam") BookQueryEntity bParam);

	/**
	 * 根据查询参数，查询更新时间+invalidTime超过当前时间的预约单
	 * 
	 * @param bParam
	 * @param invalidTime  失效时间（单位：毫秒）
	 * @return
	 */
	List<BookEntity> selectInvalidBook(@Param("bParam") BookQueryEntity bParam, @Param("invalidTime") Integer invalidTime);

	List<BookEntity> selectquerySparpreisNotCheck(@Param("bParam") BookQueryEntity bParam,
			@Param("overdueTime") Long overdueTime);

}
