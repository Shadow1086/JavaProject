package com.ck.it.util;

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

	public String createToken(Integer uid, String username) {
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
		return Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public  Integer getUserId(String token){
		try{
			if(!StringUtils.hasText(token)){
				return null;
			}
			Claims claims = parseToken(token);
			return  claims.get("uid",Integer.class);
		}catch (Exception e ){
			return null;
		}
	}

	public boolean isExpired(String token){
		try{
			parseToken(token);
			return false;
		} catch (Exception e) {
			return true;
		}
	}
}
