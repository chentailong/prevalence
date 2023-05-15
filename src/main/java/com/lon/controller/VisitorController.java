package com.lon.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lon.common.ResultMsg;
import com.lon.entity.GoHome;
import com.lon.entity.Visitor;
import com.lon.service.VisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 访客信息控制器
 * @author ctl
 */

@Slf4j
@RestController
@RequestMapping("/visitor")
@Api(tags = "访客信息接口")
public class VisitorController {

    @Autowired
    private VisitorService service;

    /**
     * 新增访客
     * @param visitor
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增访客")
    public ResultMsg<String> save(@RequestBody Visitor visitor) {
        boolean save = service.save(visitor);
        if (save){
            return ResultMsg.success("新增访客信息成功");
        }else {
            return  ResultMsg.error("新增访客信息失败");
        }
    }


    /**
     * 根据id删除访客来往信息
     */
    @DeleteMapping("/del")
    @ApiOperation(value = "删除访客来往信息")
    public ResultMsg<String> delete(Long id) {
        boolean fol = service.removeById(id);
        if (fol) {
            return ResultMsg.success("删除信息成功");
        }else {
            return ResultMsg.error("删除失败");
        }
    }

    /**
     * 根据分页查找访客信息
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "根据分页查找访客信息")
    public ResultMsg<Page> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Visitor> wrapper = new LambdaQueryWrapper();
        //根据条件查找
        wrapper.like(StringUtils.isNotEmpty(name), Visitor::getName, name);
        //添加排序条件
        wrapper.orderByDesc(Visitor::getCreateTime);
        //执行查询
        service.page(pageInfo, wrapper);
        //返回结果
        return ResultMsg.success(pageInfo);
    }

    /**
     * 统计访客人数
     */
    @GetMapping("/count")
    @ApiOperation(value = "统计访客人数数量")
    public ResultMsg<Integer> count() {
        LambdaQueryWrapper<Visitor> queryWrapper = new LambdaQueryWrapper<>();
        int count = service.count(queryWrapper);
        if (count != 0) {
            return ResultMsg.success(count);
        } else {
            return ResultMsg.error("没有统计到访客信息");
        }
    }

    /**
     * 修改访客人员登记信息
     * @param visitor
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改访客信息")
    public ResultMsg<String> update(@RequestBody Visitor visitor) {
        visitor.setUpdateTime(LocalDateTime.now());
        boolean fol = service.updateById(visitor);
        if (fol) {
            return ResultMsg.success("访客修改成功");
        } else {
            return ResultMsg.error("修改失败");
        }
    }


}
