package com.batman.matchman.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.batman.common.utils.ApiConstant;
import com.batman.common.utils.ApiResult;
import com.batman.common.utils.ApiUtil;
import com.batman.matchman.service.NikeCommonService;

@Service
public class NikeCommonServiceImpl implements NikeCommonService {
	private static final Logger logger =LoggerFactory.getLogger(NikeCommonServiceImpl.class);

	@Override
	public ApiResult catchAirForce() {
		ApiResult result=new ApiResult();

		try{
			
			
			//如果执行成功
			result=ApiUtil.genResult(ApiConstant.success, "af1成功", null);
		}catch(Exception e){
			logger.info("抓取报错:"+e.getMessage());
			e.printStackTrace();
			result=ApiUtil.genErrorResult("服务报错");
		}
		
		return result;
	}

}
