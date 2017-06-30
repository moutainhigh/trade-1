package com.pzj.trade.withdraw.event;

import java.util.ArrayList;
import java.util.List;

import com.pzj.trade.withdraw.nv.NameValue;

/**
 * 凑钱结果.
 * @author YRJ
 *
 */
public class RaisingResult {

    /**
     * 是否凑齐. true: 已凑齐; false: 未凑齐.
     */
    private boolean                         raised = false;

    /**
     * 还差多少.
     */
    private double                          margin = 0;

    /**
     * 借条. 表示谁借给你多少钱.
     */
    private List<NameValue<String, Double>> ious   = new ArrayList<NameValue<String, Double>>();

    public RaisingResult(double margin) {
        this.margin = margin;
    }

    public boolean isRaised() {
        return raised;
    }

    public double getMargin() {
        return margin;
    }

    public List<NameValue<String, Double>> getIous() {
        return ious;
    }

    /**
     * 打借条.
     * @param iou
     */
    //TODO 同步该算法效率会很低，是否可以考虑同步当前对象
    public synchronized void writeIOU(NameValue<String, Double> iou) {
        double tmp=iou.getValue();
        margin += -tmp;
        if (margin >= 0) {
            ious.add(iou);
        } else {
            ious.add(new NameValue<String, Double>(iou.getName(), iou.getValue() + margin));
        }
        if (margin <= 0) {
            this.raised = true;
        }
    }
}
