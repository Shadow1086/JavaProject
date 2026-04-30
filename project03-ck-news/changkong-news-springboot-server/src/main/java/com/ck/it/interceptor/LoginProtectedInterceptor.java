package com.ck.it.interceptor;

import com.ck.it.common.Result;
import com.ck.it.common.ResultCodeEnum;
import com.ck.it.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * Package: com.ck.it.interceptor
 * Description:
 * <p>
 * 登录保护拦截器，检查请求头中是否包含有效token
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
		Result<Object> result;
		if (token == null || token.isEmpty()) {
			/// 没有token
			result = Result.build(null,ResultCodeEnum.NOT_LOGIN);
			writeJson(response,result);
			return false;
		}
		if(jwt.isExpired(token)){
			writeJson(response,Result.build(null,ResultCodeEnum.TOKEN_EXPIRED));
			return false;
		}
		return true;
	}

	private void writeJson(HttpServletResponse response,Result<Object> result) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		new ObjectMapper().writeValue(response.getWriter(),result);
	}
}
