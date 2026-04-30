package com.it.ck.server.controller;

import com.it.ck.server.common.ResultCodeEnum;
import com.it.ck.server.pojo.NewsUser;
import com.it.ck.server.service.NewsUserService;
import com.it.ck.server.service.impl.NewsUserServiceImpl;
import com.it.ck.server.utils.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Package: com.it.ck.server.controller
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/15 10:49
 */
@WebServlet("/user/*")
public class NewsUserController extends BaseController {
	private final NewsUserService userService = new NewsUserServiceImpl();

	/**
	 * 用户登录验证
	 */
	protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NewsUser newsUser = WebUtil.readJson(req, NewsUser.class);
		NewsUser user = userService.findByName(newsUser.getUsername());
		Result<String> result = Result.ok(null);
		if (user == null) {
			JDBCUtil.releaseConnectin();
			WebUtil.writeJson(resp, Result.build(null, ResultCodeEnum.USERNAME_ERROR));
		} else {
			boolean matches = BCryptUtil.matches(newsUser.getUserPwd(), user.getUserPwd());
			if (matches) {
				user.setUserPwd("");
				String token = JwtHelper.createToken(user.getUid(), user.getUsername());
				result= Result.ok(token);
				JDBCUtil.releaseConnectin();
				WebUtil.writeJson(resp, result);
				return;
			}
			JDBCUtil.releaseConnectin();
			WebUtil.writeJson(resp, Result.build(null, ResultCodeEnum.PASSWORD_ERROR));
		}
	}


	/**
	 * 用户注册
	 */
	protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NewsUser newsUser = WebUtil.readJson(req, NewsUser.class);

		Result result = Result.ok(null);

		if (userService.findByName(newsUser.getUsername()) == null) {
			NewsUser user = userService.register(newsUser);
			if (user != null) {
				System.out.println(user);
				JDBCUtil.releaseConnectin();
				WebUtil.writeJson(resp, result);
			}
		} else {
			JDBCUtil.releaseConnectin();
			WebUtil.writeJson(resp, Result.build(null, ResultCodeEnum.USERNAME_USED));
		}
	}
}
