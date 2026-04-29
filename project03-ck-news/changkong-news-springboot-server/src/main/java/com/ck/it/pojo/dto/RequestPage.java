package com.ck.it.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Package: com.ck.it.pojo.dto
 * Description:
 * <p>
 *     前端传入的查询分页信息
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 00:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPage {

	private String keyWord;

	private Integer type = 0;

	private Integer pageSize = 5;

	private Integer currentPage = 1;

}
