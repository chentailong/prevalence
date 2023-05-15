package com.lon.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lon.dto.OrderDto;
import com.lon.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 订单映射器
 *
 * @author ctl
 * @date 2022/09/18
 */
@Mapper
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {
    /**
     * 与数据库交互,查询用户订单详情
     *
     * @param page
     * @param wrapper
     * @param id
     * @return
     */
    IPage<OrderDto> findPage(IPage<OrderDto> page, @Param(Constants.WRAPPER) QueryWrapper<OrderDto> wrapper, @Param("id") Long id);


    /**
     * 查询所有订单信息
     * @param page
     * @param wrapper
     * @return
     */
    IPage<OrderDto> findAllPage(IPage<OrderDto> page, @Param(Constants.WRAPPER) QueryWrapper<OrderDto> wrapper);
}
