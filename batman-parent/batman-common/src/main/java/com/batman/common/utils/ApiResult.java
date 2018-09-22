package com.batman.common.utils;

/**   
 * @ClassName:  ApiResult   
 * @Description:TODO(接口返回给前端的消息对象)   
 * @author: tristan
 * @date:   2017年9月11日 上午12:10:19   
 *   
 */ 
public class ApiResult {

	private Integer code; // 0 成功 －1 失败
	private String msg;// 接口返回信息
	private Object apiData;

	public ApiResult(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ApiResult(String msg) {
		super();
		this.code = -1;
		this.msg = msg;
		this.setApiData(null);
	}

	public ApiResult() {
		super();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getApiData() {
		return apiData;
	}

	public void setApiData(Object apiData) {
		this.apiData = apiData;
	}



}