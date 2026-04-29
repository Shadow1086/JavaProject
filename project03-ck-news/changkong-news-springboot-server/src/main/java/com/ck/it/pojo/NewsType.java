package com.ck.it.pojo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @TableName news_type
 */
@TableName(value = "news_type")
@Data
public class NewsType {
	private Integer tid;

	private String tname;

	@Version
	private Integer version;
	@TableLogic
	private Integer isDeleted;
}