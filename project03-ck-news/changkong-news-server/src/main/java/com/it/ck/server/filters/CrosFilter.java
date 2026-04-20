package com.it.ck.server.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

/**
 * Package: com.it.ck.server.filters
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/15 10:51
 */
@WebFilter("/*")
public class CrosFilter implements Filter {
	private static final Set<String> ALLOWED_ORIGINS = Set.of(
			"http://localhost:5173",
			"http://127.0.0.1:5173"
	);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String origin = request.getHeader("Origin");

		// 仅放行本地开发环境，生产同域代理到 /api 时不依赖 CORS。
		if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
			response.setHeader("Access-Control-Allow-Origin", origin);
			response.setHeader("Vary", "Origin");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept, Origin, X-Requested-With");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.setHeader("Access-Control-Max-Age", "3600");
		}

		if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}

		chain.doFilter(request, response);
	}
}
