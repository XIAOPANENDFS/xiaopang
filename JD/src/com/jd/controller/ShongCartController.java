package com.jd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jd.dao.ShoppingCartDao;
import com.jd.dao.ShoppingCartVoDao;
import com.jd.pojo.ShoppingCart;

import net.sf.json.JSONArray;

@WebServlet("/ShoppingCart")
public class ShongCartController extends HttpServlet {

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
			insert(request, response);
		} else if ("list".equals(mthod)) {
			list(request, response);
		}
	}

	public void list(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ShoppingCartVoDao dao = new ShoppingCartVoDao();
		JSONArray rel = JSONArray.fromObject(dao.selectList(id));

		PrintWriter pw = response.getWriter();
		pw.write(rel.toString());
		pw.flush();
		pw.close();

	}

	// 展示商品列表
	public void insert(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int shong_id = Integer.parseInt(request.getParameter("shong_id"));
		int dianpu_id = Integer.parseInt(request.getParameter("dianpu_id"));
		float money = Float.parseFloat(request.getParameter("money"));

		// 处理编号
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
		String no = formatter2.format(new Date()) + shong_id;

		ShoppingCart obj = new ShoppingCart();
		obj.setDianpu_id(dianpu_id);
		obj.setShong_id(shong_id);
		obj.setMoney(money);
		obj.setNo(no);
		obj.setStatus(0);
		//这个userid需要正确的
		obj.setUser_id(1);

		obj.setCreate_date(new Date());
		// 购买数量是1
		obj.setNumber(1);

		ShoppingCartDao dao = new ShoppingCartDao();
		String rel = dao.save(obj);

		PrintWriter pw = response.getWriter();
		pw.write(rel);

		pw.flush();
		pw.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 处理跨域的机制
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

		// 处理乱码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
