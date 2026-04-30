package com.ck.it.controller;

import com.ck.it.common.Result;
import com.ck.it.common.ResultCodeEnum;
import com.ck.it.exception.BusinessException;
import com.ck.it.pojo.NewsUser;
import com.ck.it.service.NewsUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "新闻用户接口", description = "用户登录注册等操作")
public class NewsUserController {
	private final NewsUserService service;

	@Operation(summary = "用户登录", description = "用户登录")
	@PostMapping("login")
	public Result<String> login(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "登录用户信息", required = true)
			@Valid @RequestBody
			NewsUser user) {
		String token = service.login(user);
		return Result.ok(token);
	}

	@Operation(summary = "用户注册",description = "用户注册接口方法")
	@PostMapping("register")
	public Result<String> register(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "注册用户信息", required = true)
			@Valid @RequestBody
			NewsUser request) {
		int rows = service.register(request);
		if (rows == -1) {
			return Result.build("用户名已存在", ResultCodeEnum.USERNAME_USED);
		} else if (rows == 1) {
			return Result.ok("注册成功");
		} else {
			return Result.fail("注册失败");
		}
	}

	@Operation(summary = "获取当前用户信息", description = "根据 Authorization 请求头中的 JWT 获取当前登录用户信息")
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("getUserInfo")
	public Result<Object> getUserInfo(
			@Parameter(description = "登录凭证，格式：Bearer <token>", required = true)
			@RequestHeader("Authorization")
			String token) {
		NewsUser userInfo = null;
		if (token != null && !token.isEmpty()) {
			userInfo = service.getUserInfo(token);
		} else {
			throw new BusinessException(ResultCodeEnum.TOKEN_INVALID);
		}
		try {
			return Result.ok(userInfo);
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
