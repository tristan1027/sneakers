package com.batman.common.utils;

/**   
 * @ClassName:  ApiUtil   
 * @Description: 生产接口返回消息的工具类  
 * @author: tristan
 * @date:   2017年9月11日 上午12:25:02   
 *   
 */ 
public class ApiUtil {

	/**   
	 * @Title: genErrorResult   
	 * @Description:  返回请求错误的消息 
	 * @param: @param message  错误信息
	 * @param: @return      
	 * @return: ApiResult     
	 * @author: tristan
	 * @throws   
	 */ 
	public static ApiResult genErrorResult(String message) {
		ApiResult result=new ApiResult(message);
		ApiDataNoPage  data=new ApiDataNoPage();
		result.setApiData(data);
		return result;
	}
	
	/**   
	 * @Title: genResult   
	 * @Description: 返回请求成功的消息 不分页  
	 * @param: @return      
	 * @return: ApiResult     
	 * @author: tristan
	 * @throws   
	 */ 
	public static ApiResult genResult(Integer code, String msg,Object data) {
		ApiResult result=new ApiResult(code,msg);
		ApiDataNoPage apiData=new ApiDataNoPage(data);
		result.setApiData(apiData);
		return result;
	}

	/**   
	 * @Title: genResultWithPage   
	 * @Description: 返回请求成功的消息  分页 
	 * @param: @return      
	 * @return: ApiResult     
	 * @author: tristan
	 * @throws   
	 */ 
	public static ApiResult genResultPage(Integer code, String msg,Object data,int totalPage,int currentPage) {
		ApiResult result=new ApiResult(code,msg);
		ApiData apiData=new ApiData(data,totalPage,currentPage);
		result.setApiData(apiData);
		return result;

	}
}
