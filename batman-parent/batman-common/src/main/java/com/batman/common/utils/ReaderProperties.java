package com.batman.common.utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReaderProperties {
	private static final Logger logger = LoggerFactory.getLogger(ReaderProperties.class);

    /**
     * 读取properties文件 将数据封装到Map中
     * @author Administrator
     * @creaetime 01-04-2014 13:54:04
     * @param propertie 需要读取的配置文件
     * @return map
     */
    public static Map<String, String> readData(String  propertie) {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = null;
        try {
            inputStream = ReaderProperties.class.getClassLoader().getResourceAsStream(propertie);
            Properties properties = new Properties();
            properties.load(inputStream);
            Set<Entry<Object, Object>> set = properties.entrySet();
            // 迭代
            Iterator<Map.Entry<Object, Object>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Entry<Object, Object> entry = iterator.next();
                map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        catch (Exception e) {
            logger.error(propertie+"没有找到");
        }finally{
            if(inputStream != null){
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    logger.error("关闭输入流的时候出现异常"+e.getMessage());
                }
            }
        }
        return map;
    }
}
