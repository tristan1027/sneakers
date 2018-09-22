package com.batman.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class DES3 {
	private static final int KEY_LENGTH=26;//必须大于等于24
	private static final String KEY_MODEL="DESede";
	private static final String DES_MODEL="DESede/ECB/PKCS5Padding";
	/**
	 * 加密 
	 * @param src 要加密的内容
	 * @param key 大于等于24个字符的随机key
	 * @return key+ BASE64编码的密文
	 * @throws Exception
	 */
	public static String encryptThreeDES(String src,String key)  
	{  
	    try {
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));  
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_MODEL);  
			SecretKey securekey = keyFactory.generateSecret(dks);  
			Cipher cipher = Cipher.getInstance(DES_MODEL);  
			cipher.init(Cipher.ENCRYPT_MODE, securekey);  
			byte[] b=cipher.doFinal(src.getBytes());  
			  
			BASE64Encoder encoder = new BASE64Encoder();  
			String result = encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");
			return   key+result;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}  
	
	
	/**
	 * 加密 
	 * @param src 要加密的内容
	 * @return  key+ BASE64编码的密文
	 * @throws Exception
	 */
	public static String encryptThreeDES(String src) 
	{  
		try {
			String key = getKey(KEY_LENGTH);
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));  
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_MODEL);  
			SecretKey securekey = keyFactory.generateSecret(dks);  
			Cipher cipher = Cipher.getInstance(DES_MODEL);  
			cipher.init(Cipher.ENCRYPT_MODE, securekey);  
			byte[] b=cipher.doFinal(src.getBytes());  
			BASE64Encoder encoder = new BASE64Encoder();  
			String result = encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");
			return   key+result;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	      
	}  
	  
	
	
	/**
	 * 解密
	 * @param src
	 * @return 
	 * @throws DESException 
	 * @throws Exception
	 */
	//3DESECB解密,key必须是长度大于等于 3*8 = 24 位  
	public static String decryptThreeDES(String src)
	{  
	    try {
			//--通过base64,将字符串转成byte数组  
			 //--解密的key  
			String key = src.substring(0,KEY_LENGTH);
			System.out.println("解密key:"+key);
			String content = src.substring(KEY_LENGTH);
			System.out.println("需要解密内容:"+content);
			BASE64Decoder decoder = new BASE64Decoder();  
			byte[] bytesrc = decoder.decodeBuffer(content);  
   
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));  
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_MODEL);  
			SecretKey securekey = keyFactory.generateSecret(dks);  
			  
			//--Chipher对象解密  
			Cipher cipher = Cipher.getInstance(DES_MODEL);  
			cipher.init(Cipher.DECRYPT_MODE, securekey);  
			byte[] retByte = cipher.doFinal(bytesrc);  
			System.out.println("解密后内容:"+new String(retByte)); 
			return new String(retByte);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	    return null;
	    
	}  
	
	

	 /**
    * 随机获取一个加解密的key DES3加密key需要大于等于24个字符
    * @param length
    *        随机字符串长度
    * @return 随机字符串
    */
   private static String getKey(int length) {
       if ( length <= 0 ) {
           return "";
       }
       
       char[] randomChar = { 'A',  'X','1', 'C',  'T', 'E', 'B','F', '8', 'H','Q','G', 'I','2', 'J', '5','K', 'L','3',  'N',
       		'O', 'M','P','6', 'R',  'U', 'V', 'W', '4', 'Y','S', '7','Z','D','0','9','a','b','c','d','e','f',
       		'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
       
       Random random = new Random();
       StringBuffer stringBuffer = new StringBuffer();
       for (int i = 0; i < length; i++) {
           stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
       }
       return stringBuffer.toString();
   }
	
	public static void main(String[] args) throws Exception {
		String bodyb="{'bonusMoney':'100','cipher':'ef9dfbc52a91b2c542a497ef1be5fa04'}";
		System.out.println(DES3.encryptThreeDES("rtmp://p025082f3.live.126.net/live/8e07fc6dd06a4b5d957a085b56c013bd?wsSecret=6ef52f5a0ceeaf6975d68a0a40062705&wsTime=1530178103"));
//		System.out.println(decryptThreeDES("l8IddTOyqf8igbAABcI4LJ3PJN0vkt3wMy8uw="));
//		System.out.println(encryptThreeDES("333"));
		
	}
	
	/**
	 * 解密
	 * @param src
	 * @return 
	 * @throws DESException 
	 * @throws Exception
	 */
	//3DESECB解密,key必须是长度大于等于 3*8 = 24 位  
	public static String decryptThreeDESH5(String src)
	{  
	    try {
			//--通过base64,将字符串转成byte数组  
			 //--解密的key  
			String key = src.substring(0,KEY_LENGTH);
			System.out.println("解密key:"+key);
			String content = src.substring(KEY_LENGTH);
			System.out.println("需要解密内容:"+content);
			BASE64Decoder decoder = new BASE64Decoder();  
			byte[] bytesrc = decoder.decodeBuffer(content);  
   
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));  
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_MODEL);  
			SecretKey securekey = keyFactory.generateSecret(dks);  
			  
			//--Chipher对象解密  
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS7Padding");  
			cipher.init(Cipher.DECRYPT_MODE, securekey);  
			byte[] retByte = cipher.doFinal(bytesrc);  
			System.out.println("解密后内容:"+new String(retByte)); 
			return new String(retByte);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	    return null;
	    
	}  
	
}
