package com.lon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.common.CustomException;
import com.lon.entity.Category;
import com.lon.entity.Goods;
import com.lon.mapper.CategoryMapper;
import com.lon.service.CategoryService;
import com.lon.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ctl
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private GoodsService goodsService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Goods> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Goods::getCategoryId, id);
        int count1 = goodsService.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
            //已经关联菜品,抛出一个业务异常
            throw new CustomException("当前分类关联了菜品,不能删除");
        }
        super.removeById(id);
    }
}
