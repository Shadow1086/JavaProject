package com.ck.it.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.ck.it.common
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 18:14
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
	SUCCESS(200,"success"),
	USERNAME_ERROR(501,"username error"),
	PASSWORD_ERROR(502,"password error"),
	NOT_LOGIN(503,"token is expired"),
	TOKEN_INVALID(504,"token invalid"),
	REGISTER_ERROR(505,"register error"),
	USERNAME_USED(506,"username has been registered before");

	private final Integer code;
	private final String message;

}
