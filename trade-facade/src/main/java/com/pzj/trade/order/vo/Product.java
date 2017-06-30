package com.pzj.trade.order.vo;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7005160799882482845L;
    /** 产品ID 必填*/
    private long              productId;
    /** 产品数量 必填*/
    private int               num;
    /** 产品名称 必填*/
    private String            name;
    /** 供应商ID 必填*/
    private long              supplierId;

    /** 是否为组合商品 */
    private boolean           combined;

    /** 是否需要确认 */
    private boolean           confirm;

    /** 渠道ID 必填*/
    private long              channelId;

    /** 产品类别1: 景区; 2: 票; 3: 住宿; 4: 小交通; 5: 特产;6:一日游  必填*/
    private int               product_type;

    /** 商品服务属性 */
    private long              voucherId;

    private List<Product>     products;
    /** 政策 必填*/
    private List<Strategy>    strategies;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public boolean isCombined() {
        return combined;
    }

    public void setCombined(boolean combined) {
        this.combined = combined;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public int getProduct_type() {
        return product_type;
    }

    public void setProduct_type(int product_type) {
        this.product_type = product_type;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Strategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<Strategy> strategies) {
        this.strategies = strategies;
    }
}
