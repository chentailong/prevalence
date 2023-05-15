package com.lon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.entity.AddressBook;
import com.lon.mapper.AddressBookMapper;
import com.lon.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * 通讯录服务impl
 *
 * @author ctl
 * @date 2022/09/16
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
