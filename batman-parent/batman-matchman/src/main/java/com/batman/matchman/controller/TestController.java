package com.batman.matchman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.batman.common.utils.ApiResult;
import com.batman.common.utils.ApiUtil;

@RequestMapping
@ResponseBody
@Controller
public class TestController {

	@RequestMapping("/test.do")
	public ApiResult test() {
		
		ApiResult result=new ApiResult();
		result=ApiUtil.genErrorResult("success");
		return result;
	}
}
