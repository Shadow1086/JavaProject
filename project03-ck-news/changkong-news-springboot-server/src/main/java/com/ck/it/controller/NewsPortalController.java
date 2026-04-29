package com.ck.it.controller;

import com.ck.it.common.Result;
import com.ck.it.pojo.dto.RequestPage;
import com.ck.it.pojo.vo.DetailVo;
import com.ck.it.pojo.vo.HeadlinePageVo;
import com.ck.it.pojo.vo.PageInfoVo;
import com.ck.it.service.NewsHeadlineService;
import com.ck.it.service.NewsTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Package: com.ck.it.controller
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 18:52
 */
@RestController
@RequestMapping("portal")
@CrossOrigin
@Tag(name = "首页接口",description = "首页新闻类别与列表展示")
public class NewsPortalController {
	@Autowired
	private NewsTypeService typeService;
	@Autowired
	private NewsHeadlineService headlineService;
	@Operation(summary = "展示所有类别分类",description = "页眉展示的新闻类型分类查询")
	@GetMapping("findAllTypes")
	public Result findAllTypes(){
		return typeService.findAllTypes();
	}
	@Operation(summary = "新闻列表展示",description = "新闻列表展示接口")
	@PostMapping("findNewsPage")
	public Result<PageInfoVo<HeadlinePageVo>> findNewsPage(@RequestBody RequestPage request){
		try{
			return headlineService.findNewsPage(request);
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}
	@Operation(summary = "新闻详情",description = "展示新闻详情")
	@PostMapping("showHeadlineDetail")
	public Result<DetailVo> showHeadlineDetail(@RequestParam("hid") Long hid){
		DetailVo detail = headlineService.showHeadlineDetail(hid);
		if(detail!=null){
			return Result.ok(detail);
		}else{
			return Result.fail(null);
		}
	}
}
