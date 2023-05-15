package com.lon.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lon.common.BaseContext;
import com.lon.common.ResultMsg;
import com.lon.dto.OrderDto;
import com.lon.entity.Orders;
import com.lon.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器
 *
 * @author ctl
 * @date 2022/09/18
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Api(tags = "查询订单")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;


    @GetMapping("/pages")
    @ApiOperation(value = "查询用户相关订单")
    public ResultMsg<IPage<OrderDto>> page(int page, int pageSize) {
        Long id = BaseContext.getCurrentId();
        Page<OrderDto> pageInfo = new Page<>(page, pageSize);
        IPage<OrderDto> orderList = ordersService.findPage(pageInfo, new QueryWrapper<>(), id);
        return ResultMsg.success(orderList);
    }


    @GetMapping("/allPages")
    @ApiOperation(value = "查询所有订单信息")
    public ResultMsg<IPage<OrderDto>> pages(int page, int pageSize) {
        Long id = BaseContext.getCurrentId();
        Page<OrderDto> pageInfo = new Page<>(page, pageSize);
        IPage<OrderDto> orderList = ordersService.findAllPage(pageInfo, new QueryWrapper<>());
        return ResultMsg.success(orderList);
    }

    @PutMapping("/update")
    public ResultMsg<String> updateStatus(@RequestBody Orders orders) {
        if (2 == orders.getStatus() || 3 == orders.getStatus()) {
            orders.setStatus(orders.getStatus() + 1);
        } else if (orders.getStatus() == 7) {
            orders.setStatus(4);
        } else if (orders.getStatus() == 8) {
            orders.setStatus(5);
        }
        boolean flo = ordersService.updateById(orders);
        if (flo) {
            return ResultMsg.success("成功");
        } else {
            return ResultMsg.error("失败");
        }
    }
}
