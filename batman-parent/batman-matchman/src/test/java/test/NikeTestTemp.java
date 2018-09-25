package test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.batman.matchman.utils.HttpData;
import com.batman.matchman.utils.HttpService;
import com.google.gson.Gson;

public class NikeTestTemp {
	static Gson gson = new Gson();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		String paramsStr = "{\"actorId\":\"65082479-0ad5-4b52-8852-32c97724ac64\",\"resourceType\":\"dotcom/event_batch\",\"resourceVersion\":\"v1\",\"events\":[{\"eventName\":\"click_pdpAddToCart\",\"eventType\":\"changeView\",\"application\":{\"name\":\"pdp\",\"environment\":\"production\",\"division\":\"commerce\",\"domain\":\"dotcom\",\"platform\":\"cloud\",\"version\":\"\"},\"consumer\":{\"upmId\":\"71b63056-a719-4f83-9011-49f36669a756\",\"visitorId\":\"65082479-0ad5-4b52-8852-32c97724ac64\",\"visitId\":\"65082479-0ad5-4b52-8852-32c97724ac64\",\"isSwoosh\":false,\"anonymousId\":\"DE70D579F4CF0029EDBFAF67FC91F737\",\"allowsMarketing\":true,\"allowsPerformance\":true,\"adobeMarketingCloudId\":\"82916036888205389382580268705428988002\",\"adobeVisitorId\":\"\"},\"products\":[{\"productId\":\"12257398\",\"brand\":\"Jordan\",\"category\":\"Lifestyle\",\"inventoryStatus\":\"in stock\",\"pid\":\"12257398\",\"priceAmount\":969,\"priceCurrency\":\"cny\",\"priceStatus\":\"regular\",\"productName\":\"Air Jordan 1 Mid 男子运动鞋\",\"reviewAverage\":4,\"reviewCount\":12,\"quantity\":1,\"skuId\":\"21654395\"}],\"platform\":{\"type\":\"web\",\"pageTitle\":\"Air Jordan 1 Mid 男子运动鞋耐克官网 中国\",\"referrer\":\"\",\"url\":\"https://www.nike.com/cn/t/air-jordan-1-mid-%E7%94%B7%E5%AD%90%E8%BF%90%E5%8A%A8%E9%9E%8B-eoTVomwM/554724-109?vst=jordan\",\"adobeVisitorId\":\"82916036888205389382580268705428988002\",\"userAgent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36\",\"heightInPixels\":900,\"widthInPixels\":1440},\"properties\":{\"view\":{\"name\":\"add to cart\",\"channel\":\"cart\"},\"previousView\":{\"viewType\":\"pdp\",\"name\":\"Air Jordan 1 Mid 男子运动鞋\",\"channel\":\"pdp\"}},\"locale\":{\"country\":\"cn\",\"language\":\"zh-CN\"},\"privacy\":{\"isFunctional\":true,\"isMarketing\":true,\"isPerformance\":true},\"abTest\":{\"optimizely\":[{\"experimentId\":\"11370930219\",\"experimentName\":\"browse_pdp_cicb-2844_lazyloadcarouselcards\",\"variationId\":\"11390220126\",\"variationName\":\"CICB2844A\"},{\"experimentId\":\"11376842017\",\"experimentName\":\"browse_pdp_cicb-2830_lazy6up\",\"variationId\":\"11382744455\",\"variationName\":\"CICB2830A\"},{\"experimentId\":\"11395245481\",\"experimentName\":\"browse_pdp_cicb-2283_turn_off_reviews_shipping\",\"variationId\":\"11380100177\",\"variationName\":\"CICB2283B\"},{\"experimentId\":\"11055224577\",\"experimentName\":\"browse_pdp_cicb-2677_elevated-content\",\"variationId\":\"11049236085\",\"variationName\":\"CICB2677Z\"}]},\"traits\":{\"Locale_Country\":\"CN\",\"Experience_Has_Video\":\"false\",\"Experience_Has_eGift\":\"false\",\"Experience_PDP_inStock\":\"true\",\"Experience_PDP_styleColor\":\"554724-109\",\"Experience_PDP_style\":\"554724\",\"Experience_Has_ElevatedContent\":\"false\",\"Experience_Has_SaveData\":\"false\"},\"id\":\"50652fb0-799c-4dad-b3d6-51d8bafe4739\",\"timestamp\":\"2018-09-25T14:45:54.506Z\"}]}";

		Map<String, String> heads = new HashMap<String, String>();

		heads.put(":authority", "api.nike.com");
		heads.put(":method", "POST");
		heads.put(":path", "/measure/uxevents/v1");
		heads.put(":scheme", "https");
		heads.put("accept", "*/*");
		heads.put("accept-encoding", "gzip, deflate, br");
		heads.put("accept-language", "zh-CN,zh;q=0.9");
		heads.put("content-type", "application/json; charset=UTF-8");
		heads.put("origin", "https://www.nike.com");

		heads.put("referer",
				"https://www.nike.com/cn/t/air-jordan-1-mid-%E7%94%B7%E5%AD%90%E8%BF%90%E5%8A%A8%E9%9E%8B-eoTVomwM/554724-104");
		heads.put("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
		System.out.println("数据源:" + paramsStr);
		HashMap<String, Object> paramObj=JSONObject.parseObject(paramsStr, HashMap.class);
		System.out.println("第一次读取"+JSON.toJSONString(paramObj));
		
		Map<String, String> param = objToString(paramObj);
		

		HttpService httpServicev = new HttpService();
		String url = "https://api.nike.com/measure/uxevents/v1";

		try {
			HttpData httpData = httpServicev.postWithHeads(url, param, heads);
			String cookiev = httpServicev.getCookies();
			System.out.println("return cookie:" + cookiev);
			String htmlContent = httpData.getContentStr();
			System.out.println("return httpdata:" + htmlContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> objToString(Map<String, Object> map) {
		Map<String, String> postMap = new HashMap<String, String>();

		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value.getClass().isArray()) {
				int length = Array.getLength(value);
				Object[] os = new Object[length];
				for (int i = 0; i < os.length; i++) {
					os[i] = Array.get(value, i);
				}
				postMap.put(key, JSONArray.toJSONString(os));
			} else {
				// 不是数组类型的 直接转json字符串 再put
				postMap.put(key, JSONObject.toJSONString(value));
			}

		}
		System.out.println("转换string之后:" + JSONObject.toJSONString(postMap));
		return postMap;

	}

}
