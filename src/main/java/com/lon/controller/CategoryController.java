package com.lon.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lon.common.ResultMsg;
import com.lon.entity.Category;
import com.lon.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ctl
 */

@Slf4j
@RestController
@RequestMapping("/category")
@Api(tags = "分类接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增分类")
    public ResultMsg<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return ResultMsg.success("新增成功");
    }

    /**
     * 分类查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分类查询")
    public ResultMsg<Page> page(int page, int pageSize) {
        //分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        //进行分页查询
        categoryService.page(pageInfo, wrapper);
        return ResultMsg.success(pageInfo);
    }

    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public ResultMsg<String> delete(Long id) {
        categoryService.remove(id);
        return ResultMsg.success("分类信息删除成功");
    }

    /**
     * 修改分类信息
     *
     * @param category 类别
     * @return {@link ResultMsg}<{@link String}>
     */
    @PutMapping
    @ApiOperation(value = "修改分类信息")
    public ResultMsg<String> update(@RequestBody Category category) {
        log.info("修改分类信息： {}", category);
        categoryService.updateById(category);
        return ResultMsg.success("分类信息修改成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "查找分类")
    public ResultMsg<List<Category>> list(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper();
        wrapper.eq(category.getType() != null, Category::getType, category.getType());
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(wrapper);
        return ResultMsg.success(list);
    }
}
