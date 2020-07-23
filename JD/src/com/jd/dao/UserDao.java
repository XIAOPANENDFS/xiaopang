package com.jd.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jd.pojo.User;

public class UserDao extends ABaseDao<User> {

//	public static void main(String[] args) {
//		UserDao a = new UserDao();
//		a.autoGenPojoTitle();
//	}

	// 登录方法
	public User selectByUser(User user) {
		User obj = FindByColunm("select * from  user where user = ?", user.getUser());
		return obj;
	}
}
