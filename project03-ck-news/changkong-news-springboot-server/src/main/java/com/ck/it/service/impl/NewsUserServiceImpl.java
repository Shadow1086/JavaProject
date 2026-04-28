package com.ck.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ck.it.pojo.NewsUser;
import com.ck.it.pojo.dto.LoginRequest;
import com.ck.it.service.NewsUserService;
import com.ck.it.mapper.NewsUserMapper;
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
    implements NewsUserService{
//	@Autowired
	private final JwtUtil jwt;

	@Override
	public String login(LoginRequest request) {
		NewsUser user = lambdaQuery().eq(NewsUser::getUsername,request.getUsername())
				.one();
		if(user == null){
			throw new RuntimeException("用户名错误");
		}
		boolean matches = BcryptUtil.matches(request.getUserPwd(), user.getUserPwd());
		if(!matches){
			throw new RuntimeException("密码错误");
		}
		return jwt.createToken(user.getUid(),user.getUsername());
	}
}




