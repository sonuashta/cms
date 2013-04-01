package com.intut.luckylottery.cms.domain;

import java.util.Date;

public class SearchFilter {

	private String name;
	private String ticketNumber;
	private String lotteryType;
	private String bumperName;
	private String phoneNumber;
	private String address;
	private Date fromdate;
	private Date toDate;
	private boolean isMessageSend;
	private String emailId;
	private int pageCount;
	private int pageNumber;
	private boolean bothSelected;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public boolean isMessageSend() {
		return isMessageSend;
	}

	public void setMessageSend(boolean isMessageSend) {
		this.isMessageSend = isMessageSend;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public boolean isBothSelected() {
		return bothSelected;
	}

	public void setBothSelected(boolean bothSelected) {
		this.bothSelected = bothSelected;
	}

}
