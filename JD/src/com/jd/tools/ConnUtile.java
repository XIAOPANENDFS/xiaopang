package com.jd.tools;

public class ConnUtile {
//	验证空判断
	public static boolean isBloom(Object obj) {
		if (  obj == null || obj.equals("")) {
			return false;
		}
		return true;
	}
}
