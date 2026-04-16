package com.it.ck.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.it.ck.server.pojo
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/16 17:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo <T>{
	private Integer pageNum;
	private Integer pageSize;
	private Integer totalSize;
	private Integer totalPage;
	private List<T> pageData;
}
