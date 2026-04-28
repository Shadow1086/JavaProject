package com.ck.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ck.it.mapper.NewsUserMapper;
import com.ck.it.pojo.NewsUser;
import com.ck.it.service.NewsUserService;
import com.ck.it.util.BcryptUtil;
import com.ck.it.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liang-ht
 * @description 针对表【news_user】的数据库操作Service实现
 * @createDate 2026-04-28 18:12:17
 */
@Service
@RequiredArgsConstructor
public class NewsUserServiceImpl extends ServiceImpl<NewsUserMapper, NewsUser>
		implements NewsUserService {

	private final JwtUtil jwt;
	@Autowired
	private NewsUserMapper mapper;

	@Override
	public String login(NewsUser request) {
		NewsUser user = lambdaQuery().eq(NewsUser::getUsername, request.getUsername()).one();
		if (user == null) {
			throw new RuntimeException("用户名错误");
		}
		boolean matches = BcryptUtil.matches(request.getUserPwd(), user.getUserPwd());
		if (!matches) {
			throw new RuntimeException("密码错误");
		}
		return jwt.createToken(user.getUid(), user.getUsername());
	}

	/**
	 * 根据用户名查找数据库中的用户
	 *
	 * @param request 请求的用户信息
	 * @return boolean      如果数据库中没有返回true,反之返回false;
	 */
	@Override
	public boolean findByUsername(NewsUser request) {
		return lambdaQuery().eq(NewsUser::getUsername, request.getUsername()).exists();
	}

	/**
	 * 创建用户，返回影响行数
	 *
	 * @param request 请求的用户信息
	 * @return {@link Long }
	 */
	@Override
	public Integer register(NewsUser request) {
		if (this.findByUsername(request)) {
			/// 如果数据库中没有该用户
			request.setUserPwd(BcryptUtil.encode(request.getUserPwd()));
			request.setUid(null);
			return save(request) ? 1 : 0;
		} else {
			return -1;
		}
	}

}




