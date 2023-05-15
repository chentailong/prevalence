package com.lon.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lon.common.ResultMsg;
import com.lon.entity.Notice;
import com.lon.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * @author ctl
 */
@Slf4j
@RestController
@RequestMapping("/notice")
@Api(tags = "公告接口")
public class NoticeController {

    @Autowired
    public NoticeService noticeService;

    @PostMapping("/save")
    @ApiOperation(value = "新增通知")
    public ResultMsg<String> save(@RequestBody Notice notice) {
        notice.setUpdateTime(LocalDateTime.now());
        boolean save = noticeService.save(notice);
        if (save) {
            return ResultMsg.success("新增公告成功");
        } else {
            return ResultMsg.error("新增访客失败");
        }
    }

    /**
     * 修改公告信息
     */
    @PutMapping
    @ApiOperation(value = "修改公告")
    public ResultMsg<String> update(@RequestBody Notice notice) {
        notice.setUpdateTime(LocalDateTime.now());
        boolean fol = noticeService.updateById(notice);
        if (fol) {
            return ResultMsg.success("修改成功");
        } else {
            return ResultMsg.error("修改失败");
        }
    }

    /**
     * 根据id删除公告
     */
    @DeleteMapping("/del")
    @ApiOperation(value = "删除公告信息")
    public ResultMsg<String> delete(Long id) {
        log.info(id.toString());
        boolean fol = noticeService.removeById(id);
        if (fol) {
            return ResultMsg.success("删除信息成功");
        } else {
            return ResultMsg.error("删除失败");
        }
    }

    /**
     * @param page     页面
     * @param pageSize 页面大小
     * @param title    标题
     * @return {@link ResultMsg}<{@link Page}>
     */
    @GetMapping("/page")
    @ApiOperation(value = "根据分页查找公告")
    public ResultMsg<Page> page(int page, int pageSize, String title) {
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper();

        wrapper.like(StringUtils.isNotEmpty(title), Notice::getTitle, title);
        //添加排序条件
        wrapper.orderByDesc(Notice::getCreateTime);
        //执行查询
        noticeService.page(pageInfo, wrapper);

        return ResultMsg.success(pageInfo);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查找近一月的公告")
    public ResultMsg<List<Notice>> list() {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        LocalDateTime endDateTime = now.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        wrapper.le(Notice::getUpdateTime, endDateTime).ge(Notice::getUpdateTime,startDateTime);
        List<Notice> list = noticeService.list(wrapper);
        return ResultMsg.success(list);
    }

}
