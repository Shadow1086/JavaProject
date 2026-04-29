package com.ck.it.interceptor;

import com.ck.it.common.Result;
import com.ck.it.common.ResultCodeEnum;
import com.ck.it.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.ck.it.interceptor
 * Description:
 * <p>
 *     登录保护拦截器，检查请求头中是否包含有效token
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 13:36
 */
@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {
	@Autowired
	private JwtUtil jwt;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("Authorization");
		boolean expired = jwt.isExpired(token);
		if(!expired){
			return true;
		}else{
			Result<Object> result = Result.build(null, ResultCodeEnum.NOT_LOGIN);
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(result);
			response.getWriter().print(json);
		}
		return false;
	}
}
