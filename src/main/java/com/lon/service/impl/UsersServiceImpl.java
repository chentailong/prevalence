package com.lon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.entity.Users;
import com.lon.mapper.UsersMapper;
import com.lon.service.UsersService;
import org.springframework.stereotype.Service;

/**
 * 用户服务impl
 *
 * @author ctl
 * @date 2022/09/09
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
