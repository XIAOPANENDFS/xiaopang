package com.jd.pojo;

import java.util.Date;

public class ShoppingCartVo {

	private Integer id;
	private String no;
	private Integer shong_id;
	private Integer dianpu_id;
	private Integer number;
	private Float money;
	private Date create_date;
	private Integer status;
	private String shong_name;
	private String dianpu_name;
	private String youhui;
	private int user_id;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShong_id() {
		return shong_id;
	}

	public void setShong_id(Integer shong_id) {
		this.shong_id = shong_id;
	}

	public Integer getDianpu_id() {
		return dianpu_id;
	}

	public void setDianpu_id(Integer dianpu_id) {
		this.dianpu_id = dianpu_id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShong_name() {
		return shong_name;
	}

	public void setShong_name(String shong_name) {
		this.shong_name = shong_name;
	}

	public String getDianpu_name() {
		return dianpu_name;
	}

	public void setDianpu_name(String dianpu_name) {
		this.dianpu_name = dianpu_name;
	}

	public String getYouhui() {
		return youhui;
	}

	public void setYouhui(String youhui) {
		this.youhui = youhui;
	}

}
