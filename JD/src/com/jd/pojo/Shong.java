package com.jd.pojo;

import java.io.Serializable;

public class Shong implements Serializable {
	private Integer id;
	private Integer dianpu_id;
	private String name;
	private Float money;
	private Integer number;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDianpu_id() {
		return dianpu_id;
	}

	public void setDianpu_id(Integer dianpu_id) {
		this.dianpu_id = dianpu_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}