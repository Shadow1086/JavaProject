package com.it.ck.server.utils;

import com.it.ck.server.common.ResultCodeEnum;
import lombok.Data;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.it.ck.server.utils
 * Description:
 * <p>
 *     全局同一返回结果类
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 16:56
 */
@Data
public class Result<T> {
	// 返回业务码
	private Integer code;
	// 返回消息
	private String message;
	// 返回数据
	private T data;
	// 构造器
	public Result(){};

	// 返回数据
	protected static <T> Result<T> build(T data){
		Result<T> result = new Result<T>();
		if(data!=null){
			result.setData(data);
		}
		return result;
	}

	protected static <T> Result<T> build(T data,Integer code,String message){
		Result<T> result = build(data);
		result.setCode(code);
		result.setMessage(message);
		return result;
	}

	public static <T> Result<T> build(T data, ResultCodeEnum resultCodeEnum){
		Result<T> result = build(data);
		result.setCode(resultCodeEnum.getCode());
		result.setMessage(resultCodeEnum.getMessage());
		return result;
	}

	/**
	 *  操作成功
	 *
	 * @param data          success
	 * @return {@link Result }<{@link T }>
	 */
	public static<T> Result<T> ok(T data){
		Result<T> result = build(data);
		return build(data,ResultCodeEnum.SUCCESS);
	}

	public Result<T> message(String msg){
		this.setMessage(msg);
		return this;
	}
	public Result<T> code(Integer code){
		this.setCode(code);
		return this;
	}
}
