package com.ck.it.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Package: com.ck.it.config
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 18:27
 */
@Configuration
@OpenAPIDefinition(
		info=@Info(
				title = "长空新闻接口文档," ,
				version = "2.0",
				description = "长空新闻spring boot 后端api"
		)
)
@SecurityScheme(
		name = "bearerAuth",
		type= SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
)
public class OpenApiConfig {

}
