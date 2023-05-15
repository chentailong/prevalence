package com.lon.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lon.common.ResultMsg;
import com.lon.dto.GoodsDto;
import com.lon.entity.Category;
import com.lon.entity.Goods;
import com.lon.service.CategoryService;
import com.lon.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author ctl
 */
@RestController
@RequestMapping("/goods")
@Slf4j
@Api(tags = "商品信息接口")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;


    /**
     * 查
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @param name     标题
     * @return {@link ResultMsg}<{@link Page}>
     */
    @GetMapping("/page")
    @ApiOperation(value = "根据分页查找物资")
    public ResultMsg<Page> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page<Goods> pageInfo = new Page(page, pageSize);
        Page<GoodsDto> goodsDtoPage = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper();
        //筛选
        wrapper.like(StringUtils.isNotEmpty(name), Goods::getName, name);
        //判断物资是否被删除
        wrapper.eq(Goods::getIsDeleted, 0);
        //添加排序条件
        wrapper.orderByDesc(Goods::getUpdateTime);
        //执行查询
        goodsService.page(pageInfo, wrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, goodsDtoPage, "records");
        List<Goods> records = pageInfo.getRecords();

        List<GoodsDto> list = records.stream().map((record) -> {
            GoodsDto goodsDto = new GoodsDto();
            BeanUtils.copyProperties(record, goodsDto);
            Long categoryId = record.getCategoryId();
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                goodsDto.setCategoryName(categoryName);
            }
            return goodsDto;
        }).collect(Collectors.toList());

        goodsDtoPage.setRecords(list);

        return ResultMsg.success(goodsDtoPage);
    }

    /**
     * 统计商品数量
     */
    @GetMapping("/count")
    @ApiOperation(value = "统计商品数量")
    public ResultMsg<Integer> count() {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        int count = goodsService.count(queryWrapper);
        if (count != 0) {
            return ResultMsg.success(count);
        } else {
            return ResultMsg.error("没有统计到住户信息");
        }
    }


    /**
     * 修改状态
     *
     * @param status
     * @param ids
     * @return {@link ResultMsg}<{@link String}>
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改物资状态")
    public ResultMsg<String> updateStatus(@PathVariable int status, Long[] ids) {
        for (Long id : ids) {
            Goods goods = goodsService.getById(id);
            Goods goodsInfo = new Goods();
            BeanUtils.copyProperties(goods, goodsInfo);
            LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Goods::getId, ids);
            goodsInfo.setStatus(status);
            goodsInfo.setUpdateTime(LocalDateTime.now());
            goodsService.updateById(goodsInfo);
        }
        return ResultMsg.success("状态修改成功");
    }


    /**
     * 修改物资信息
     *
     * @param goods
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改物资")
    public ResultMsg<String> update(@RequestBody Goods goods) {
        goods.setUpdateTime(LocalDateTime.now());
        boolean fol = goodsService.updateById(goods);
        if (fol) {
            return ResultMsg.success("物资信息修改成功");
        } else {
            return ResultMsg.error("物资信息修改失败");
        }
    }

    /**
     * 根据id删除物资信息
     */
    @DeleteMapping("/del")
    @ApiOperation(value = "删除物资")
    public ResultMsg<String> delete(@RequestParam List<Long> ids) {
        for (Long id : ids) {
            Goods goods = goodsService.getById(id);
            Goods goodsInfo = new Goods();
            BeanUtils.copyProperties(goods, goodsInfo);
            LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Goods::getId, ids);
            goodsInfo.setIsDeleted(1);
            goodsInfo.setUpdateTime(LocalDateTime.now());
            goodsService.updateById(goodsInfo);
        }
        return ResultMsg.success("删除成功");
    }


    /**
     * 增
     *
     * @param goods
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增物资")
    public ResultMsg<String> save(@RequestBody Goods goods) {
        boolean save = goodsService.save(goods);
        if (save) {
            return ResultMsg.success("新增物资信息成功");
        } else {
            return ResultMsg.error("新增物资信息失败");
        }
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取出售商品信息")
    public ResultMsg<List<Goods>> list() {
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getStatus, 1);
        wrapper.eq(Goods::getIsDeleted,0);
        List<Goods> list = goodsService.list(wrapper);
        return ResultMsg.success(list);
    }

}
