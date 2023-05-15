package com.lon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.entity.ShoppingCart;
import com.lon.mapper.ShoppingCartMapper;
import com.lon.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author ctl
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
