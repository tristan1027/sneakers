package test;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class StringTest {

	public static void main(String[] args) {
		int[] ids = { 1, 2, 3, 4 };
		String[] members = { "tom", "jerry", "ben" };

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("members", members);
		map.put("ids", ids);

		//<String, Object> 转 <string,string> 包含数组
		Map<String, String> postMap = new HashMap<String, String>();

		for (Entry<String, Object> entry : map.entrySet()) {
			String key=entry.getKey();
			Object value=entry.getValue();
			if (value.getClass().isArray()) {
			    int length = Array.getLength(value);
			    Object[] os = new Object[length];
			    for (int i = 0; i < os.length; i++) {
			        os[i] = Array.get(value, i);
			    }
			    postMap.put(key, JSONArray.toJSONString(os));
			}else {
				//不是数组类型的 直接转json字符串 再put
				System.out.println("不是数组:"+value);
			}

		}
		
		System.out.println("postmap:"+postMap);

	}
}
