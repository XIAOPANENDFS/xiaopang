package com.jd.dao;

import java.util.List;

import com.jd.pojo.ShongVo;

public class ShongVoDao extends ABaseDao<ShongVo> {
	//商品列表
	public List<ShongVo> selectAll() {
		return FindSome("SELECT s.*,dp.name as dianpu_name from shong s left join dianpu dp on s.dianpu_id = dp.id;");
	}
	//查询单个商品的方法
	public ShongVo selectOne(int id) {
		return FindByColunm("SELECT s.*,dp.name as dianpu_name from shong s left join dianpu dp on s.dianpu_id = dp.id  where s.id = ?",id);
	}
	
	
}
