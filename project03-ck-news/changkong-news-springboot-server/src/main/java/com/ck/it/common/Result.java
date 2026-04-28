package com.ck.it.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.ck.it.common
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 18:21
 */
@Component
@Data
public class Result<T> {
	private Integer code;
	private String message;
	@NotBlank
	private T data;


	public static <T> @NonNull Result<T> build(T data){
		Result<T> result = new Result<>();
		result.data = data;
		return result;
	}
	public static <T> Result<T> build(T data,ResultCodeEnum codeEnum){
		Result<T> result = build(data);
		result.setCode(codeEnum.getCode());
		result.setMessage(codeEnum.getMessage());
		return result;
	}
	public static <T> Result<T> build(T data,Integer code,String message){
		Result<T> result = build(data);
		result.setMessage(message);
		result.setCode(code);
		return result;
	}

	public static<T> Result<T> ok(T data){
		Result<T> result =  new Result<>();
		return build(data,ResultCodeEnum.SUCCESS);
	}

	public static<T> Result<T> fail(T data){
		Result<T> result  = build(data);
		return build(data,100,"error");
	}
}
