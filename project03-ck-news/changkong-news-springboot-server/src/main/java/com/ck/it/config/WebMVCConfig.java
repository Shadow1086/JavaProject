package com.ck.it.config;

import com.ck.it.interceptor.LoginProtectedInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.ck.it.config
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 13:41
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
	@Autowired
	private LoginProtectedInterceptor interceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor).addPathPatterns("/headline/**");
	}
}
