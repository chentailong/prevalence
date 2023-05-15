package com.lon.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lon.common.BaseContext;
import com.lon.common.ResultMsg;
import com.lon.entity.Goods;
import com.lon.entity.ShoppingCart;
import com.lon.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ctl
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
@Api(tags = "购物车接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    /**
     * 查看购物车
     *
     * @return {@link ResultMsg}<{@link List}<{@link ShoppingCart}>>
     */
    @GetMapping("/list")
    @ApiOperation(value = "查看购物车")
    public ResultMsg<List<ShoppingCart>> list() {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        wrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = cartService.list(wrapper);
        return ResultMsg.success(list);
    }


    /**
     * 清空购物车
     *
     * @return {@link ResultMsg}<{@link String}>
     */
    @DeleteMapping("/clean")
    @ApiOperation(value = "清空购物车")
    public ResultMsg<String> clean() {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        cartService.remove(wrapper);
        return ResultMsg.success("清空购物车成功");
    }


    /**
     * 删除信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/del")
    @ApiOperation(value = "删除信息")
    public ResultMsg<String> delete(@RequestParam List<Long> ids) {
        boolean flo = false;
        for (Long id : ids) {
            LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ShoppingCart::getId, id);
            flo = cartService.remove(wrapper);
        }
        if (flo) {
            return ResultMsg.success("删除成功");
        } else {
            return ResultMsg.error("'删除失败");
        }
    }


    /**
     * 新增商品至购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增商品至购物车")
    public ResultMsg<ShoppingCart> addCart(@RequestBody ShoppingCart shoppingCart) {
        //设置用户ID，指定当前是哪位用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        //查询当前商品是否在购物车中
        Long matterId = shoppingCart.getMatterId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, currentId);
        wrapper.eq(ShoppingCart::getMatterId, matterId);
        ShoppingCart cartServiceOne = cartService.getOne(wrapper);
        if (cartServiceOne != null) {
            //如果有 在则在原来的基础上数量加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            //将整形数值赋值给BigDecimal
            BigDecimal number1 = BigDecimal.valueOf(cartServiceOne.getNumber());
            BigDecimal price1 = cartServiceOne.getPrice();
            //调用方法乘法计算小计
            cartServiceOne.setSubtotal(number1.multiply(price1));
            cartService.updateById(cartServiceOne);
        } else {   //否则新增数据一条数据
            shoppingCart.setNumber(1);
            //调用方法乘法计算小计
            shoppingCart.setSubtotal(shoppingCart.getPrice());
            shoppingCart.setCreateTime(LocalDateTime.now());
            cartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return ResultMsg.success(cartServiceOne);
    }

    /**
     * 修改物资信息
     *
     * @param cart
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改购物车信息")
    public ResultMsg<List<ShoppingCart>> update(@RequestBody ShoppingCart cart) {
        cart.setUpdateTime(LocalDateTime.now());
        BigDecimal number = BigDecimal.valueOf(cart.getNumber());
        cart.setSubtotal(number.multiply(cart.getPrice()));
        boolean fol = cartService.updateById(cart);
        if (fol) {
            LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            wrapper.orderByAsc(ShoppingCart::getCreateTime);
            List<ShoppingCart> list = cartService.list(wrapper);
            return ResultMsg.success(list);
        } else {
            return ResultMsg.error("购物车信息修改失败");
        }
    }

}
