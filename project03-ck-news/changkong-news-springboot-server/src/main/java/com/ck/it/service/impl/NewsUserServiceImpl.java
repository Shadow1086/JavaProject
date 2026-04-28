package com.ck.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ck.it.pojo.NewsUser;
import com.ck.it.service.NewsUserService;
import com.ck.it.mapper.NewsUserMapper;
import org.springframework.stereotype.Service;

/**
* @author liang-ht
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2026-04-28 18:12:17
*/
@Service
public class NewsUserServiceImpl extends ServiceImpl<NewsUserMapper, NewsUser>
    implements NewsUserService{

}




