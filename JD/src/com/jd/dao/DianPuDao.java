package com.jd.dao;

import java.util.List;

import com.jd.pojo.DianPu;

public class DianPuDao extends ABaseDao<DianPu> {


//	public static void main(String[] args) {
//		DianPuDao a =new DianPuDao();
//		//自动生成
//		a.autoGenPojoTitle();
//	}
	// 编写一个店铺的储存方法
	public String save22(DianPu obj) {
		// 这个自带的单表存储方法
		return save(obj);
		
	}

	// 编写删除方法
	public String deleteOne(String id) {
		return delete(id);
	}

	// 更新
	public String update(DianPu obj) {
		return update(obj);
	}

	// 查询全部
	public List<DianPu> selectAll() {
		return FindByAll();
	}

	// 查询单个
	public DianPu selectOne() {
		return FindByColunm("select * from  DianPu");
	}

}
