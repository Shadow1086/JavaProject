package com.ck.it.controller;

import com.ck.it.common.Result;
import com.ck.it.common.ResultCodeEnum;
import com.ck.it.pojo.NewsUser;
import com.ck.it.service.NewsUserService;
import com.ck.it.util.BcryptUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
