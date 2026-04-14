package com.it.ck.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.it.ck.server.utils
 * Description:
 * <p>
 *     写/读 JSON的工具类
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 18:25
 */

public class WebUtil {
	private static final ObjectMapper mapper;
	static{
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	public static <T> T readJson(HttpServletRequest request,Class<T> clazz){
		T t = null;
		BufferedReader reader = null;
		try{
			reader = request.getReader();
			StringBuilder buffer = new StringBuilder();
			String line = reader.readLine();
			while((line=reader.readLine())!=null){
				buffer.append(line);
			}
			t = mapper.readValue(buffer.toString(),clazz);
		}catch (IOException e ){
			throw new RuntimeException(e);
		}
		return t;
	}

	public static void writeJson(HttpServletResponse response,Result result){
		response.setContentType("application/json;charset=UTF-8");
		try{
			String json = mapper.writeValueAsString(result);
			response.getWriter().write(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
