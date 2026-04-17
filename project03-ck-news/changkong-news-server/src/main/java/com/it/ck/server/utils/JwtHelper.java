package com.it.ck.server.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Package: com.it.ck.server.utils
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/16 13:46
 */

public class JwtHelper {
	// token 过期时间
	private static final long EXPIRE_MILLIS = 7 * 24 * 60 * 60 * 1000L;
	//
	private static final String SECRET = "changkong-news-auth-secret-key-2026-32bytes";

	private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

	// 生成token 字符串
	public static String createToken(Integer uid, String username) {
		Date now = new Date();
		Date expireTime = new Date(now.getTime() + EXPIRE_MILLIS);

		return Jwts.builder()
				// 设置主题，这个token主要代表谁
				.subject(String.valueOf(uid))
				// 签发时间，这个token什么时候创建的
				.issuedAt(now)
				// 设置过期时间，超过这个时间自动失效
				.expiration(expireTime)
				// 放数据，可以有多个.claim方法
				.claim("username", username)
				// 用密钥给token 签名
				.signWith(KEY)
				// 将前面设置好的压缩成最终的字符串
				.compact();
	}

	/**
	 * 解析token,失败会抛出异常
	 *
	 * @param token
	 * @return {@link Claims }
	 */
	public static Claims parseToken(String token) {
		Jws<Claims> claimsJws = Jwts.parser()
				//  用同一个密钥进行校验，创建的token 和 校验的token必须是同一个
				.verifyWith(KEY)
				// 准备开始解析
				.build()
				// 进行解析，解析成功说明token可信，解析失败会抛出异常
				.parseSignedClaims(token);
		return claimsJws.getPayload();
	}

	/**
	 * 校验token是否有效
	 */
	public static boolean isValidToken(String token) {
		try {
			parseToken(token);
			return true;
		} catch (JwtException | IllegalArgumentException e ){
			return false;
		}
	}

	/**
	 *  从token中获取用户id
	 *
	 * @param token         用户带来的token
	 * @return {@link Integer }
	 */
	public static Integer getUserId(String token){
		Claims claims = parseToken(token);
		return Integer.valueOf(claims.getSubject());
	}

	public static String getUsername(String token){
		Claims claims = parseToken(token);
		return claims.get("username",String.class);
	}
}
