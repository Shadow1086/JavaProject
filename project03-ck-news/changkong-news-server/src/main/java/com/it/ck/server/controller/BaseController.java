package com.it.ck.server.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Package: com.it.ck.server.controller
 * Description:
 * <p>
 *     让Controller中的每一个方法都能够通过/controller/method-name方式找到资源
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/15 20:09
 */

public class BaseController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = requestURI.substring(contextPath.length());

		int lastSlashIndex = path.lastIndexOf('/');
		String methodName = (lastSlashIndex >= 0) ? path.substring(lastSlashIndex + 1) : path;

		if (methodName.isBlank()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "请求方法不能为空");
			return;
		}

		try {
			Class<?> clazz = this.getClass();
			Method method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

			if (!Modifier.isProtected(method.getModifiers()) && !Modifier.isPublic(method.getModifiers())) {
				resp.sendError(HttpServletResponse.SC_FORBIDDEN, "非法访问");
				return;
			}

			method.invoke(this, req, resp);
		} catch (NoSuchMethodException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "请求方法不存在: " + methodName);
		} catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			throw new ServletException("目标方法执行失败: " + methodName, targetException);
		} catch (IllegalAccessException e) {
			throw new ServletException("无权访问目标方法: " + methodName, e);
		}
	}
}

