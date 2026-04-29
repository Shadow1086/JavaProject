package com.ck.it.advice;

import com.ck.it.common.Result;
import com.ck.it.common.ResultCodeEnum;
import com.ck.it.exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Package: com.ck.it.advice
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 19:44
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public Result<Object> handleBusinessException(BusinessException e) {
		return Result.build(null, e.getCodeEnum());
	}

	@ExceptionHandler(Exception.class)
	public Result<Object> handleException(Exception e) {
		return Result.build(null, ResultCodeEnum.FAIL);
	}
}
