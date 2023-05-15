package com.lon.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lon.dto.OrderDto;
import com.lon.entity.Orders;

/**
 * 订单服务
 *
 * @author ctl
 * @date 2022/09/18
 */
public interface OrdersService extends IService<Orders> {


    /**
     * 提交订单数据
     * @param orderId
     * @param pay
     * @param parseMessage
     */
    void submit(Object pay, String orderId, JSONArray parseMessage);


    /**
     * 分页查询订单
     * @param page
     * @param queryWrapper
     * @param id
     * @return
     */
    IPage<OrderDto> findPage(Page<OrderDto> page, QueryWrapper<OrderDto> queryWrapper, Long id);


    /**
     * 分页查询所有订单信息
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<OrderDto> findAllPage(Page<OrderDto> page, QueryWrapper<OrderDto> queryWrapper);

}
