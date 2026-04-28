package com.ck.it;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.ck.it
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 18:06
 */
@ConfigurationPropertiesScan
@MapperScan("com.ck.it.mapper")
@SpringBootApplication
public class ApplicationMain {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationMain.class,args);
	}

	@Bean
	public MybatisPlusInterceptor interceptor(){
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		return interceptor;
	}
}
