package com.ck.it.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Package: com.ck.it.advice
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 23:28
 */
@Aspect
@Component
@Slf4j
public class LogAdvice {
	@Around("execution(* com.ck.it.controller..*(..))")
	public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		String methodName = joinPoint.getSignature().toShortString();
		try {
			Object result = joinPoint.proceed();
			log.info("接口调用成功：method={},cost={}ms",
					methodName, System.currentTimeMillis() - start);
			return result;
		} catch (Throwable e) {
			log.warn("接口调用失败：method={},cost={}ms",
					methodName, System.currentTimeMillis() - start);
			throw e;
		}
	}
}
