package com.batman.common.utils;

/**   
 * @ClassName:  ApiDataNoPage   
 * @Description: api的data内容没有分页 
 * @author: tristan
 * @date:   2017年9月22日 下午5:59:57   
 *   
 */ 
public class ApiDataNoPage {

	private Object data;
	
	public ApiDataNoPage() {
		super();
	}
	
	public ApiDataNoPage(Object data) {
		super();
		this.setData(data);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
