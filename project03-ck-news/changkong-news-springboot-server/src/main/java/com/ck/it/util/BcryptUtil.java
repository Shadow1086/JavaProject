package com.ck.it.util;

import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.ck.it.util
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 18:48
 */
@Component
public class BcryptUtil {
	private static final Integer STRENGTH = 10;
	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(STRENGTH);

	private BcryptUtil(){}

	public static String encode(@NotBlank String password){
		return ENCODER.encode(password);
	}
	public static boolean matches(@NotBlank String password,String encodePassword){
		return ENCODER.matches(password,encodePassword);
	}
}
