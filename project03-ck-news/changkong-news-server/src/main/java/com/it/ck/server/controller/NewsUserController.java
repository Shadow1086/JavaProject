package com.it.ck.server.controller;

import com.it.ck.server.common.ResultCodeEnum;
import com.it.ck.server.pojo.NewsUser;
import com.it.ck.server.service.NewsUserService;
import com.it.ck.server.service.impl.NewsUserServiceImpl;
import com.it.ck.server.utils.BCryptUtil;
import com.it.ck.server.utils.Result;
import com.it.ck.server.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.it.ck.server.controller
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/15 10:49
 */
@WebServlet("/user/*")
public class NewsUserController extends BaseController{
	private final NewsUserService userService = new NewsUserServiceImpl();

	/**
	 *  用户登录验证
	 */
	protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NewsUser newsUser = WebUtil.readJson(req, NewsUser.class);
		List<NewsUser> users = userService.findByName(newsUser.getUsername());
		Result<String> result  = Result.ok(null);
		if(users.isEmpty()){
			WebUtil.writeJson(resp,Result.build(null, ResultCodeEnum.USERNAME_ERROR));
		}else{
			for(NewsUser user : users){
				boolean matches = BCryptUtil.matches(newsUser.getUserPwd(), user.getUserPwd());
				if(matches){
					WebUtil.writeJson(resp,result);
					return;
				}
			}
			WebUtil.writeJson(resp,Result.build(null,ResultCodeEnum.PASSWORD_ERROR));
		}
	}
}
