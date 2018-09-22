package com.batman.matchman.intercptor;
/**
 * 接收和返回分页的参数信息</br>
 * 在前端分页拦截器中设置前端传入的分页参数</br>
 * 在后端mybatis分页拦截器中设置返回的总记录数等</br>
 *
 */
public class PageThreadLocal {
	/***
	 * 当前页数
	 */
	private static ThreadLocal<Integer> curPage = new ThreadLocal<Integer>();
	/***
	 * 每页显示多少条
	 */
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	
	  /**
     * 总记录数
     */
	private static ThreadLocal<Integer> total = new ThreadLocal<Integer>();
	
	 /**
     * 总页数
     */
	private static ThreadLocal<Integer> totalPage = new ThreadLocal<Integer>();
	
	public static void setTotalPage(int i) {
		totalPage.set(Integer.valueOf(i));
	}
	public static int  getTotalPage() {
		Integer i =  (Integer) totalPage.get();
		 if(i != null){
			 return i.intValue();
		 }
		return 0;//默认0页
	}

	public static void setTotal(int i) {
		total.set(Integer.valueOf(i));
	}
	public static int  getTotal() {
		Integer i =  (Integer) total.get();
		 if(i != null){
			 return i.intValue();
		 }
		return 0;//默认0条
	}


	
	public static void removeTotal(){
		total.remove();
	}
	public static void removeTotalPage(){
		totalPage.remove();
	}
	

	public static int getCurPage() {
		 Integer i =  (Integer) curPage.get();
		 if(i != null){
			 return i.intValue();
		 }
		return 1;//默认第一页
	}

	public static void setCurPage(int nowPage) {
		curPage.set(Integer.valueOf(nowPage));
	}

	public static int  getPageSize() {
		Integer i =  (Integer) pageSize.get();
		 if(i != null){
			 return i.intValue();
		 }
		return 10;//默认第10条
	}

	public static void setPageSize(int i) {
	     pageSize.set(Integer.valueOf(i));
	}
	

	
	
    
	public static void removeCurPage(){
		curPage.remove();
	}
	public static void removePageSize(){
		pageSize.remove();
	}
	
	
	
	/**
	 * 删除所有存储在ThreadLocal中的变量
	 */
	public static void  removeAll(){
		removeCurPage();
		removePageSize();
		removeTotal();
		removeTotalPage();
	}
	
	
}
