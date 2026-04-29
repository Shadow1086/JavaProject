package com.ck.it.controller;

import com.ck.it.common.Result;
import com.ck.it.pojo.dto.RequestPage;
import com.ck.it.pojo.vo.DetailVo;
import com.ck.it.pojo.vo.HeadlinePageVo;
import com.ck.it.pojo.vo.PageInfoVo;
import com.ck.it.service.NewsHeadlineService;
import com.ck.it.service.NewsTypeService;
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
public class NewsPortalController {
	@Autowired
	private NewsTypeService typeService;
	@Autowired
	private NewsHeadlineService headlineService;

	@GetMapping("findAllTypes")
	public Result findAllTypes(){
		return typeService.findAllTypes();
	}

	@PostMapping("findNewsPage")
	public Result<PageInfoVo<HeadlinePageVo>> findNewsPage(@RequestBody RequestPage request){
		try{
			return headlineService.findNewsPage(request);
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}

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
