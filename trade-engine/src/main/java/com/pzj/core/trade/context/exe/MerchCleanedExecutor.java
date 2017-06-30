package com.pzj.core.trade.context.exe;

import org.springframework.stereotype.Component;

import com.pzj.core.trade.context.exe.base.TradeExecutor;
import com.pzj.core.trade.context.model.CleanMerchModel;
import com.pzj.framework.converter.JSONConverter;

@Component("merchCleanedExecutor")
public class MerchCleanedExecutor implements TradeExecutor<CleanMerchModel> {
	@Override
	public void execute(CleanMerchModel paramModel) {
		System.out.println(JSONConverter.toJson(paramModel));
	}
}
