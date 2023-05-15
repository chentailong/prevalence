package com.lon.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.kernel.util.JsonUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lon.common.BaseContext;
import com.lon.common.CustomException;
import com.lon.dto.OrderDto;
import com.lon.entity.*;
import com.lon.mapper.OrdersMapper;
import com.lon.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 订单服务
 *
 * @author ctl
 * @date 2022/09/18
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private UsersService userService;

    @Autowired
    private AddressBookService bookService;

    @Autowired
    private OrderDetailService detailService;

    @Autowired
    private OrdersMapper ordersMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(Object pay, String orderNumber, JSONArray parseMessage) {
        //获得当前用户ID
        Long currentId = BaseContext.getCurrentId();

        Orders orders = new Orders();

        List<ShoppingCart> cartList = parseMessage.toJavaList(ShoppingCart.class);

        //查询用户数据
        Users users = userService.getById(currentId);

        Pay pays = (Pay) pay;

        Long addressBookIds = pays.getAddressBookId();

        String remark = pays.getRemark();

        AddressBook addressBook = bookService.getById(addressBookIds);
        if (addressBook == null) {
            throw new CustomException("用户地址信息有误，不能下单");
        }

        /**
         * 订单号
         *
         * 计算总金额，安全
         */
        long orderId = IdWorker.getId();
        AtomicInteger amount = new AtomicInteger(0);

        //计算总金额
        List<OrderDetail> orderDetails = cartList.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderNumber);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setMatterId(item.getMatterId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setPrice(item.getPrice());
            amount.addAndGet(item.getPrice().multiply(new BigDecimal(item.getNumber())).intValue());
            cartService.removeById(item.getId());
            return orderDetail;
        }).collect(Collectors.toList());
        orders.setId(orderId);
        orders.setOrderNumber(String.valueOf(orderNumber));
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(currentId);
        orders.setRemark(remark);
//        orders.setUserName(users.getName());
        orders.setAddressBookId(addressBook.getId());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getDetail() == null ? "" : addressBook.getDetail()));
//        //向订单表插入数据，一条数
        this.save(orders);

        //向订单详情表插入数据，可能是多条数据
        detailService.saveBatch(orderDetails);

        //清空当前用户的购物车信息
//        cartService.remove(wrapper);

    }

    @Override
    public IPage<OrderDto> findPage(Page<OrderDto> page, QueryWrapper<OrderDto> queryWrapper, Long id) {
        return ordersMapper.findPage(page, queryWrapper, id);
    }


    @Override
    public IPage<OrderDto> findAllPage(Page<OrderDto> page, QueryWrapper<OrderDto> queryWrapper) {
        return ordersMapper.findAllPage(page, queryWrapper);
    }
}
