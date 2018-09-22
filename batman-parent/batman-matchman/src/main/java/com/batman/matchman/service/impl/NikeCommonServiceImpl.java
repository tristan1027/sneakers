package com.batman.matchman.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.batman.common.utils.ApiResult;
import com.batman.matchman.service.NikeCommonService;

@Service
public class NikeCommonServiceImpl implements NikeCommonService {
	private static final Logger logger =LoggerFactory.getLogger(NikeCommonServiceImpl.class);

	@Override
	public ApiResult catchAirForce() {
		ApiResult result=new ApiResult();

		try{
			
			
			
		}catch(Exception e){
			logger.info("");
			
		}
		
		return result;
	}

}
