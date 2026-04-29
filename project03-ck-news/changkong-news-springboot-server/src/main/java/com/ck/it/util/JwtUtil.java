package com.ck.it.util;

import com.ck.it.common.ResultCodeEnum;
import com.ck.it.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Package: com.ck.it.util
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 19:25
 */
@Component
@RequiredArgsConstructor
@Data
public class JwtUtil {
	private final JwtProperties jwtProperties;

	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 提取token  比如以 Bearer 开头
	 * @param authorization     传入的字符串
	 * @return                  token
	 */
	private String extractToken(String authorization) {
		if (!StringUtils.hasText(authorization)) {
			throw new BusinessException(ResultCodeEnum.TOKEN_INVALID);
		}
		if (authorization.startsWith("Bearer ")) {
			return authorization.substring(7).trim();
		}
		return authorization.trim();
	}

	public String createToken(Long uid, String username) {
		Date now = new Date();
		Date expireTime = new Date(now.getTime() + jwtProperties.getExpireMillis());
		return Jwts.builder()
				.claim("uid", uid)
				.claim("username", username)
				.issuer(jwtProperties.getIssuer())
				.issuedAt(now)
				.expiration(expireTime)
				.signWith(getSecretKey())
				.compact();
	}

	public Claims parseToken(String token) {
		String tokenNew = this.extractToken(token);
		return Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(tokenNew)
				.getPayload();
	}

	public Long getUserId(String token) {
		try {
			if (!StringUtils.hasText(token)) {
				throw new BusinessException(ResultCodeEnum.NOT_LOGIN);
			}
			Claims claims = parseToken(token);
			Number uid = claims.get("uid", Number.class);
			return uid == null ? null : uid.longValue();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 检查token是否过期
	 * @param token token
	 * @return      过期：true,未过期：false
	 */
	public boolean isExpired(String token) {
		try {
			parseToken(token);
			return false;
		} catch (Exception e) {
			return true;
		}
	}
}
