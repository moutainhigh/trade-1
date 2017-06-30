package com.pzj.voucher.lineVo;

import java.io.Serializable;
import java.util.Date;

public class TheaterProductVoucherVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 散票 0 ，团票  1  */
    private Integer           ticketVarie      = 0;

    /**景区名称 */
    private String            scenic;

    /** 景区ID */
    private String            scenicId;

    /** 产品ID */
    private Long              productId;

    /** 演出场次  */
    private String            screenings;

    /** 演出区域 */
    private String            region;

    /** 座位号、中间用,隔开 */
    private String            seatNumbers;

    /** 演出开始时间 */
    private Date              showStartTime;

    /** 演出结束时间  */
    private Date              showEndTime;

    public String getScreenings() {
        return screenings;
    }

    public void setScreenings(String screenings) {
        this.screenings = screenings;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(String seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public Integer getTicketVarie() {
        return ticketVarie;
    }

    public void setTicketVarie(Integer ticketVarie) {
        this.ticketVarie = ticketVarie;
    }

    public String getScenic() {
        return scenic;
    }

    public void setScenic(String scenic) {
        this.scenic = scenic;
    }

    public String getScenicId() {
        return scenicId;
    }

    public void setScenicId(String scenicId) {
        this.scenicId = scenicId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(Date showStartTime) {
        this.showStartTime = showStartTime;
    }

    public Date getShowEndTime() {
        return showEndTime;
    }

    public void setShowEndTime(Date showEndTime) {
        this.showEndTime = showEndTime;
    }

}
