package com.ck.it.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName news_type
 */
@TableName(value ="news_type")
@Data
public class NewsType {
    private Integer tid;

    private String tname;
}