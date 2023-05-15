package com.lon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lon.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单细节映射器
 *
 * @author ctl
 * @date 2022/09/18
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
