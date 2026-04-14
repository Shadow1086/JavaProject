package com.it.ck.server.common;

import lombok.Data;
import lombok.Getter;

/**
 * Package: com.it.ck.server.common
 * Description:
 * <p>
 *     返回结果的状态码和提示信息
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 17:04
 */

@Getter
public enum ResultCodeEnum {
	SUCCESS(200,"success"),
	USERNAME_ERROR(501,"usernameError"),
	PASSWORD_ERROR(503,"passwordError"),
	NOTLOGIN(504,"notLogin"),
	USERNAME_USED(505,"userNameUsed");

	private final Integer code;
	private final String message;

	private ResultCodeEnum(Integer code,String message){
		this.code = code;
		this.message = message;
	}
}
