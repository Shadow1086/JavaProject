package com.ck.it.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ck.it.pojo.NewsHeadline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ck.it.pojo.dto.RequestPage;
import com.ck.it.pojo.vo.HeadlinePageVo;
import com.ck.it.pojo.vo.PageInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author liang-ht
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2026-04-28 18:03:20
* @Entity com.ck.it.domain.NewsHeadline
*/
public interface NewsHeadlineMapper extends BaseMapper<NewsHeadline> {

	IPage<HeadlinePageVo> selectMyPage(IPage<HeadlinePageVo> page, @Param("request") RequestPage request);
}




