package com.lon.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ctl
 */
@Data
public class AliReturnPay  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 开发者的app_id
     */
    private String app_id;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 签名
     */
    private String sign;

    /**
     * 交易状态
     */
    private String trade_status;

    /**
     *  支付宝交易号
     */
    private String trade_no;

    /**
     * 交易的金额
     */
    private String total_amount;


    @Override
    public String toString() {
        return "AliReturnPayBean [app_id=" + app_id + ", out_trade_no=" + out_trade_no + ", sign=" + sign
                + ", trade_status=" + trade_status + ", trade_no=" + trade_no + "]";
    }
}
