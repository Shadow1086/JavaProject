package com.ck.it.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Package: com.ck.it.common
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 18:14
 */
@Getter
@AllArgsConstructor
@Schema(description = "业务响应状态码")
public enum ResultCodeEnum {
	SUCCESS(10000,"成功"),
	FAIL(10001,"错误"),

	PARAM_ERROR(20001,"参数错误"),

	NOT_LOGIN(30001,"未登录"),
	TOKEN_EXPIRED(30002,"token已过期"),
	TOKEN_INVALID(30003,"token失效"),

	USERNAME_ERROR(31001,"用户名错误"),
	PASSWORD_ERROR(31002,"密码错误"),
	USERNAME_USED(31003,"用户名已存在"),

	HEADLINE_NOT_FOUND(40001,"新闻不存在"),
	HEADLINE_NO_PERMISSION(40002,"无权限操作该新闻");

	@Schema(description = "业务状态码",example = "10000")
	private final Integer code;
	@Schema(description = "业务状态信息",example = "成功")
	private final String message;

}
