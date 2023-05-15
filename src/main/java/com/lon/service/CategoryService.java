package com.lon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lon.entity.Category;

/**
 * @author ctl
 */
public interface CategoryService extends IService<Category> {
    /**
     * 根据id判断该分类是否可以删除
     * @param id
     */
    void remove(Long id);
}
