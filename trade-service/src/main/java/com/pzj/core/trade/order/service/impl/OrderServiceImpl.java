package com.pzj.core.trade.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzj.core.trade.agent.engine.AgentOrderEngine;
import com.pzj.core.trade.book.engine.BookQueryEngine;
import com.pzj.core.trade.book.engine.converter.BookJsonConverter;
import com.pzj.core.trade.book.engine.model.BookJsonEModel;
import com.pzj.core.trade.book.engine.model.OrderBook;
import com.pzj.core.trade.book.resolver.StockResponseResolver;
import com.pzj.core.trade.exception.TradeException;
import com.pzj.core.trade.order.build.MerchFilter;
import com.pzj.core.trade.order.build.OrderResovler;
import com.pzj.core.trade.order.engine.OrderGenerateEngine;
import com.pzj.core.trade.order.engine.OrderRemarkEngine;
import com.pzj.core.trade.order.engine.RemarkEngine;
import com.pzj.core.trade.order.engine.TradeOrderEngine;
import com.pzj.core.trade.order.engine.common.PayWayEnum;
import com.pzj.core.trade.order.engine.exception.OrderException;
import com.pzj.core.trade.order.engine.exception.OrderStockException;
import com.pzj.core.trade.order.engine.model.MerchBaseModel;
import com.pzj.core.trade.order.engine.model.MerchModel;
import com.pzj.core.trade.order.validator.OrderArgsCheck;
import com.pzj.core.trade.order.validator.OrderRemarkArgsValidator;
import com.pzj.framework.context.Result;
import com.pzj.framework.context.ServiceContext;
import com.pzj.framework.converter.JSONConverter;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.framework.exception.ServiceException;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.entity.OrderResponse;
import com.pzj.trade.order.entity.TradingOrderEntity;
import com.pzj.trade.order.model.MultiOrderInModel;
import com.pzj.trade.order.model.OrderRemarkModel;
import com.pzj.trade.order.service.OrderService;
import com.pzj.trade.order.vo.AgentOrderVO;
import com.pzj.trade.order.vo.OrderRemarkVO;
import com.pzj.trade.order.vo.PurchaseProductVO;
import com.pzj.trade.order.vo.TradeOrderVO;
import com.pzj.trade.payment.entity.PaymentResult;
import com.pzj.trade.payment.service.PaymentService;
import com.pzj.trade.payment.vo.PaymentVO;

/**
 *
 * @author CHJ
 *
 */
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

	private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Resource(name = "orderArgsValidater")
	private ObjectConverter<TradeOrderVO, Void, Void> orderArgsValidater;

	@Resource(name = "merchModelFilter")
	private ObjectConverter<List<PurchaseProductVO>, Void, List<MerchModel>> merchModelFilter;

	@Resource(name = "strategyFilter")
	private ObjectConverter<TradeOrderVO, List<MerchModel>, Void> strategyFilter;

	@Resource(name = "saleOrderResolver")
	private ObjectConverter<TradeOrderVO, List<MerchModel>, TradingOrderEntity> saleOrderResolver;

	@Resource(name = "purchOrderResolver")
	private ObjectConverter<TradingOrderEntity, List<MerchModel>, List<TradingOrderEntity>> purchOrderResolver;

	@Resource(name = "tradeOrderEngine")
	private TradeOrderEngine tradeOrderEngine;

	@Autowired
	private OrderArgsCheck orderArgsCheck;

	@Autowired
	private MerchFilter merchFilter;

	@Autowired
	private OrderResovler orderResovler;

	@Autowired
	private OrderGenerateEngine orderGenerateEngine;

	@Autowired
	private OrderRemarkArgsValidator orderRemarkArgsValidator;

	@Autowired
	private OrderRemarkEngine orderRemarkEngine;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private BookQueryEngine bookQueryEngine;

	@Override
	@Deprecated
	public Result<OrderResponse> createOrder(final TradeOrderVO reqModel, final ServiceContext context) {
		try {
			//1. 验证下单参数的合法性.
			orderArgsValidater.convert(reqModel, null);

			final String args = JSONConverter.toJson(reqModel);
			logger.info("交易创建参数: createOrderModel:" + args);

			//2. 根据购买的商品构建商品模型、政策、返利等信息.
			final List<MerchModel> merchModels = merchModelFilter.convert(reqModel.getProducts(), null);
			strategyFilter.convert(reqModel, merchModels);

			//filterChain.doFilter(merchModels);

			//3. 订单构建流程.
			final TradingOrderEntity order = saleOrderResolver.convert(reqModel, merchModels);
			final List<TradingOrderEntity> purchs = purchOrderResolver.convert(order, merchModels);

			final OrderResponse resp = tradeOrderEngine.doHandler(order, purchs);
			logger.info("交易创建成功, reqModel: " + args);

			directWithoutPay(context, resp);

			return new Result<OrderResponse>(resp);

		} catch (final Throwable e) {
			logger.error("交易创建失败, reqModel: " + (JSONConverter.toJson(reqModel)), e);

			if (!(e instanceof OrderException)) {
				throw new ServiceException("交易创建失败.", e);
			}

			//stockOperateEngine.rollbackSeatAndStock(transaction_id);

			final OrderException ex = (OrderException) e;
			return new Result<OrderResponse>(ex.getErrCode(), ex.getMessage());
		}
	}

	/**
	 * 直签免支付特殊处理
	 * @param context
	 * @param resp
	 */
	private void directWithoutPay(final ServiceContext context, final OrderResponse resp) {
		if (resp.getOnline_pay() == 0) {
			final PaymentVO payment = new PaymentVO();
			payment.setOrderId(resp.getOrderId());
			payment.setResellerId(resp.getReseller_id());
			payment.setPayType(0);
			final Result<PaymentResult> result = paymentService.payOrder(payment, context);
			if (!result.isOk()) {
				logger.error("直签免支付支付失败:" + JSONConverter.toJson(result));
				throw new OrderException(result.getErrorCode(), result.getErrorMsg());
			}
		}
	}

	@Autowired
	private RemarkEngine remarkEngine;

	@Override
	public Result<Boolean> createOrderRemark(final OrderRemarkVO orderRemarkVO, final ServiceContext context) {

		logger.info("添加备注, 参数->" + orderRemarkVO);

		try {
			remarkEngine.createRemark(orderRemarkVO);
		} catch (final Throwable e) {
			logger.error("添加备注失败, 参数->" + orderRemarkVO, e);

			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new ServiceException("备注添加失败");
		}

		logger.info("添加备注成功, 参数->" + orderRemarkVO);
		return new Result<Boolean>(Boolean.TRUE);
	}

	@Resource(name = "agentOrderArgsValidator")
	private ObjectConverter<AgentOrderVO, Void, Void> agentOrderArgsValidator;

	@Autowired
	private AgentOrderEngine agentOrderEngine;

	@Override
	@Deprecated
	public Result<Boolean> updateAgentOrderId(final AgentOrderVO reqModel, final ServiceContext context) {

		try {
			agentOrderArgsValidator.convert(reqModel, null);
			agentOrderEngine.doHandler(reqModel);
		} catch (final Throwable e) {
			logger.error("代下单失败, 订单[" + reqModel.getOrder_id() + "], 第三方订单[" + reqModel.getAgent_order_id() + "].");//此代码存在bug.

			if (e instanceof TradeException) {
				//
				//				final TradeException ex = (TradeException) e;
				//				return new Result<Boolean>(ex.getErrCode(), ex.getMessage());
				return new Result<Boolean>();
			}

			throw new ServiceException(
					"代下单失败, 订单[" + reqModel.getOrder_id() + "], 第三方订单[" + reqModel.getAgent_order_id() + "].", e);

		}
		return new Result<Boolean>();
	}

	@Override
	public Result<Boolean> updateOrderRemark(final OrderRemarkModel orderRemarkModel, final ServiceContext context) {
		try {
			orderRemarkArgsValidator.convert(orderRemarkModel, null);
			orderRemarkEngine.doHandler(orderRemarkModel);
		} catch (final Throwable e) {
			logger.error("修改备注失败" + orderRemarkModel.getOrder_id() + ";" + orderRemarkModel.getRemark());//此代码存在bug.

			if (e instanceof TradeException) {
				return new Result<Boolean>();
			}
			throw new ServiceException("修改备注失败, 订单[" + orderRemarkModel.getOrder_id() + "]" + "].", e);
		}
		return new Result<Boolean>();
	}

	@Override
	public Result<OrderResponse> createMultilevelOrder(final MultiOrderInModel orderInModel,
			final ServiceContext context) {
		try {
			logger.info("创建订单参数:" + JSONConverter.toJson(orderInModel));
			//1参数验证
			orderArgsCheck.check(orderInModel);

			//预约单验证,获取book type
			final OrderBook orderBook = bookQueryEngine.queryOrderBookByBookId(orderInModel.getBookId());
			String transaction_id = null;
			if (!Check.NuNObject(orderBook)) {
				orderInModel.setBookType(orderBook.getBook().getBookType());//查询产品政策时，需要该字段，是不是免票特价票
				transaction_id = orderBook.getBook().getTransactionId();
				BookJsonEModel json = BookJsonConverter.INTANCE.getBookJson(orderBook.getBook());
				orderInModel.setCheckinPoints(json.getCheckinPoints());
			}
			//2获取产品模型，政策模型
			final List<MerchBaseModel> merchs = merchFilter.assemble(orderInModel);
			//3产品是否可以购买
			merchFilter.filter(merchs, orderInModel);

			//4拆单，生成voucher
			final List<TradingOrderEntity> orders = orderResovler.resolve(orderInModel, merchs, transaction_id);

			//5创建订单，占库存，会写book，释放book库存
			final OrderResponse resp = orderGenerateEngine.doHandler(orders, orderBook, merchs);
			logger.info("创建订单成功, reqModel: " + orderInModel.toString() + "订单ID:" + resp.getOrderId());

			atOncePay(orders, resp);

			return new Result<OrderResponse>(resp);

		} catch (final OrderStockException e) {
			logger.error("创建订单失败,库存服务异常. reqModel: " + (JSONConverter.toJson(orderInModel)), e);
			Result<OrderResponse> result = new Result<OrderResponse>(e.getErrCode(), e.getMessage());
			result.setData(StockResponseResolver.INTANCE.convertOrderR(e.getStockException()));
			return result;

		} catch (final Throwable e) {
			logger.error("创建订单失败, reqModel: " + (JSONConverter.toJson(orderInModel)), e);

			if (!(e instanceof OrderException)) {
				throw new ServiceException("创建订单失败.", e);
			}

			final OrderException ex = (OrderException) e;
			return new Result<OrderResponse>(ex.getErrCode(), ex.getMessage());
		}
	}

	/**
	 * 
	 * @param orders
	 */
	private void atOncePay(final List<TradingOrderEntity> orders, final OrderResponse resp) {
		final TradingOrderEntity order = orders.get(0);
		if (order.getPay_way() == PayWayEnum.CASH.getPayWay() || order.getPay_way() == PayWayEnum.AFTER.getPayWay()) {
			final PaymentVO payment = new PaymentVO();
			payment.setOrderId(order.getOrder_id());
			payment.setResellerId(order.getReseller_id());
			payment.setPayType(0);
			final Result<PaymentResult> result = paymentService.payOrder(payment, null);
			if (!result.isOk()) {
				logger.error("现金或后付调用支付失败:" + JSONConverter.toJson(result));
				throw new OrderException(result.getErrorCode(), result.getErrorMsg());
			}
			resp.setOrderStatus(OrderStatusEnum.AlreadyPaid.getValue());
		}
	}
}
