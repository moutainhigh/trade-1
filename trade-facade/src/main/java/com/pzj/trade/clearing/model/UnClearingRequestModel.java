package com.pzj.trade.clearing.model;

import com.pzj.framework.entity.PageableRequestBean;

/**
 * 未清算记录请求参数.
 * @author YRJ
 *
 */
public class UnClearingRequestModel extends PageableRequestBean {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder tostr = new StringBuilder(UnClearingRequestModel.class.getSimpleName());
        tostr.append("[currentPage=").append(getCurrentPage());
        tostr.append(", pageIndex").append(getPageIndex());
        tostr.append(", pageSize=").append(getPageSize());
        tostr.append("]");
        return tostr.toString();
    }
}
