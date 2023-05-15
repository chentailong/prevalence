package com.lon.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lon.common.BaseContext;
import com.lon.common.ResultMsg;
import com.lon.entity.AddressBook;
import com.lon.entity.Notice;
import com.lon.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 地址本控制器
 * 地址管理
 *
 * @author ctl
 * @date 2022/09/16
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
@Api(tags = "地址管理")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     */
    @PostMapping
    @ApiOperation(value = "新增地址")
    public ResultMsg<AddressBook> save(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        return ResultMsg.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/default")
    @ApiOperation(value = "设置默认地址")
    public ResultMsg<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        wrapper.set(AddressBook::getIsDefault, 0);
        addressBookService.update(wrapper);

        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return ResultMsg.success(addressBook);
    }

    /**
     * 修改地址
     */
    @PutMapping
    @ApiOperation(value = "修改地址")
    public ResultMsg<String> update(@RequestBody AddressBook addressBook) {
        addressBook.setUpdateTime(LocalDateTime.now());
        boolean fol = addressBookService.updateById(addressBook);
        if (fol) {
            return ResultMsg.success("修改成功");
        } else {
            return ResultMsg.error("修改失败");
        }
    }

    /**
     * 根据id删除地址
     */
    @DeleteMapping("/del")
    @ApiOperation(value = "删除地址")
    public ResultMsg<String> delete(Long id) {
        boolean fol = addressBookService.removeById(id);
        if (fol) {
            return ResultMsg.success("删除地址成功");
        } else {
            return ResultMsg.error("删除失败");
        }
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询地址")
    public ResultMsg get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return ResultMsg.success(addressBook);
        } else {
            return ResultMsg.error("没有找到该对象");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("/default")
    @ApiOperation(value = "查询默认地址")
    public ResultMsg<AddressBook> getDefault() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        if (null == addressBook) {
            return ResultMsg.error("没有找到该对象");
        } else {
            return ResultMsg.success(addressBook);
        }
    }

    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询指定用户的全部地址")
    public ResultMsg<List<AddressBook>> list(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBook.getUserId(), AddressBook::getUserId, addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getIsDefault);
        return ResultMsg.success(addressBookService.list(queryWrapper));
    }
}
