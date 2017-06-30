package com.pzj.core.trade.order.engine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.voucher.entity.VoucherBasicEntity;
import com.pzj.core.trade.voucher.read.VoucherReadMapper;
import com.pzj.trade.merch.entity.MerchEntity;
import com.pzj.trade.order.entity.OrderEntity;
import com.pzj.trade.order.model.PrintMerchDetailRespModel;
import com.pzj.trade.order.model.PrintOrderDetailRespModel;
import com.pzj.trade.order.read.MerchReadMapper;
import com.pzj.trade.order.read.OrderReadMapper;

/**
 * 票打印查询引擎.
 * @author YRJ
 *
 */
@Component(value = "printTicketQueryEngine")
public class PrintTicketQueryEngine {

	@Resource(name = "orderReadMapper")
	private OrderReadMapper orderReadMapper;

	@Resource(name = "merchReadMapper")
	private MerchReadMapper merchReadMapper;

	@Resource(name = "voucherReadMapper")
	private VoucherReadMapper voucherReadMapper;

	public PrintOrderDetailRespModel queryTicketDetailByOrderId(String orderId) {

		//1. 获取订单信息, 检测是否可支持打印票.
		OrderEntity orderEntity = orderReadMapper.getOrderById(orderId);
		if (orderEntity == null) {
			return null;
		}

		//2. 获取商品信息, 只有商品信息存在的情况下打印票据才有意义.
		List<MerchEntity> merchEntities = merchReadMapper.getMerchByOrderId(orderId);
		if (merchEntities == null || merchEntities.isEmpty()) {
			return null;
		}

		//3. 将商品转换为可打印的票据, 其中涉及到二位码信息, 需要从凭证中获取.
		List<PrintMerchDetailRespModel> merchs = new ArrayList<PrintMerchDetailRespModel>();
		for (MerchEntity merchEntity : merchEntities) {
			PrintMerchDetailRespModel merch = toTotick(merchEntity);

			String qrCode = getQrCode(merchEntity.getVoucher_id());
			merch.setQrCode(qrCode);
			merchs.add(merch);
		}

		//4. 当为演艺产品时, 加载该票据对应的场次、区域及座位信息.
		// 为了减少网络传输, 此处采用批量获取方式, 并将返回值再按产品维度进行拆分.

		PrintOrderDetailRespModel respModel = new PrintOrderDetailRespModel();
		respModel.setOrderId(orderId);
		respModel.setTotalAmount(BigDecimal.valueOf(orderEntity.getTotal_amount() - orderEntity.getRefund_amount()));
		respModel.setPrintMerchDetailRespModel(merchs);
		return respModel;
	}

	/**
	 * 票据的转换, 从商品转换为可打印的票据.
	 * @param merchEntity
	 * @param merch
	 */
	private PrintMerchDetailRespModel toTotick(MerchEntity merchEntity) {
		PrintMerchDetailRespModel merch = new PrintMerchDetailRespModel();
		merch.setMerchId(merchEntity.getMerch_id());
		merch.setProductId(merchEntity.getProduct_id());
		merch.setMerchName(merchEntity.getMerch_name());
		merch.setTotalNum(merchEntity.getTotal_num() - merchEntity.getRefund_num());//原则上讲, 此处不需要减去退款数量.
		merch.setTravelTime(merchEntity.getStart_time());//话说, 游玩日期的名称叫做start_time, 也够业余的.
		return merch;
	}

	/**
	 * 从凭证中获取二维码信息.
	 * @param voucherId
	 * @return
	 */
	private String getQrCode(long voucherId) {
		VoucherBasicEntity voucher = voucherReadMapper.queryVoucherBasicById(voucherId);
		if (voucher == null) {
			return null;
		}
		return voucher.getVoucher_content();
	}
}
