package com.jd.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class BaseController extends HttpServlet {
	// 基础接收表单的数据
	public Object FormData(HttpServletRequest request, Class<?> classz) {
		// 通过反射获取对应的实体类的类型
		try {
			Object obj = classz.newInstance();

			Enumeration<String> names = request.getParameterNames();

			while (names.hasMoreElements()) {
				String strings = (String) names.nextElement();
				// name的名称，来变成方法名称 setXXX 第一个字母为大写
				String methodName = "set" + strings.substring(0, 1).toUpperCase()
						+ strings.substring(1, strings.length());
				// 通过反射类的调用存放值
				if (!strings.equals("method")) {
					Method method = classz.getMethod(methodName, classz.getDeclaredField(strings).getType());
					String[] parameterValues = request.getParameterValues(strings);
					for (int i = 0; parameterValues != null && i < parameterValues.length; i++) {
//						System.out.println(strings + ":" + parameterValues[i] + "\t");
						method.invoke(obj, parameterValues[i]);
					}
				}
			}
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ajax对象请求值
	public Object AjaxData(HttpServletRequest request, Class classz) {
		// 通过反射获取对应的实体类的类型
		try {
			Object obj = classz.getClass().newInstance();

			Enumeration<String> names = request.getParameterNames();

			while (names.hasMoreElements()) {
				String strings = (String) names.nextElement();
				// name的名称，来变成方法名称 setXXX 第一个字母为大写
				String methodName = "set" + strings.substring(0, 1).toUpperCase()
						+ strings.substring(1, strings.length());
				// 通过反射类的调用存放值
				Method method = classz.getMethod(methodName, classz.getField(strings).getType());
				String[] parameterValues = request.getParameterValues(strings);
				for (int i = 0; parameterValues != null && i < parameterValues.length; i++) {
					System.out.println(strings + ":" + parameterValues[i] + "\t");
					method.invoke(obj, parameterValues[i]);
				}
			}
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

}
