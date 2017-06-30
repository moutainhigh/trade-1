package com.pzj.trade.book.service;

import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.trade.book.model.BookCancelModel;
import com.pzj.trade.book.model.BookEditModel;
import com.pzj.trade.book.model.BookInModel;
import com.pzj.trade.book.model.BookResponse;
import com.pzj.trade.book.model.SparpreisCheckModel;

/**
 * 预订单接口
 * @author YRJ
 *
 */
public interface BookService {

	/**
	 * @api {API} com.pzj.trade.book.service.BookService#createBook 创建预约单,免票特价票，前置订单
	 * @apiGroup bookService
	 * @apiName 创建预约单2
	 * @apiDescription 创建预约单
	 * @apiVersion 1.0.0
	 * 
	 * 
	 * @apiParam {BookInModel} model 预约单信息
	 * @apiParam {ServiceContext} context 
	 * 
	 * @apiUse BookInModel 
	 * @apiUse ContacteeModel
	 * @apiUse FilledModel
	 * @apiUse BookProductModel
	 * @apiUse TouristModel
	 * @apiUse ServiceContext
	 * @apiParamExample 参数举例
	 * {"model":{"resellerId":"12345617","operatorId":2216619736763787,"travel":2216619736764717,"travelDepartId":2216613736765112,
	 * "guideId": 2216619736563713,"travelDate":1488346614490,"deliveryCode":"MF12345",
	 * "contactees":[{}],"filledModelList":[{}],"products":[{}],"remark":"示例"},
	 * "context":{}}
	 * 
	 * @apiSuccess (返回) Result<BookResponse> result 预订单信息
	 * 
	 * @apiSuccessExample 正确返回
	 *  {"code":10000,data:["bookId": "MF1000447348"}
	 * 
	 */
	Result<BookResponse> createBook(BookInModel model, ServiceContext context);

	/**
	 * @api {API} com.pzj.trade.book.service.BookService#cancel 取消预约单/特价票免票
	 * @apiGroup bookService
	 * @apiName 取消预约单/特价票免票
	 * @apiDescription 取消预约单/特价票免票
	 * @apiVersion 1.0.0
	 * 
	 * @apiParam {BookCancelModel} model 预约单取消入参
	 * @apiParam {ServiceContext} context
	 * @apiUse BookCancelModel
	 * @apiUse ServiceContext
	 * @apiParamExample 参数举例
	 * {"bookId":1783274892}
	 * 
	 * @apiSuccess (返回) Result<Boolean> result 是否取消成功
	 * 
	 * @apiSuccessExample 正确返回
	 *  {"code":10000,data:{true}}
	 * 
	 */
	Result<Boolean> cancel(BookCancelModel model, ServiceContext context);

	/**
	 * @api {API} com.pzj.trade.book.service.BookService#editBooker 更新预约单
	 * @apiGroup bookService
	 * @apiName 更新预约单3
	 * @apiDescription 更新预约单
	 * @apiVersion 1.0.0
	 * 
	 * @apiParam {BookEditModel} model 预订单信息
	 * @apiParam {ServiceContext} context
	 * @apiUse BookEditModel
	 * @apiUse ContacteeModel
	 * @apiUse FilledModel
	 * @apiUse BookProductModel
	 * @apiUse TouristModel
	 * @apiUse ServiceContext
	 * @apiParamExample 参数举例
	 * {"model":{"bookId":38294830948,"booker":"12345617","operatorId":2216619736763787,"travel":2216619736764717,"travelDepartId":2216613736765112,
	 * "guideId": 2216619736563713,"travelDate":1488346614490,"deliveryCode":"MF12345",
	 * "contactees":[{}],"filledModelList":[{}],"products":[{}],"remark":"示例"},
	 * "context":{}}
	 * 
	 * @apiSuccess (返回) Result<BookResponse> result 预订单信息
	 * 
	 * @apiSuccessExample 正确返回
	 *  {"code":10000,data:["bookId": "MF1000447348"}
	 * 
	 */
	Result<BookResponse> editBooker(BookEditModel model, ServiceContext context);

	/**
	 * @api {API} com.pzj.trade.book.service.BookService#audit 免票、特价票审核通过
	 * @apiGroup bookService
	 * @apiName 免票、特价票审核通过1
	 * @apiDescription 免票、特价票审核通过
	 * @apiVersion 1.0.0
	 * 
	 * @apiParam {SparpreisCheckModel} model 免票、特价票审核信息
	 * @apiParam {ServiceContext} context
	 * @apiUse SparpreisCheckModel
	 * @apiUse ServiceContext
	 * @apiParamExample 参数举例
	 * {"model":{"bookId":1234561723,"operatorId":2216619736563728},
	 * "context":{}}
	 * 
	 * @apiSuccess (返回) Result<Boolean> result 预订单信息
	 * 
	 * @apiSuccessExample 正确返回
	 *  {"code":10000,data:true}
	 * 
	 */
	Result<Boolean> audit(SparpreisCheckModel model, ServiceContext context);

	/**
	 * @api {API} com.pzj.trade.book.service.BookService#refuse 免票、特价票审核拒绝
	 * @apiGroup bookService
	 * @apiName 免票、特价票审核拒绝2
	 * @apiDescription 免票、特价票审核拒绝
	 * @apiVersion 1.0.0
	 * 
	 * @apiParam {SparpreisCheckModel} model 免票、特价票审核信息
	 * @apiParam {ServiceContext} context
	 * @apiUse SparpreisCheckModel
	 * @apiUse ServiceContext
	 * @apiParamExample 参数举例
	 * {"model":{"bookId":1234561723,"operatorId":2216619736563728,"reason":"产品价格有问题"},
	 * "context":{}}
	 * 
	 * @apiSuccess (返回) Result<Boolean> result 预订单信息
	 * 
	 * @apiSuccessExample 正确返回
	 *  {"code":10000,data:true}
	 * 
	 */
	Result<Boolean> refuse(SparpreisCheckModel model, ServiceContext context);

	/**
	 * @api {API} com.pzj.trade.book.service.BookService#cancelPreBook 取消预约单的前置订单
	 * @apiGroup bookService
	 * @apiName 取消预约单的前置订单
	 * @apiDescription 取消预约单的前置订单
	 * @apiVersion 1.0.0
	 * 
	 * @apiParam {BookCancelModel} model 取消预约单的前置订单入参
	 * @apiParam {ServiceContext} context
	 * @apiUse BookCancelModel
	 * @apiUse ServiceContext
	 * @apiParamExample 参数举例
	 * {"bookId":1783274892}
	 * 
	 * @apiSuccess (返回) Result<Boolean> result 是否取消成功
	 * 
	 * @apiSuccessExample 正确返回
	 *  {"code":10000,data:{true}}
	 * 
	 */
	Result<Boolean> cancelPreBook(BookCancelModel model, ServiceContext context);

}
