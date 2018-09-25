package test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.batman.matchman.utils.HttpData;
import com.batman.matchman.utils.HttpService;
import com.google.gson.Gson;

public class NikeTestTemp {
	static Gson gson = new Gson();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		String paramsStr = "{\"actorId\":\"e75d0de1-ef34-4231-804c-7bc1715d4482\",\"resourceType\":\"dotcom/event_batch\",\"resourceVersion\":\"v1\",\"events\":[{\"eventName\":\"click_pdpChooseSize\",\"eventType\":\"mouse\",\"application\":{\"name\":\"pdp\",\"environment\":\"production\",\"division\":\"commerce\",\"domain\":\"dotcom\",\"platform\":\"cloud\",\"version\":\"\"},\"consumer\":{\"upmId\":\"5bc5ac15-10ce-4cee-8cc2-1e67a0bbaaf8\",\"visitorId\":\"e75d0de1-ef34-4231-804c-7bc1715d4482\",\"visitId\":\"e75d0de1-ef34-4231-804c-7bc1715d4482\",\"isSwoosh\":false,\"anonymousId\":\"5B3DA1A60AE1D16F6CA4B34725CB6972\",\"allowsMarketing\":true,\"allowsPerformance\":true,\"adobeMarketingCloudId\":\"41695701196714460033402094807870807007\",\"adobeVisitorId\":\"\"},\"products\":[{\"productId\":\"11595155\",\"brand\":\"Jordan\",\"category\":\"Lifestyle\",\"inventoryStatus\":\"in stock\",\"pid\":\"11595155\",\"priceAmount\":969,\"priceCurrency\":\"cny\",\"priceStatus\":\"regular\",\"productName\":\"Air Jordan 1 Mid 男子运动鞋\",\"reviewAverage\":4,\"reviewCount\":12,\"quantity\":1,\"skuId\":\"19237111\"}],\"platform\":{\"type\":\"web\",\"pageTitle\":\"Air Jordan 1 Mid 男子运动鞋耐克官网 中国\",\"referrer\":\"\",\"url\":\"https://www.nike.com/cn/t/air-jordan-1-mid-%E7%94%B7%E5%AD%90%E8%BF%90%E5%8A%A8%E9%9E%8B-eoTVomwM/554724-104\",\"adobeVisitorId\":\"41695701196714460033402094807870807007\",\"userAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36\",\"heightInPixels\":900,\"widthInPixels\":1440},\"properties\":{\"mouseTarget\":\"size selection: 44\",\"interactionType\":\"click\",\"view\":{\"name\":\"browse\",\"channel\":\"pdp\"}},\"locale\":{\"country\":\"cn\",\"language\":\"zh-CN\"},\"privacy\":{\"isFunctional\":true,\"isMarketing\":true,\"isPerformance\":true},\"abTest\":{\"optimizely\":[{\"experimentId\":\"11370930219\",\"experimentName\":\"browse_pdp_cicb-2844_lazyloadcarouselcards\",\"variationId\":\"11392130173\",\"variationName\":\"CICB2844Z\"},{\"experimentId\":\"11376842017\",\"experimentName\":\"browse_pdp_cicb-2830_lazy6up\",\"variationId\":\"11382744455\",\"variationName\":\"CICB2830A\"},{\"experimentId\":\"11395245481\",\"experimentName\":\"browse_pdp_cicb-2283_turn_off_reviews_shipping\",\"variationId\":\"11380100177\",\"variationName\":\"CICB2283B\"},{\"experimentId\":\"11055224577\",\"experimentName\":\"browse_pdp_cicb-2677_elevated-content\",\"variationId\":\"11049236085\",\"variationName\":\"CICB2677Z\"}]},\"traits\":{\"Locale_Country\":\"CN\",\"Experience_Has_Video\":\"true\",\"Experience_Has_eGift\":\"false\",\"Experience_PDP_inStock\":\"true\",\"Experience_PDP_styleColor\":\"554724-104\",\"Experience_PDP_style\":\"554724\",\"Experience_Has_ElevatedContent\":\"false\",\"Experience_Has_SaveData\":\"false\"},\"id\":\"3a69a1fe-b936-46f5-b24c-b4ffd53649f2\",\"timestamp\":\"2018-09-25T05:34:13.146Z\"}]}";

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
//		File filecc = new File("C:\\Users\\Administrator\\Desktop\\cookie\\pramsJson.txt");
		// String paramsStr = "";
		// BufferedReader readercc = new BufferedReader(new FileReader(filecc));
		// String paramsTmp = null;
		// while ((paramsTmp = readercc.readLine()) != null) {
		// paramsStr += paramsTmp;
		// }
		System.out.println("paramsStr:" + paramsStr);
		HashMap<String, Object> paramObj = gson.fromJson(paramsStr, HashMap.class);
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
		System.out.println("postmap:" + postMap);
		return postMap;

	}

}
