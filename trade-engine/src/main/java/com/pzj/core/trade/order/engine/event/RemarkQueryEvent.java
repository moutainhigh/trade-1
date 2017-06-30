package com.pzj.core.trade.order.engine.event;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pzj.trade.order.entity.RemarkEntity;
import com.pzj.trade.order.entity.RemarkResponse;
import com.pzj.trade.order.model.OrderRemarksPageReqModel;
import com.pzj.trade.order.read.RemarkReadMapper;

/**
 * 备注查询事件.
 * @author YRJ
 *
 */
@Component(value = "remarkQueryEvent")
public class RemarkQueryEvent {

	@Resource(name = "remarkReadMapper")
	private RemarkReadMapper remarkReadMapper;

	public List<RemarkResponse> queryRemarkByOrderId(String orderId) {
		// 查询备注
		final List<RemarkEntity> remarks = remarkReadMapper.getRemarkByOrderId(orderId);
		if (remarks == null) {
			return null;
		}
		return buildRemarkResponses(remarks);
	}

	public List<RemarkResponse> queryRemarkPageByOrderVO(OrderRemarksPageReqModel orderVO) {
		int pageIndex=(orderVO.getPageNum()-1)*orderVO.getPageSize();
		final List<RemarkEntity> remarks = remarkReadMapper.queryRemarkPageByOrderVO(orderVO,pageIndex,orderVO.getPageSize());
		if (remarks == null) {
			return null;
		}
		return buildRemarkResponses(remarks);
	}

	public int queryRemarkPageTotalByOrderId(long orderId) {
		return remarkReadMapper.queryRemarkPageTotalByOrderId(orderId);
	}
	
	private List<RemarkResponse> buildRemarkResponses(List<RemarkEntity> remarks){
		List<RemarkResponse> remarkResps = new ArrayList<RemarkResponse>();
		for (RemarkEntity remark : remarks) {
			RemarkResponse remarkResp = new RemarkResponse();
			remarkResp.setOrder_id(remark.getOrder_id());
			remarkResp.setRemark(remark.getRemark());
			remarkResp.setOperator_id(remark.getOperator_id());
			remarkResp.setOperator_type(remark.getOperator_type());
			remarkResp.setCreate_time(remark.getCreate_time());
			remarkResp.setOperator_name(remark.getOperator_name());
			remarkResps.add(remarkResp);
		}
		return remarkResps;
	}

}
