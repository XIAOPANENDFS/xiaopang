package com.jd.dao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class ABaseDao<T> {
	// 创建必要
	protected Connection conn;
	protected PreparedStatement pstmt;

	private String driver;
	private String url;
	private String user;
	private String password;
	private Properties prop;

	private static SimpleDateFormat smt_1 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat smt_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ABaseDao() {
		try {
			// 读取配置文件
			prop = new Properties();
			prop.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");

			// 1.通过反射去加载驱动类
			Class.forName(driver);
			// 2.驱动类创建一个连接
			conn = DriverManager.getConnection(url, user, password);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 关闭所有资源
	protected void closeAll() {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 检查连接
	protected void chenkConn() {
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(url, user, password);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 发起查询返回结果
	protected ResultSet executeQuery(String sql, Object... parms) {
		// 取得连接
		chenkConn();
		try {
			// 传入sql
			pstmt = conn.prepareStatement(sql);
			// 设置参数
			if (parms != null && parms.length > 0) {
				for (int i = 0; i < parms.length; i++) {
					pstmt.setObject(i + 1, parms[i]);
				}
			}
			// 发起查询返回值
			return pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// 负责事务的方法
	protected int executeUpdate(String sql, Object... parms) {
		// 取得连接
		chenkConn();
		try {
			// 传入sql
			pstmt = conn.prepareStatement(sql);
			// 设置参数
			if (parms != null && parms.length > 0) {
				for (int i = 0; i < parms.length; i++) {
					pstmt.setObject(i + 1, parms[i]);
				}
			}
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	// 生成pojo的文本 做一个自动化的pojo
	public void autoGenPojoTitle() {
		StringBuilder pojoTitle = new StringBuilder();

		Class<?> daoClz = this.getClass();
		// com.text.dao.EmpsDao
		// System.out.println(c.getName());
		// String pojoName =
		// daoclz.getName().substring(daoclz.getName().indexOf("dao")+4,daoclz.getName().lastIndexOf("Dao"));
		String pojoName = daoClz.getName().substring(daoClz.getName().indexOf("dao") + 4).replace("Dao", "");
		pojoTitle.append(daoClz.getPackage().toString().replace("dao", "pojo"));
		pojoTitle.append(";\nimport java.io.Serializable;\n\n");
		pojoTitle.append("public class " + pojoName + " implements Serializable{\n");

		// 数据库查询
		this.chenkConn();
		try {
			ResultSet rel = conn.createStatement().executeQuery("desc " + pojoName);
			String sub_str = "";
			// 迭代这个数据，生成私有类文本
			while (rel.next()) {
				// System.out.println(rel.getString("field")+" "+rel.getString("type"));
				//
				// pojoTitle.append(" private " + rel.getString("type") + " " +
				// rel.getString("field") + ";\n");
				pojoTitle.append(" private ");
				sub_str = rel.getString("type").substring(0, 3);
				if (sub_str.equals("int")) {
					pojoTitle.append(" Integer ");
				} else if (sub_str.equals("dou")) {
					pojoTitle.append(" double ");
				} else if (sub_str.equals("var")) {
					pojoTitle.append(" String ");
				} else if (sub_str.equals("dou")) {
					pojoTitle.append(" double ");
				} else if (sub_str.equals("dat")) {
					pojoTitle.append(" java.util.Date ");
				}else if(sub_str.equals("flo")) {
					pojoTitle.append(" Float ");
				}

				pojoTitle.append(" ");
				pojoTitle.append(rel.getString("field"));
				pojoTitle.append(";\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pojoTitle.append("}");
		System.out.println(pojoTitle);

	}

	// 填充结果集
	public List<T> addRel(ResultSet rel) {
		List<T> list = new ArrayList();
		try {
			while (rel.next()) {
				// 通过反射获取这个子类的XXXDao方法
				Class<?> daoClz = this.getClass();
				String pojoName = daoClz.getName().replaceFirst("dao", "pojo");

				String className = pojoName.substring(0, pojoName.lastIndexOf("Dao"));
				Class<?> pojoclz = Class.forName(className);
				// 通过实例化获得自己
				Object obj = pojoclz.newInstance();
				String settMthName = null;
				Method setterMethd = null;
				// 取得该类的里面所有声明的变量名
				Field[] fs = pojoclz.getDeclaredFields();
				for (int i = 0; i < fs.length; i++) {
					// 根据这个取得他的方法名
					settMthName = "set" + fs[i].getName().substring(0, 1).toUpperCase() + fs[i].getName().substring(1);
					setterMethd = pojoclz.getMethod(settMthName, fs[i].getType());
					setterMethd.invoke(obj, rel.getObject(fs[i].getName()));
				}
				list.add((T) obj);
			}
			return list;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public T addObject(ResultSet rel) {
		try {
			if (rel.next()) {
				// 通过反射获取这个子类的XXXDao方法
				Class<?> daoClz = this.getClass();
				String pojoName = daoClz.getName().replaceFirst("dao", "pojo");

				String className = pojoName.substring(0, pojoName.lastIndexOf("Dao"));
				Class<?> pojoclz = Class.forName(className);
				// 通过实例化获得自己
				Object obj = pojoclz.newInstance();
				String settMthName = null;
				Method setterMethd = null;
				// 取得该类的里面所有声明的变量名
				Field[] fs = pojoclz.getDeclaredFields();
				for (int i = 0; i < fs.length; i++) {
					// 根据这个取得他的方法名
					settMthName = "set" + fs[i].getName().substring(0, 1).toUpperCase() + fs[i].getName().substring(1);
					setterMethd = pojoclz.getMethod(settMthName, fs[i].getType());
					setterMethd.invoke(obj, rel.getObject(fs[i].getName()));
				}
				return (T) obj;
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 写一个控制sql的方法 type 0:insert 1.delete 2.update 3.查询根据ID查询一个 4.查询全部
	private String genSQL(int type) {
		// 获得自己的类
		Class<?> daoClz = this.getClass();
		String pojoName = daoClz.getName().toString().replace("dao", "pojo");
		// pojoName = pojoName.substring(0, pojoName.length() -3);
		pojoName = pojoName.substring(0, pojoName.lastIndexOf("Dao"));
		// 获取表名
		String tableName = "" + pojoName.substring(pojoName.lastIndexOf(".") + 1);
		//
		StringBuilder sql = new StringBuilder();
		try {
			// 反射获取pojo类
			Class<?> pojoClz = Class.forName(pojoName);

			Field[] fields = pojoClz.getDeclaredFields();

			// 先写delete
			if (type == 1) {
				sql.append("delete from ");
				sql.append(tableName);
				sql.append(" where ");
				// 因为我们的pojo都是以id开头，所以这里可以直接使用0下标
				sql.append(fields[0].getName());
				sql.append(" = ?");
			} else if (type == 0) {
				sql.append("insert into ");
				sql.append(tableName);
				sql.append(" (");
				// id不需要使用，因为默认主键都是自增列
				for (int i = 1; i < fields.length; i++) {
					sql.append(fields[i].getName());
					// 最后一个不加逗号
					if (i < fields.length - 1) {
						sql.append(",");
					}

				}
				sql.append(") values (");
				for (int i = 1; i < fields.length; i++) {
					sql.append("?");
					if (i < fields.length - 1) {
						sql.append(",");
					}
				}
				sql.append(")");
			} else if (type == 2) {
				// 更新
				sql.append("update ");
				sql.append(tableName);
				sql.append(" set ");
				for (int i = 1; i < fields.length; i++) {
					sql.append(fields[i].getName());
					sql.append("=?");
					if (i < fields.length - 1) {
						sql.append(",");
					}
				}

				sql.append(" where ");
				sql.append(fields[0].getName());
				sql.append(" = ?");

			} else if (type == 3) {

				sql.append("select * from ");
				sql.append(tableName);
				sql.append(" where ");
				sql.append(fields[0].getName());
				sql.append(" =?");
			} else if (type == 4) {
				sql.append("select * from ");
				sql.append(tableName);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql.toString();
	}

	// 公开一个删除方法
	public String delete(String id) {
		return this.executeUpdate(genSQL(1), id) == 1 ? "删除成功" : "删除失败";
	}

	// 公开一个增加方法 增加的时候，第一个id就不需要放进去
	public String save(Object obj) {
		// 原本为Emps 还原成Emps
		Class<?> pojoClz = obj.getClass();
		// System.out.println(pojoClz.getName());
		// 取得所有属性---->属性名---->gette方法 调用取值
		Field[] fs = pojoClz.getDeclaredFields();
		// 生成参数
		Object[] parms = new Object[fs.length - 1];
		String getterMthd = null;
		for (int i = 1; i < fs.length; i++) {
			getterMthd = "get" + fs[i].getName().substring(0, 1).toUpperCase() + fs[i].getName().substring(1);
			// System.out.println(getterMthd);
			try {
				// 获得对应的get方法
				parms[i - 1] = pojoClz.getMethod(getterMthd).invoke(obj);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 执行添加
		int i = this.executeUpdate(this.genSQL(0), parms);
		// 生成参数
		return i == 1 ? "添加成功" : "添加失败";
	}

	// update方法
	public String update(Object obj) {
		Class<?> pojoClz = obj.getClass();
		Field[] fs = pojoClz.getDeclaredFields();
		// 生成参数
		Object[] parms = new Object[fs.length];
		String getterMthd = null;
		int act = -1;
		for (int i = 0; i < fs.length; i++) {
			getterMthd = "get" + fs[i].getName().substring(0, 1).toUpperCase() + fs[i].getName().substring(1);
			// System.out.println(getterMthd);
			try {
				if (i == 0) {
					parms[fs.length - 1] = pojoClz.getMethod(getterMthd).invoke(obj);
				} else {
					// 获得对应的get方法
					parms[i - 1] = pojoClz.getMethod(getterMthd).invoke(obj);
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 执行添加
		act = this.executeUpdate(this.genSQL(2), parms);
		// 生成参数
		return act == 1 ? "更新成功" : "更新失败";
	}

	// 最初的全部查询
	protected List<T> FindSome(String sql, Object... params) {
		ResultSet rs = executeQuery(sql, params);
		return addRel(rs);
	}

	// 查询全部
	public List<T> FindByAll(Object... params) {
		return this.FindSome(genSQL(4), params);
	}

	// 通过字段查询一条记录
	protected T FindByColunm(String sql, Object... params) {
		ResultSet rs = executeQuery(sql, params);
		T objt = addObject(rs);
		this.chenkConn();
		return objt;
	}

}
