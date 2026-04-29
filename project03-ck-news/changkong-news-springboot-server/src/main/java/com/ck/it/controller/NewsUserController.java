package com.ck.it.controller;

import com.ck.it.common.Result;
import com.ck.it.common.ResultCodeEnum;
import com.ck.it.pojo.NewsUser;
import com.ck.it.service.NewsUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Package: com.ck.it.controller
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 18:52
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class NewsUserController {
	private final NewsUserService service;

	@PostMapping("login")
	public Result<String> login(@Valid @RequestBody NewsUser user) {
		String token = service.login(user);
		return Result.ok(token);
	}

	@PostMapping("register")
	public Result<String> register(@Valid @RequestBody NewsUser request) {

		int rows = service.register(request);
		if (rows == -1) {
			return Result.build("用户名已存在", ResultCodeEnum.USERNAME_USED);
		} else if (rows == 1) {
			return Result.ok("注册成功");
		} else {
			return Result.fail("注册失败");
		}
	}

	@PostMapping("getuserinfo")
	public Result<Object> getUserInfo(@RequestHeader("Authorization") String token) {
		NewsUser userInfo = service.getUserInfo(token);
		try {
			if (userInfo != null) {
				return Result.ok(userInfo);
			}
			return Result.fail(null);
		} catch (RuntimeException e) {
			if (e.getMessage().equals("token已过期，请重新登录")) {
				return Result.build("token已过期，请重新登录", ResultCodeEnum.NOT_LOGIN);
			} else if (e.getMessage().equals("token无效")) {
				return Result.build("token无效", ResultCodeEnum.TOKEN_INVALID);
			}
		}
		return Result.fail(null);
	}

}
