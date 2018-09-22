package com.batman.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class DateUtils {
	
	public static String getNowTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间
		return date;
	}
	
	/**   
	 * @Title: getDateTimestamp   
	 * @Description: 返回当前时间戳
	 * @param: @return      
	 * @return: String     
	 * @author: jiangsong
	 * @throws   
	 */ 
	public static String getDateTimestamp(){
		Long Timestamp = System.currentTimeMillis();
		return String.valueOf(Timestamp);
	}
	
	/**   
	 * @Title: getNowTimeSecond   
	 * @Description: 获取当前时间秒
	 * @param: @return      
	 * @return: int     
	 * @author: jiangsong
	 * @throws   
	 */ 
	public static int getNowTimeSecond(){
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.getSecond();
	}
	
	/** 
	 * 获取精确到秒的时间戳 
	 * @param date 
	 * @return 
	 */  
	public static String getSecondTimestampTwo(){  
		Date date = new Date();
	    String timestamp = String.valueOf(date.getTime()/1000);  
	    return timestamp;  
	}
	/* 
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
	
    /**
     * 得到本周周一
     * 
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
     Calendar c = Calendar.getInstance();
     int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
     if (day_of_week == 0)
     day_of_week = 7;
     c.add(Calendar.DATE, -day_of_week + 1);
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
     return simpleDateFormat.format(c.getTime());
    }

    /**
     * 得到本周周日
     * 
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
     Calendar c = Calendar.getInstance();
     int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
     if (day_of_week == 0)
      day_of_week = 7;
     c.add(Calendar.DATE, -day_of_week + 7);
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
     return simpleDateFormat.format(c.getTime());
    }
    
    /**   
     * @Title: getNowNumberTime   
     * @Description: 该方法只用于 创建云信聊天室 
     * @param: @return      
     * @return: String      
     * @throws   
     */
    public static String getNowNumberTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间
		return date;
    }
    
    public static String getToday(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间
		return date;
	}
    
    //明天凌晨
    public static String getTomorrowZero(){
    Date date=new Date();//取时间
   	Calendar calendar = new GregorianCalendar();
   	calendar.setTime(date);
    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
   	date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
   	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
   	String dateString = formatter.format(date); 
   	return dateString;
	}
    
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     * @throws ParseException 
     */
    public static int differentDaysByMillisecond(String dateStr,String dateStr2) throws ParseException
    {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    	Date date2 = format2.parse(dateStr2);
        Date date = format.parse(dateStr); 	
        int days = (int) ((date2.getTime() - date.getTime()) / (1000*3600*24));
        return days;
    }
    
    //两个日期中间的日期
    public static List<String> getBetweenDates(String beginStr, String endStr) throws ParseException {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date begin=format.parse(beginStr); 	
    	Date end=format.parse(endStr); 	
        List<String> result = new ArrayList<String>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        /* Calendar tempEnd = Calendar.getInstance();
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }*/
     while(begin.getTime()<=end.getTime()){
         result.add(format.format(tempStart.getTime()));
         tempStart.add(Calendar.DAY_OF_YEAR, 1);
         begin = tempStart.getTime();
     }
        return result;
}
    
    //两个日期大小
    public static int compare_date(String DATE1, String DATE2) {       
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() < dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() > dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    
    /**
     * 得到几天后的时间
     * @param d
     * @param day
     * @return
     * @throws ParseException 
     */
    public static String getDateAfter(String d,int day) throws ParseException{
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
     Calendar now =Calendar.getInstance();
     now.setTime(df.parse(d));
     now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
     return df.format(now.getTime());
    }

}
