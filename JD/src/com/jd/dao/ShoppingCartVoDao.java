package com.jd.dao;

import java.util.List;

import com.jd.pojo.ShoppingCartVo;

public class ShoppingCartVoDao extends ABaseDao<ShoppingCartVo> {

	public List<ShoppingCartVo> selectList(int id) {
		String sql = "select sc.*,s.name as shong_name,dp.name as dianpu_name ,dp.youhui ,u.name as user_name  from shoppingcart sc left join shong s on sc.shong_id = s.id left join dianpu dp on sc.dianpu_id = dp.id  LEFT join user u on sc.user_id = u.id  where u.id = ? and sc.status = 0 ORDER BY sc.dianpu_id ";
		return FindSome(sql, id);
	}
}
