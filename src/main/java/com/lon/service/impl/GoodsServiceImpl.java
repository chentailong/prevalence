package com.lon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.entity.Goods;
import com.lon.mapper.GoodsMapper;
import com.lon.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 * @author ctl
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

}
