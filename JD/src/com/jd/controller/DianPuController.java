package com.jd.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jd.dao.DianPuDao;
import com.jd.pojo.DianPu;

@WebServlet("/DianPu")
public class DianPuController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 处理跨域的机制
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

		// 处理乱码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String mthod = request.getParameter("method");
		if ("insert".equals(mthod)) {
			// 需要执行增加功能
			insertInto(request, response);
		}else if("delete".equals(mthod)) {
			
		}
	}

	private void insertInto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		// 这里接收的数据哪怕是空的，设置为空字符串 ''
		String name = request.getParameter("name");
		String youhui = request.getParameter("youhui");
		DianPuDao dao = new DianPuDao();
		DianPu obj = new DianPu();
		obj.setName(name);
		obj.setYouhui(youhui);
		pw.write(dao.save22(obj));
		// 因为是ajax请求机制，所以不能再随意发送转发和重定向
		pw.flush();
		pw.close();
	}

	//

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	

}
