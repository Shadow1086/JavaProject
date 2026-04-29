package com.ck.it.service;

import com.ck.it.common.Result;
import com.ck.it.pojo.NewsHeadline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ck.it.pojo.dto.RequestPage;
import com.ck.it.pojo.dto.RequestPublish;
import com.ck.it.pojo.vo.DetailVo;
import com.ck.it.pojo.vo.HeadlinePageVo;
import com.ck.it.pojo.vo.PageInfoVo;

/**
* @author liang-ht
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2026-04-28 18:03:20
*/
public interface NewsHeadlineService extends IService<NewsHeadline> {

	Result<PageInfoVo<HeadlinePageVo>> findNewsPage(RequestPage request);

	DetailVo showHeadlineDetail(Long hid);

	boolean publish(RequestPublish request,Long uid);

	boolean updateData(NewsHeadline headline);

	boolean headlinePermission(Long hid, Long userId);
}
