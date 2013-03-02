package com.intut.luckylottery.domain;

public class Lottery {

	private String type;
	private String name;
	private int id;
	public Lottery(){
		type="";
		name="";
		id=0;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
