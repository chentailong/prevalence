package com.lon.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lon.common.ResultMsg;
import com.lon.entity.OrderDetail;
import com.lon.service.OrderDetailService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ctl
 */
@Slf4j
@RestController
@RequestMapping("/orderDetail")

public class OrderDetailController {

    @Autowired
    private OrderDetailService detailService;

    @GetMapping("/list")
    @ApiOperation(value = "查询订单号的所有信息")
    private ResultMsg<List<OrderDetail>> list(Long orderId) {
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId, orderId);
        List<OrderDetail> detailList = detailService.list(wrapper);
        if (detailList != null) {
            return ResultMsg.success(detailList);
        } else {
            return ResultMsg.error("数据查询失败");
        }
    }
}