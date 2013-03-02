package com.intut.luckylottery.domain;

import java.util.Date;

public class Customer {

	private String name;
	private String code;
	private String mobile1;
	private String mobile2;
	private String address;
	private String city;
	private String state;
	private String email;
	private String zip;
	private int lotterTypeId;
	private boolean smsSend;
	private Date createdDate;
	private Date deletedDate;

	public Customer() {
		this.name = "";
		this.code = "";
		this.mobile1 = "";
		this.mobile2 = "";
		this.address = "";
		this.city = "";
		this.state = "";
		this.email = "";
		this.zip = "";
		this.createdDate = new Date();
		this.deletedDate = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public int getLotterTypeId() {
		return lotterTypeId;
	}

	public void setLotterTypeId(int lotterTypeId) {
		this.lotterTypeId = lotterTypeId;
	}

	public boolean isSmsSend() {
		return smsSend;
	}

	public void setSmsSend(boolean smsSend) {
		this.smsSend = smsSend;
	}

	
}
