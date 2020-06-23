package com.springboot.common.utils;
/**
 * 格式化编码
 *
 * @author wl
 */
public class CommonUtils {
	/**
	 * 格式化字符串
	 * */
	public static String format(String s){
		if (s == "" || s == null) {
			s = "0000";
		}else {
			s = s.substring(s.length()-4);
			Integer codes = Integer.parseInt(s) + 1;
			s = codes.toString();
			// 这里假定是一个1到4位的整数，不足四位时前面补0
			int len = s.length();
			// 不足四位
			if(len<4){
				int prefixNum = 4-len;//计算要补几个0
				// 前面补0
				for(int i = 0; i < prefixNum; i++){
					s = "0" + s;
				}
			}
		}

		return s;
	}
}
