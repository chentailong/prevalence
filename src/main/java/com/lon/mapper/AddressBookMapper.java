package com.lon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lon.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * 地址簿映射器
 *
 * @author ctl
 * @date 2022/09/16
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
