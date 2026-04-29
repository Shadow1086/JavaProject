package com.ck.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ck.it.pojo.NewsUser;
import jakarta.validation.Valid;

/**
 * @author liang-ht
 * @description 针对表【news_user】的数据库操作Service
 * @createDate 2026-04-28 18:12:17
 */
public interface NewsUserService extends IService<NewsUser> {
	String login(NewsUser request);

	/**
	 * 根据用户名查找用户是否存在
	 *
	 * @param request 请求来的用户信息
	 * @return boolean  存在：false 不存在：true
	 */
	NewsUser findByUsername(NewsUser request);

	/**
	 * 创建用户，返回主键
	 *
	 * @param request 请求的用户信息
	 * @return {@link Long }
	 */
	Integer register(@Valid NewsUser request);

	NewsUser getUserInfo(String token);

}
