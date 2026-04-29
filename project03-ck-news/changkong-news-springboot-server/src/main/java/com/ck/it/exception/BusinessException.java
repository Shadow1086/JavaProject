package com.ck.it.exception;

import com.ck.it.common.ResultCodeEnum;
import lombok.Getter;

/**
 * Package: com.ck.it.exception
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 19:40
 */
@Getter
public class BusinessException extends RuntimeException {
	private final ResultCodeEnum codeEnum;

	public BusinessException(ResultCodeEnum codeEnum) {
		super(codeEnum.getMessage());
		this.codeEnum = codeEnum;
	}
}
