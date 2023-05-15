package com.lon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lon.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户映射器
 *
 * @author ctl
 * @date 2022/09/09
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}
