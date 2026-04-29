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
	@TableId(value = "hid", type = IdType.ASSIGN_ID)
    private Long hid;

    private String title;

    private String article;

    private Integer type;

    private Long publisher;

    private Integer pageViews;

    private Date createTime;

    private Date updateTime;

	@TableLogic
    private Integer isDeleted;
}
