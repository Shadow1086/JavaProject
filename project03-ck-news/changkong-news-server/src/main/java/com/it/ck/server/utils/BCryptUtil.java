package com.it.ck.server.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Package: com.it.ck.server.utils
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 18:02
 */

public class BCryptUtil {
	private static final int STRENGTH = 10;
	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(STRENGTH);

	private BCryptUtil(){}

	public static String encode(String password){
		if(password==null || password.isBlank()){
			throw new IllegalArgumentException("密码不能为空");
		}
		return ENCODER.encode(password);
	}

	public static boolean matches(String password,String encodedPwd){
		if(password == null || password.isBlank()){
			throw new IllegalArgumentException("密码不能为空");
		}
		if(encodedPwd == null || encodedPwd.isBlank()){
			throw new IllegalArgumentException("加密密码不能为空");
		}
		return ENCODER.matches(password, encodedPwd);
	}
}
