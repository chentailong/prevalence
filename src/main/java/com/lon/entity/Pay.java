package com.lon.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ctl
 */
@Data
public class Pay {

    private BigDecimal money;

    private String title;

    private String remark;

    private Long addressBookId;

    private Integer payMethod;

    private String message;

}
