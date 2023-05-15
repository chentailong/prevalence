package com.lon.dto;

import com.lon.entity.Orders;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ctl
 */
@Data
public class OrderDto extends Orders {

    private String name;

    private String image;

    private Long matterId;

    private Integer number;

    private BigDecimal price;

}
