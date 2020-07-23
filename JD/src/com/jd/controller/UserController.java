package com.jd.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jd.dao.UserDao;
import com.jd.pojo.User;

@WebServlet("/user")
public class UserController extends BaseController {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 走向值
		String method = request.getParameter("method");
		if ("logo".equals(method)) {
			// 需要执行增加功能
			logo(request, response);
		} else if ("insert".equals(method)) {

		}
	}

	public void logo(HttpServletRequest request, HttpServletResponse response) throws IOException {

		UserDao dao = new UserDao();
		User a = (User) FormData(request, User.class);
		System.out.println(a.toString());
		User user = dao.selectByUser((User) FormData(request, User.class));
		PrintWriter pw = response.getWriter();

		if (user != null) {
			if (request.getParameter("pwd").equals(user.getPwd())) {
				//将标志存放Session
				request.getSession().setAttribute("user", user.getId());
				pw.write("200");
			} else {
				// 202为密码错误
				pw.write("202");
			}
		} else {
			// 201代码为用户不存在
			pw.write("201");
		}
		// 强制刷新输出
		pw.flush();
		pw.close();
	}
}
