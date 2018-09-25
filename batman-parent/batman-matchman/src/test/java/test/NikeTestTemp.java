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
import com.batman.matchman.utils.HttpUtil;
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
		System.out.println("数据源:" + paramsStr.toString());
		

		String url = "https://api.nike.com/measure/uxevents/v1";

		try {
			String response=HttpUtil.doPostSSL(url, heads, paramsStr);
			//注释
			String content = doCart();
			System.out.println("接口返回内容:"+response);
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
		return postMap;

	}
	
	private static String doCart() {
		String cookie="AnalysisUserId=182.132.32.100.187011537575840387; guidU=822538e5-a544-4f0e-9640-c2084b126380; neo.swimlane=97; cicPWIntercept=1; RES_TRACKINGID=321724964001461; ResonanceSegment=1; _gscu_207448657=37575851pxghv317; anonymousId=1C7B5852FD89B0B89361EE7161FFF12E; _smt_uid=5ba58bab.5188f37e; ajs_user_id=null; ajs_group_id=null; ajs_anonymous_id=%221C7B5852FD89B0B89361EE7161FFF12E%22; CONSUMERCHOICE_SESSION=t; CONSUMERCHOICE=cn/zh_cn; siteCatalyst_sample=82; dreamcatcher_sample=49; neo_sample=35; lls=3; slCheck=6Nawg9wgt8is/vTl+JTHMOw8MO+U1am1IYeJjivPku2PnLg1TvUM94KEtKdVlaTrDY86IJvNPWm0rKWFsAHTzT8DXMiSBy0+TVxayZuVw7x2vC1bZ7qb2Kz22Sgfj8YL; llCheck=tZnJV5iwvrd1lF8qI/pyBWa2DnWdT61smE+iRRD9K705QgS7CaMDyjAXdkAnPVZFEFVUBdY+UMUH9o9EW/+Qq1OtCvivdHJBBCA8jvQtWEU18vA+/nF+X3MrNf2QmalPv+G/LaejqS6eT2uPhxfes21MP7sHwcrzsYKewkFz/TA=; nike_cp=cnns_sz_071516_a_alnul_bz01; neo.experiments=%7B%22main%22%3A%7B%223333-interceptor-cn%22%3A%22a%22%2C%223698-interceptor%22%3A%22a%22%7D%2C%22snkrs%22%3A%7B%7D%2C%22ocp%22%3A%7B%7D%7D; dreams_sample=18; AMCVS_F0935E09512D2C270A490D4D%40AdobeOrg=1; _gscbrs_207448657=1; sls=1; exp.swoosh.user=%7B%22granted%22%3A0%7D; guidA=642084b60d4900003d96a85b7d000000a4280000; geoloc=cc=CN,rc=SC,tp=vhigh,tz=GMT+8,la=30.67,lo=104.07,bw=5000; _qzjc=1; Hm_lvt_ed406c6497cc3917d06fd572612b4bba=1537615905,1537626827,1537775181; NIKE_COMMERCE_COUNTRY=CN; NIKE_COMMERCE_LANG_LOCALE=zh_CN; APID=BF7B927D651B8B0909BBEFCBB7A71A8D.sin-341-app-ap-0; RES_SESSIONID=644462663359338; mm_wc_pmt=1; guidS=451f361d-74b8-45d2-ed6c-fc1ce6f399b7; DAPROPS=\"sdevicePixelRatio:1.25|sdeviceAspectRatio:16/9|bcookieSupport:1\"; CART_SUMMARY=%7B%22profileId%22+%3A%2218261271308%22%2C%22userType%22+%3A%22DEFAULT_USER%22%2C%22securityStatus%22+%3A%221%22%2C%22cartCount%22+%3A1%7D; utag_main=_st:1537893429613$ses_id:1537892232340%3Bexp-session; guidSTimestamp=1537891606767|1537891630113; Hm_lpvt_ed406c6497cc3917d06fd572612b4bba=1537891630; _qzja=1.267957700.1537615905145.1537802093490.1537891607203.1537891630493.1537891630503..0.0.31.7; _qzjb=1.1537891607202.7.0.0.0; _qzjto=7.1.0; s_pers=%20s_dfa%3Dnikecomprod%7C1537893429958%3B%20c58%3Dno%2520value%7C1537893432466%3B; s_sess=%20c51%3Dhorizontal%3B%20s_cc%3Dtrue%3B%20prevList2%3D%3B%20tp%3D1756%3B%20s_ppv%3Dnikecom%25253Ecart%25253Eview%252C46%252C46%252C806%3B; AKA_A2=A;  ak_bmsc=437F0C6A536114111C03514F9B335EBBB68420640D490000245DAA5B46D5D069~plfnga83yyW3HbOnp9tvw8NmvC2uh+3VEcCdusQ2drz9bp8VHyeE7An8X1UcfpaJQ/UJjnGBTIeoVjqwqV7xFQ14YAAvhD1bBLMOfmG9djIr92dQI3cXPGl2HQi4IwbGnzSmhjLH4jdMo+TCNaCZtx8fIsnc92C4AvD3S90nv206nqv26+d8MwIqspEKDtHRn2XecsNEJQcuZLBRi1sR4NkWDSXlxrJ30ecB2eHu2QmZZZLahO46GnmMFM9KeU0oif; RT=\"sl=5&ss=1537891602874&tt=13857&obo=2&sh=1537891639370%3D5%3A2%3A13857%2C1537891630735%3D4%3A2%3A7023%2C1537891618035%3D3%3A2%3A2868%2C1537891615041%3D2%3A2%3A0%2C1537891614884%3D1%3A1%3A0&dm=nike.com&si=a19c0444-f491-4d70-bfcc-cf4ee120777c&bcn=%2F%2F36fb78d7.akstat.io%2F&ld=1537891639371\"; ppd=pdp|nikecom>pdp>Air%20Jordan%201%20Mid%20%E7%94%B7%E5%AD%90%E8%BF%90%E5%8A%A8%E9%9E%8B; AMCV_F0935E09512D2C270A490D4D%40AdobeOrg=-1891778711%7CMCIDTS%7C17799%7CMCMID%7C76542376286062809363725693145865169681%7CMCAAMLH-1538496439%7C11%7CMCAAMB-1538496439%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1537898839s%7CNONE%7CMCSYNCSOP%7C411-17804%7CMCAID%7CNONE%7CvVersion%7C2.4.0; _gscs_207448657=t37891607vz0zq110|pv:5; bm_sv=51312864E32CBACE991718034BCD3DB8~yX9vulQz81gWoCFUSEvP32HEr5aY37vQaVxyBtC64bpUCQCEnLZA2Hs1aABAZbClzuJ0pt57KdSlv/fZGcvwZ6jXFqG1/Z8AVVtABsp9N1oiR2Pom9AOu2eYh1bfprnVumGwqRclWOHlJaGTHlJIYw==";
		String cookiev="_abck=1C0E04DA2203EA52C7D6356A1BA8622F7D38DA2CC61A00003F5EAA5B4211E453~-1~Rm4fhy1jjdDQCdtQabqfdyZMpTofzrqJqz1TXSOuMMI=~-1~-1;bm_sz=F9AB1277C40E6D255FB9B94E36D81BAB~QAAQLNo4faJTbwlmAQAAXieAEZiNZvtrAOo+dcVBzkC5nOqca9FHNmuxEdefxDhd6owkOD9+rY35aDIUw2AySK2TflxLAy/5OvR3b8vTuQmVfcEnVDbjchKuL8zLP+CxY8TsHgH5BhDW7Cgk3ZL05xAjClofFLVp28EXZHuexxZfnI4/oCzxPlo0cArv;";
		cookie = cookie + cookiev;
		System.out.println("cookie:" + cookie);
		Map<String, String> params = new HashMap<String, String>();
		params.put(
				"user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
		params.put(":authority", "secure-store.nike.com");
		params.put(":method", "GET");
		params.put(
				":path",
				"/ap/services/jcartService?action=addItem&lang_locale=zh_CN&country=CN&catalogId=4&productId=12257398&price=969&qty=1&skuAndSize=21654398:43&rt=json&view=3&_=1537626963858&skuId=21654398&displaySize=43");
		params.put(":scheme", "https");
		params.put("accept", "*/*");
		params.put("accept-encoding", "gzip, deflate, br");
		params.put("accept-language", "zh-CN,zh;q=0.9");
		params.put("Cookie", cookie);
		params.put("origin", "https://www.nike.com");
		params.put(
				"referer",
				"https://www.nike.com/cn/t/air-jordan-1-mid-%E7%94%B7%E5%AD%90%E8%BF%90%E5%8A%A8%E9%9E%8B-eoTVomwM/554724-109");
		
		String url = "https://secure-store.nike.com/ap/services/jcartService?action=addItem&lang_locale=zh_CN&country=CN&catalogId=4&productId=12257398&price=969&qty=1&skuAndSize=21654398:43&rt=json&view=3&_=1537626963858&skuId=21654398&displaySize=43";
		HttpService httpService = new HttpService();
		HttpData httpData = httpService.getWithHeads(url, params);
		String htmlContent = httpData.getContentStr();
		System.out.println("7777777777777777:" + htmlContent);
		
		return htmlContent;
		
	}

}
