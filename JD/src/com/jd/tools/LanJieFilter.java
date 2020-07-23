package com.jd.tools;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LanJieFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 因为过滤器的自带请求对象不是Http协议的
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse resp = (HttpServletResponse) response;
//		System.out.println("执行到这里");
//		if( req.getSession().getAttribute("user") != null) {
//			//这里就是表示让程序继续运行
//			chain.doFilter(request, response);
//		}else {
//			//如果没有登录，那么就要拦截下来，去到别的页面
//			req.getRequestDispatcher("user.html").forward(req, resp);
//		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
