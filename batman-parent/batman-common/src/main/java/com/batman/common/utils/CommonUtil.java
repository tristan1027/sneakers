package com.batman.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**   
 * @ClassName:  CommonUtil   
 * @Description:公用的操作类 
 * @author: tristan
 * @date:   2017年9月12日 下午9:02:43   
 *   
 */ 
public class CommonUtil {

    /**   
     * @Title: genUUID   
     * @Description: 生成没有空格到uuid （sessionid）   
     * @param: @return      
     * @return: String     
     * @author: tristan
     * @throws   
     */ 
    public static String genUUID() {
        String uuid = UUID.randomUUID().toString();
        String uuid2=UUID.randomUUID().toString();
        String uuidSpOne=uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23)
        + uuid.substring(24);
        String uuidSpTwo=uuid2.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23)
        + uuid.substring(24);
        return uuidSpOne+uuidSpTwo;
    }
    
    /**   
     * @Title: isPatternMatch   
     * @Description: 校验两个字符串是否匹配 
     * @param: @param s1
     * @param: @param p1
     * @param: @return      
     * @return: boolean     
     * @author: tristan
     * @throws   
     */ 
    public static boolean isPatternMatch(String s1, String p1,String p2,String testPhones) {
        boolean ret = false;
        String []temp =testPhones.split(",");
        for (String phone : temp) {
			if(s1.equals(phone)) {
				return ret=true;
			}
		}
        if (!StringUtils.isBlank(s1)) {
            ret = s1.matches(p1)||s1.matches(p2);
        }
        return ret;
    }
    
    /**   
     * @Title: getRandomString   
     * @Description: 生成指定长度的正整数  
     * @param: @param length
     * @param: @return      
     * @return: String     
     * @author: tristan
     * @throws   
     */ 
    public static String getRandomString(int length) {
        if (length <= 0) {
            return "";
        }
        char[] randomChar = {'3', '1', '2', '0', '4', '5', '7', '6', '9', '8'};
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
        }
        return stringBuffer.toString();
    }
    
    /**   
     * @Title: getStringToDecimal   
     * @Description: 将字符串转换成 BigDecimal
     * @param: @param str
     * @param: @return      
     * @return: BigDecimal     
     * @author: jiangsong
     * @throws   
     */ 
    public static BigDecimal getStringToDecimal(String str){
    	//构造以字符串内容为值的BigDecimal类型的变量bd 
    	BigDecimal bd=new BigDecimal(str); 
    	//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入) 
    	bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd;
    }
    
    /**
     * @Title: getStringNum   
     * @Description: 生产订单号   
     * @param: @return      
     * @return: String     
     * @author: jiangsong
     * @throws
     */
    public static String getStringNum(){
		String Timestamp = String.valueOf(System.currentTimeMillis());
		String str = getRandomString(4);
		return Timestamp + str;
    }
    
    public static BigDecimal getBigDecimal( Object value ) {
        BigDecimal ret = null;
        if( value != null ) {
            if( value instanceof BigDecimal ) {
                ret = (BigDecimal) value;
            } else if( value instanceof String ) {
                ret = new BigDecimal( (String) value );
            } else if( value instanceof BigInteger ) {
                ret = new BigDecimal( (BigInteger) value );
            } else if( value instanceof Number ) {
                ret = new BigDecimal( ((Number)value).doubleValue() );
            } else {
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");
            }
        }
        return ret;
    }
}
