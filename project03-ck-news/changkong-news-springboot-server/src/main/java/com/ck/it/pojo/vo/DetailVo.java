package com.ck.it.pojo.vo;

import lombok.Data;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.ck.it.pojo.vo
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 11:42
 */
@Data
public class DetailVo {
	private Long hid;        /// 新闻id
	private String title;       /// 新闻标题
	private String article;     /// 新闻正文内容
	private String typeName;    /// 新闻所属类别名称
	private Integer pageViews;  /// 新闻流量量
	private Integer pastHour;  /// 发布的小时数
	private String username;    /// 发布的作者用户名
	private Integer version;        ///版本号
	private String author;      /// 发布的作者笔名
}
