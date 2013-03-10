package com.intut.luckylottery.domain;

import java.util.Date;

public class NewCustomer {

	private String name;
	private String code;
	private String series;
	private String ticketNumber;
	private String phoneNumber;
	private String emailId;
	private String address;
	private String typeBumper;
	private String typeMonthly;
	private int countBumper;
	private int countMonthly;
	private String status;
	private String remarks;
	private boolean isNumberValid;
	private String monthlyIds;
	private String bumperIds;
	private Date lotteryDate;
	private Date createdDate;
	private Date updatedDate;

	public NewCustomer() {
		this.name = "";
		this.code = "";
		this.series = "";
		this.ticketNumber = "";
		this.phoneNumber = "";
		this.emailId = "";
		this.address = "";
		this.typeBumper = "";
		this.typeMonthly = "";
		this.status = "";
		this.remarks = "";
		this.monthlyIds = "";
		this.bumperIds = "";
		setLotteryDate(new Date());
		setCreatedDate(new Date());
		setUpdatedDate(new Date());
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTypeBumper() {
		return typeBumper;
	}

	public void setTypeBumper(String typeBumper) {
		this.typeBumper = typeBumper;
	}

	public String getTypeMonthly() {
		return typeMonthly;
	}

	public void setTypeMonthly(String typeMonthly) {
		this.typeMonthly = typeMonthly;
	}

	public int getCountBumper() {
		return countBumper;
	}

	public void setCountBumper(int countBumper) {
		this.countBumper = countBumper;
	}

	public int getCountMonthly() {
		return countMonthly;
	}

	public void setCountMonthly(int countMonthly) {
		this.countMonthly = countMonthly;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isNumberValid() {
		return isNumberValid;
	}

	public void setNumberValid(boolean isNumberValid) {
		this.isNumberValid = isNumberValid;
	}

	public String getMonthlyIds() {
		return monthlyIds;
	}

	public void setMonthlyIds(String monthlyIds) {
		this.monthlyIds = monthlyIds;
	}

	public String getBumperIds() {
		return bumperIds;
	}

	public void setBumperIds(String bumperIds) {
		this.bumperIds = bumperIds;
	}

	public Date getLotteryDate() {
		return lotteryDate;
	}

	public void setLotteryDate(Date lotteryDate) {
		this.lotteryDate = lotteryDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
