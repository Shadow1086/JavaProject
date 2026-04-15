package com.it.ck.server.service.impl;

import com.it.ck.server.dao.NewsUserDao;
import com.it.ck.server.dao.impl.NewsUserDaoImpl;
import com.it.ck.server.pojo.NewsUser;
import com.it.ck.server.service.NewsUserService;

import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.it.ck.server.service.impl
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:26
 */

public class NewsUserServiceImpl implements NewsUserService {
	private static final NewsUserDao userDao= new NewsUserDaoImpl();
	/**
	 * 根据用户名查找用户信息
	 *
	 * @param username 用户名
	 * @return 用户名查找到的用户对象
	 */
	@Override
	public List<NewsUser> findByName(String username) {
		return userDao.findByName(username);
	}
}
