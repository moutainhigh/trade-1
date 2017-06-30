package com.pzj.voucher.lineVo;

import java.io.Serializable;
import java.util.Date;

/**
 * 景区产品凭据信息
 * @author YRJ
 *
 */
public class ScenicPruductVoucherVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 散票 0 ，团票  1  */
    private Integer           ticketVarie      = 0;

    /**景区名称 */
    private String            scenic;

    /** 景区ID */
    private String            scenicId;

    /** 游玩开始日期 */
    private Date              startTime;

    /** 游玩结束日期 */
    private Date              endTime;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
