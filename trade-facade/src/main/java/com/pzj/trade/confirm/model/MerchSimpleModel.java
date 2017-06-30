package com.pzj.trade.confirm.model;

import java.io.Serializable;

/**
 * 商品简单模型，只包含产品ID和数量.
 * @author YRJ
 *
 */
public class MerchSimpleModel implements Serializable {

    private static final long serialVersionUID = 1L;
    /**产品ID */
    private long              product_id;
    /** 数量,总数减去退款数量*/
    private int               total_num;

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

}
