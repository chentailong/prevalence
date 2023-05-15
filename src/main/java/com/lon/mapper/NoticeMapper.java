package com.lon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lon.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ctl
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
}
