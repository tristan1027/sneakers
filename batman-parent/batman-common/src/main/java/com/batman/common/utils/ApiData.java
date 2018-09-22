package com.batman.common.utils;

/**   
 * @ClassName:  ApiData   
 * @Description:TODO(api返回对象中的 数据对象)   
 * @author: tristan
 * @date:   2017年9月11日 上午12:09:43   
 *   
 */ 
public class ApiData {
	/**   
	 * @Fields totalPage : 分页的总页数  
	 */
	private int totalPage;
	/**   
	 * @Fields currentPage : 分页的当前页数 
	 */
	private int currentPage;
	/**   
	 * @Fields data : 返回的具体数据
	 */
	private Object data;
	
	
	public 	ApiData() {
		super();
	}
	
	public ApiData(Object data,int totalPage,int currentPage) {
		super();
		this.data=data;
		this.totalPage=totalPage;
		this.currentPage=currentPage;
	}
	
	public ApiData(Object data) {
		super();
		this.data=data;
	}


	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	

}
