package com.lon.dto;


import com.lon.entity.Goods;
import lombok.Data;

/**
 * @author ctl
 */

@Data
public class GoodsDto extends Goods {

    private String categoryName;

    private Integer copies;
}
