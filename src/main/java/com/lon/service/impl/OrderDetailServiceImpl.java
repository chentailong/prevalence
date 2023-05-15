package com.lon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.entity.OrderDetail;
import com.lon.mapper.OrderDetailMapper;
import com.lon.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * 订单细节服务impl
 *
 * @author ctl
 * @date 2022/09/18
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
