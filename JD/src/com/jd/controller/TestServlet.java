package com.jd.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		// AJAX 异步请求机制(不想跳转页面的情况下，而使用的方法)

		// 转发和重定向 在没有使用jsp的情况下，作用意义一样
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		
		if ("张三".equals(user)) {
			if ("1230".equals(pwd)) {
				System.out.println("登录成功:转发");
				request.getRequestDispatcher("index.html").forward(request, response);
			} else {
				System.out.println("密码错误:重定向");
				response.sendRedirect("shong.html");
			}
		} else {
			System.out.println("用户不存在:重定向");
			response.sendRedirect("shong.html");
		}

//		JSP

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String a = "100";
		String b = "100";
		
		System.out.println(a.equals(b));

		doGet(request, response);
	}

}
