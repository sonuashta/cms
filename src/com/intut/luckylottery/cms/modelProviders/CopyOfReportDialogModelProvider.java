package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import com.intut.luckylottery.cms.crudDatabase.Dbloader;
import com.intut.luckylottery.cms.domain.Fields;
import com.intut.luckylottery.cms.domain.SearchFilter;
import com.intut.luckylottery.cms.domain.SearchResult;
import com.intut.luckylottery.cms.dummydata.GetDummyData;
import com.intut.luckylottery.cms.util.Constants;


public class CopyOfReportDialogModelProvider {

	public CopyOfReportDialogModelProvider() {
		loader = new Dbloader();
		setBumpers(GetDummyData.getBumperNames());
		setMonthlyRadioButton(true);
		setFromDate(new Date());
		setToDate(new Date());
		setSendMessages(false);
	}

	private String emailId;
	private String ticketNumber;
	private String address;
	private String phoneNumber;
	private String selectedBumper;
	private boolean monthlyRadioButton;
	private boolean bumperRadioButton;
	private boolean bothRadioButton;
	private Date fromDate;
	private Date toDate;
	private String[] bumpers;
	private Dbloader loader;
	private boolean sendMessages;
	private boolean comboVisible;
	private String name;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		propertyChangeSupport.firePropertyChange("emailId", this.emailId,
				this.emailId = emailId);
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		propertyChangeSupport.firePropertyChange("ticketNumber",
				this.ticketNumber, this.ticketNumber = ticketNumber);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		propertyChangeSupport.firePropertyChange("address", this.address,
				this.address = address);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		propertyChangeSupport.firePropertyChange("phoneNumber",
				this.phoneNumber, this.phoneNumber = phoneNumber);
	}

	public String getSelectedBumper() {
		return selectedBumper;
	}

	public void setSelectedBumper(String selectedBumper) {
		propertyChangeSupport.firePropertyChange("selectedBumper",
				this.selectedBumper, this.selectedBumper = selectedBumper);
	}

	public boolean isMonthlyRadioButton() {
		return monthlyRadioButton;
	}

	public void setMonthlyRadioButton(boolean monthlyRadioButton) {
		propertyChangeSupport.firePropertyChange("monthlyRadioButton",
				this.monthlyRadioButton,
				this.monthlyRadioButton = monthlyRadioButton);
		if (monthlyRadioButton)
			setComboVisible(false);
	}

	public boolean isBumperRadioButton() {
		return bumperRadioButton;
	}

	public void setBumperRadioButton(boolean bumperRadioButton) {

		propertyChangeSupport.firePropertyChange("bumperRadioButton",
				this.bumperRadioButton,
				this.bumperRadioButton = bumperRadioButton);
		if (bumperRadioButton || bothRadioButton)
			setComboVisible(true);
		else {
			setComboVisible(false);
		}
	}

	public boolean isBothRadioButton() {

		return bothRadioButton;
	}

	public void setBothRadioButton(boolean bothRadioButton) {

		propertyChangeSupport.firePropertyChange("bothRadioButton",
				this.bothRadioButton, this.bothRadioButton = bothRadioButton);
		if (bumperRadioButton || bothRadioButton)
			setComboVisible(true);
		else {
			setComboVisible(false);
		}
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		propertyChangeSupport.firePropertyChange("fromDate", this.fromDate,
				this.fromDate = fromDate);
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		propertyChangeSupport.firePropertyChange("toDate", this.toDate,
				this.toDate = toDate);
	}

	public String[] getBumpers() {
		return bumpers;
	}

	public void setBumpers(String[] bumpers) {
		this.bumpers = bumpers;
	}

	public boolean isSendMessages() {
		return sendMessages;
	}

	public void setSendMessages(boolean sendMessages) {
		propertyChangeSupport.firePropertyChange("sendMessages",
				this.sendMessages, this.sendMessages = sendMessages);
	}

	public boolean isComboVisible() {
		return comboVisible;
	}

	public void setComboVisible(boolean comboVisible) {
		propertyChangeSupport.firePropertyChange("comboVisible",
				this.comboVisible, this.comboVisible = comboVisible);
	}

	public SearchResult searchResults() throws Exception {
		String bumperName = null;
		String lotteryType = null;
		if (bothRadioButton || bumperRadioButton) {
			bumperName = getSelectedBumper();
			lotteryType = Fields.typeBumper;
		}
		SearchFilter filter = new SearchFilter();
		filter.setAddress(getAddress());
		filter.setBothSelected(isBothRadioButton());
		filter.setBumperName(bumperName);
		filter.setEmailId(getEmailId());
		filter.setFromdate(getFromDate());
		filter.setLotteryType(lotteryType);
		filter.setMessageSend(isSendMessages());
		filter.setName(getName());
		filter.setPageCount(Constants.pageCount);
		filter.setPageNumber(1);
		filter.setPhoneNumber(getPhoneNumber());
		filter.setTicketNumber(getTicketNumber());
		filter.setToDate(getToDate());
		SearchResult searchResult = loader.getCustomers(filter);
		return searchResult;
	}

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		propertyChangeSupport.firePropertyChange("name", this.name,
				this.name = name);
	}

}
