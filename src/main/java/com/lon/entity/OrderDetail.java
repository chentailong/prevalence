package com.lon.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细
 *
 * @author ctl
 */
@Data
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 订单id
     */
    private String orderId;


    /**
     * 商品id
     */

    private Long matterId;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 图片
     */
    private String image;
}
