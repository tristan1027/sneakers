package com.batman.matchman.intercptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class FrontMvcInterceptor  extends HandlerInterceptorAdapter{
	 private static final Logger logger = Logger.getLogger(FrontMvcInterceptor.class);
     @Override
    public boolean preHandle(HttpServletRequest request,
    		HttpServletResponse response, Object handler) throws Exception {
    	  String page =  request.getParameter("page");
    	  String rows =  request.getParameter("rows");
    	  if("".equals(page) || null == page){
    		  page = "1";
    	  }
    	  if("".equals(rows) || null == rows){
    		  rows = "10";
    	  }else if (Integer.parseInt(rows)<0 ||  Integer.parseInt(rows)>30){
    		  logger.info("FrontMvcInterceptor--分页拦截器rows值:【"+rows+"】,超过或者不符合规定条数已被系统处理为默认值");
    		  rows = "30";
    	  }
    	  logger.info("FrontMvcInterceptor--分页拦截器执行!page:"+page+"rows:"+ rows);
    	  PageThreadLocal.setCurPage(Integer.parseInt(page));
    	  PageThreadLocal.setPageSize(Integer.parseInt(rows));
    	return super.preHandle(request, response, handler);
    }
     
     @Override
    public void postHandle(HttpServletRequest request,
    		HttpServletResponse response, Object handler,
    		ModelAndView modelAndView) throws Exception {
    	super.postHandle(request, response, handler, modelAndView);
    }
     @Override
    public void afterCompletion(HttpServletRequest request,
    		HttpServletResponse response, Object handler, Exception ex)
    		throws Exception {
    	    PageThreadLocal.removeAll();
    	super.afterCompletion(request, response, handler, ex);
    }
}
