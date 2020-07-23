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

public class IndexFilter implements Filter {
	@Override
	public void destroy() {

	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain arg2)
			throws IOException, ServletException {
		// 将ServletRequest 转为 HttpServletRequest
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		req.getSession().setAttribute("user", 800);
		if ("00".equals(req.getParameter("id"))) {
			// 正常通过
			arg2.doFilter(request, response);
		} else {
			// 如果没有携带，那么强制让请求跳转到别的页面
			req.getRequestDispatcher("user.html").forward(request, resp);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
