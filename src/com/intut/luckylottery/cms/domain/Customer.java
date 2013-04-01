package com.intut.luckylottery.cms.domain;

import java.util.Date;

public class Customer {

	private String name;
	private int serialNumber;
	private String series;
	private String ticketNumber;
	private String lotteryType;
	private String bumperName;
	private String phoneNumber;
	private String address;
	private Date date;
	private String emailId;

	public Customer() {
		this.name = "";
		this.series = "";
		this.ticketNumber = "";
		this.lotteryType = "";
		this.bumperName = "";
		this.phoneNumber = "";
		this.address = "";
		this.date = new Date();
		this.emailId = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getBumperName() {
		return bumperName;
	}

	public void setBumperName(String bumperName) {
		this.bumperName = bumperName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
