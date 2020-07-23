package com.jd.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jd.dao.DianPuDao;
import com.jd.dao.ShongVoDao;
import com.jd.pojo.DianPu;
import com.jd.pojo.ShongVo;
import com.mysql.cj.xdevapi.JsonArray;

import net.sf.json.JSONArray;

@WebServlet("/Shong")
public class ShongController extends HttpServlet {
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
		if ("list".equals(mthod)) {
			// 需要执行增加功能
			list(request, response);
		}
		// 当从前端接收的到的method是selectOne ，就是表示查询单个字段
		else if ("selectOneToPage".equals(mthod)) {
			selectOneToPage(request, response);
		}
	}

	// 展示商品列表
	public void list(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter pw = response.getWriter();
		ShongVoDao dao = new ShongVoDao();

		JSONArray a = new JSONArray();
		pw.write(a.fromObject(dao.selectAll()).toString());
		pw.flush();
		pw.close();

	}

	// 查询单个商品，并且跳转页面
	public void selectOneToPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String val = request.getParameter("id");
		ShongVoDao dao = new ShongVoDao();
		// 判断一下从前端接收的数据是否为空
		if (val != null && !val.equals("")) {
			int id = Integer.parseInt(val);
			ShongVo vo = dao.selectOne(id);
			// 1.将这个值放入到request作用域
			request.setAttribute("rel", vo);
			// 2.通过转发方式，将这个ShongVo对象放入的jsp的作用域
			request.getRequestDispatcher("shong_info.jsp").forward(request, response);

		} else {
			// 错误处理...
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
