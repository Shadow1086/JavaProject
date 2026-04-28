package com.ck.it.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName news_user
 */
@TableName(value ="news_user")
@Data
public class NewsUser {
    private Integer uid;

    private String username;

    private String userPwd;

    private String nickName;
}