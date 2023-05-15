package com.lon.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lon.common.ResultMsg;
import com.lon.entity.GoHome;
import com.lon.service.GoHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author ctl
 */
@RestController
@Slf4j
@RequestMapping("/goHome")
@Api(tags = "返乡人员登记接口")
public class GoHomeController {

    @Autowired
    private GoHomeService goHomeService;

    /**
     * 新增访客
     * @param goHome
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增返乡人员")
    public ResultMsg<String> save(@RequestBody GoHome goHome) {
        log.info(goHome.toString());
        boolean save = goHomeService.save(goHome);
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
    @ApiOperation(value = "删除返乡人员报备信息")
    public ResultMsg<String> delete(Long id) {
        log.info(id.toString());
        boolean fol = goHomeService.removeById(id);
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
    @ApiOperation(value = "根据分页查找返乡人员信息")
    public ResultMsg<Page> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<GoHome> wrapper = new LambdaQueryWrapper();
        //根据条件查找
        wrapper.like(StringUtils.isNotEmpty(name), GoHome::getName, name);
        //添加排序条件
        wrapper.orderByDesc(GoHome::getCreateTime);
        //执行查询
        goHomeService.page(pageInfo, wrapper);
        //返回结果
        return ResultMsg.success(pageInfo);
    }

    /**
     * 统计返乡人数
     */
    @GetMapping("/count")
    @ApiOperation(value = "统计返乡人数数量")
    public ResultMsg<Integer> count() {
        LambdaQueryWrapper<GoHome> queryWrapper = new LambdaQueryWrapper<>();
        int count = goHomeService.count(queryWrapper);
        if (count != 0) {
            return ResultMsg.success(count);
        } else {
            return ResultMsg.error("没有统计到返乡信息");
        }
    }


    /**
     * 修改返乡人员登记信息
     * @param goHome
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改返乡人员登记信息")
    public ResultMsg<String> update(@RequestBody GoHome goHome) {
        goHome.setUpdateTime(LocalDateTime.now());
        boolean fol = goHomeService.updateById(goHome);
        if (fol) {
            return ResultMsg.success("用户信息修改成功");
        } else {
            return ResultMsg.error("修改失败");
        }
    }


}
