package com.zcj.utils;

import com.zcj.exception.AesException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SHA1 {

	/**
	 * 用SHA1算法生成安全签名
	 * @return 安全签名
	 * @throws AesException 
	 */
	public static String getSHA1(String... args) throws AesException {
		try {
			//String[] array = new String[] { token, timestamp, nonce, encrypt };
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(args);
			for (int i = 0; i < args.length; i++) {
				sb.append(args[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}

	public static String genWithAmple(String... arr) {
		if (StringUtils.isAnyEmpty(arr)) {
			throw new IllegalArgumentException("非法请求参数，有部分参数为空 : " + Arrays.toString(arr));
		} else {
			Arrays.sort(arr);
			StringBuilder sb = new StringBuilder();

			for(int i = 0; i < arr.length; ++i) {
				String a = arr[i];
				sb.append(a);
				if (i != arr.length - 1) {
					sb.append('&');
				}
			}

			return DigestUtils.sha1Hex(sb.toString());
		}
	}
	public static String SHA1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}