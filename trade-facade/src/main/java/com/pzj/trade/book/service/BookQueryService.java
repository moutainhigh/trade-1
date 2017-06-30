package com.pzj.trade.book.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.entity.QueryResult;
import com.pzj.trade.book.model.BookDetailModel;
import com.pzj.trade.book.model.BookQueryInModel;
import com.pzj.trade.book.model.BookQueryOutModel;
import com.pzj.trade.book.model.DeliveryCodeVModel;
import com.pzj.trade.book.model.SparpreisQueryInModel;
import com.pzj.trade.book.model.SparpreisQueryOutModel;

public interface BookQueryService {

	/**
	 * @api {API} com.pzj.trade.book.service.BookQueryService#queryBooks 分页查询预约单
	 * @apiGroup bookService
	 * @apiName 分页查询预约单
	 * @apiDescription 分页查询预约单
	 * @apiVersion 1.0.0
	 * 
	 * 
	 * @apiParam {BookQueryInModel} model 预约单查询入参
	 * @apiParam {ServiceContext} context 
	 * 
	 * 
	 * @apiUse BookQueryInModel 
	 * @apiUse ServiceContext
	 * @apiParamExample 参数举例
	 * {"model":{"booker":"12345617","operatorId":2216619736763787},
	 * "context":{}}
	 * 
	 * @apiSuccess (返回) Result<QueryResult<BookQueryOutModel>> result 预订单查询结果
	 * 
	 * @apiSuccessExample 正确返回
	 *  {"code":10000,data:[{"bookId": "MF1000447348","booker":2216619736763347,"operatorId":2216619736763787,
	 *  "travelDate":1488434628256,"bookDate":1488434622256,"bookState":1,"totalPrice":100,"totalBugNum":10,"productIds":[238923290,43793948239]}]}
	 * 
	 */
	Result<QueryResult<BookQueryOutModel>> queryBooks(BookQueryInModel model, ServiceContext context);

	/**
	 * @api {API} com.pzj.trade.book.service.BookQueryService#queryBookByBookId 查询预约单详情
	 * @apiGroup bookService
	 * @apiName 查询预约单详情1
	 * @apiDescription 查询预约单详情
	 * @apiVersion 1.0.0
	 * 
	 * 
	 * @apiParam {String} bookId 预约单Id
	 * @apiParam {ServiceContext} context 
	 * 
	 * @apiUse ServiceContext
	 * @apiParamExample 参数举例
	 * {"bookId":348923840358309,
	 * "context":{}}
	 * 
	 * @apiSuccess (返回) Result<BookDetailModel> result 预订单详情
	 * 
	 * @apiSuccessExample 正确返回
	 *  {"code":10000,"data":{"booker":"12345617","operatorId":2216619736763787,"travel":2216619736764717,"travelDepartId":2216613736765112,
	 * "guideId": 2216619736563713,"travelDate":1488346614490,"deliveryCode":"MF12345",
	 * "contactees":[{}],"filledModelList":[{}],"products":[{}],"remark":"示例"}}
	 * 
	 */
	Result<BookDetailModel> queryBookByBookId(String bookId, ServiceContext context);

	/**
	 * @api {API} com.pzj.trade.book.service.BookService#querySparpreis 分页查询特价票免票订单
	 * @apiGroup bookService
	 * @apiName 分页查询特价票免票订单2
	 * @apiDescription 分页查询特价票免票订单
	 * @apiVersion 1.0.0
	 * 
	 * 
	 * @apiParam {SparpreisQueryInModel} model 特价票、免票查询入参
	 * @apiParam {ServiceContext} context 
	 * 
	 * 
	 * @apiUse SparpreisQueryInModel 
	 * @apiUse ServiceContext
	 * @apiParamExample 参数举例
	 * {"model":{"booker":"12345617","operatorId":2216619736763787},
	 * "context":{}}
	 * 
	 * @apiSuccess (返回) Result<QueryResult<SparpreisQueryOutModel>> result 预订单查询结果
	 * 
	 * @apiSuccessExample 正确返回
	 {"code":10000,data:[{"bookId": "MF1000447348","resellerId":2216619736763347,"operatorId":2216619736763787,"bookType":2,
	 *  "travelDate":1488434628256,"bookState":1,"totalPrice":100,"productIds":[238923290,43793948239]}]}
	 * 
	 */
	Result<QueryResult<SparpreisQueryOutModel>> querySparpreis(SparpreisQueryInModel model, ServiceContext context);

	/**
	 * @api {API} com.pzj.trade.book.service.BookService#validateCode 验证特价票免票提货码
	 * @apiGroup bookService
	 * @apiName 验证特价票免票提货码
	 * @apiDescription 验证特价票免票提货码
	 * @apiVersion 1.0.0
	 * 
	 * @apiParam {DeliveryCodeVModel} model 提货码验证入参
	 * @apiParam {ServiceContext} context
	 * @apiUse DeliveryCodeVModel
	 * @apiUse ServiceContext
	 * @apiParamExample 参数举例
	 * {"model":{"bookId":"2128117041910002","deliveryCode":"123456"},
	 * "context":{}}
	 * 
	 * @apiSuccess (返回) Result<Boolean> result 验证是否正确标识
	 * 
	 * @apiSuccessExample 正确返回
	 *  {"code":10000,data:true}
	 * 
	 */
	Result<Boolean> validateCode(DeliveryCodeVModel model, ServiceContext context);

}
