package com.lon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.entity.Notice;
import com.lon.mapper.NoticeMapper;
import com.lon.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ctl
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
