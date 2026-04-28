package com.ck.it.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * @TableName news_headline
 */
@TableName(value ="news_headline")
@Data
public class NewsHeadline {
	@TableId
    private Integer hid;

    private String title;

    private String article;

    private Integer type;

    private Integer publisher;

    private Integer pageViews;

    private Date createTime;

    private Date updateTime;

	@TableLogic
    private Integer isDeleted;
}