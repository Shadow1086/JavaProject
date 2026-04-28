package com.ck.it.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Package: com.ck.it.util
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 19:04
 */
@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties {
	private String secretKey;
	private Long expireMillis;
	private String issuer;
}
