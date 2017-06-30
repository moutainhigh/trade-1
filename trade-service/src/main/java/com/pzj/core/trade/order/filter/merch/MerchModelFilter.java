package com.pzj.core.trade.order.filter.merch;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.model.MerchModel;
import com.pzj.framework.converter.ObjectConverter;
import com.pzj.trade.order.vo.PurchaseProductVO;

/**
 * 下单商品模型包装器.
 * <p>依据购买的商品信息, 从产品服务、政策服务获取商品当前业务特性、价格、返利信息.</p>
 * <p>根据获取的信息, 构建交易内部商品信息.</p>
 * @author YRJ
 *
 */
@Component(value = "merchModelFilter")
public final class MerchModelFilter implements ObjectConverter<List<PurchaseProductVO>, Void, List<MerchModel>> {

	@Resource(name = "merchModelBuilder")
	private ObjectConverter<Long, Void, MerchModel> merchModelBuilder;

	@Resource(name = "merchRuleBuilder")
	private ObjectConverter<MerchModel, Void, MerchModel> merchRuleBuilder;

	@Override
	public List<MerchModel> convert(List<PurchaseProductVO> products, Void y) {
		List<MerchModel> merchs = new ArrayList<MerchModel>();
		for (PurchaseProductVO purch : products) {
			MerchModel merch = new MerchModel(purch.getProductId(), purch.getPlayDate());
			MerchModel existMerch = getIdenticalMerch(purch.getProductId(), merchs);
			if (existMerch == null) {
				merch = merchModelBuilder.convert(purch.getProductId(), null);
				merch.setPlayDate(purch.getPlayDate());
				merchRuleBuilder.convert(merch, null);
			} else {
				merch = existMerch;
			}
			merch = copy(merch, purch);
			merchs.add(merch);
		}
		return merchs;
	}

	/**
	 * 从商品模型列表中随机获取一个相同的模型.
	 * @param productId
	 * @param merchs
	 * @return
	 */
	private MerchModel getIdenticalMerch(long productId, List<MerchModel> merchs) {
		for (MerchModel merch : merchs) {
			if (merch != null && (merch.getProdId() == productId)) {
				return merch;
			}
		}
		return null;
	}

	/**
	 * 拷贝商品模型基本属性、政策信息. 剔除掉凭证ID、购买数量信息.
	 * @param merchModel
	 * @return
	 */
	private MerchModel copy(MerchModel merchModel, PurchaseProductVO product) {
		MerchModel _merch = new MerchModel(merchModel.getProdId());
		_merch.setProdName(merchModel.getProdName());
		_merch.setProdType(merchModel.getProdType());
		_merch.setProdVarie(merchModel.getProdVarie());
		_merch.setChannelId(merchModel.getChannelId());
		_merch.setEffecTime(merchModel.getEffecTime());
		_merch.setExpireTime(merchModel.getExpireTime());
		_merch.setPlayDate(merchModel.getPlayDate());
		_merch.setOneVote(merchModel.getOneVote());
		_merch.setVourType(merchModel.getVourType());
		_merch.setClearType(merchModel.getClearType());
		_merch.setAgent(merchModel.getAgent());
		_merch.setNeedConfirm(merchModel.getNeedConfirm());
		_merch.setIsDock(merchModel.getIsDock());
		_merch.setPlayDate(merchModel.getPlayDate());

		_merch.setPrice(merchModel.getPrice());
		_merch.setSupplierId(merchModel.getSupplierId());
		//_merch.setPlayEndTime(merchModel.getPlayEndTime());
		_merch.setSupplierStrategy(merchModel.getSupplierStrategy());
		_merch.setResellerStrategy(merchModel.getResellerStrategy());

		//购买数量、凭证ID从请求参数中读取.
		_merch.setBuyNum(product.getProductNum());
		_merch.setVoucherId(product.getVoucherId());
		return _merch;
	}

}
