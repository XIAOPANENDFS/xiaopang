package com.jd.tools;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jd.pojo.User;

/**
 * @author SIMOBAI 拦截器，拦截用户没有登录的情况，动用购物车
 *
 */
public class LogFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest	req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		User obj = (User)req.getSession().getAttribute("user");
		System.out.println("55555");
		//判断是否存在用户，不存在，强制跳转 到登录页面
		if(!ConnUtile.isBloom(obj)) {
			resp.sendRedirect("user.html");
			return;
			
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
