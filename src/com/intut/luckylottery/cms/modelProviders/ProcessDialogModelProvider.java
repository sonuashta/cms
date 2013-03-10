package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.intut.luckylottery.crudDatabase.Dbloader;

public class ProcessDialogModelProvider {
	public ProcessDialogModelProvider(String processName) {
		setName(processName);
		dbLoader = new Dbloader();
//		setTotal(""+dbLoader.getUniqueCustomersMessagesCountFromTable());
//		setLeftProcesses(""+dbLoader.getUniqueCustomersMessagesCountFromTable());
	}

	private Dbloader dbLoader;
	private String name;
	private String total;
	private String leftProcesses;
	private String leftMails;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		propertyChangeSupport.firePropertyChange("name", this.name,
				this.name = name);
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		propertyChangeSupport.firePropertyChange("total", this.total,
				this.total = total);
	}

	public String getLeftProcesses() {
		return leftProcesses;
	}

	public void setLeftProcesses(String leftProcesses) {
		propertyChangeSupport.firePropertyChange("leftProcesses",
				this.leftProcesses, this.leftProcesses = leftProcesses);
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

	public String getLeftMails() {
		return leftMails;
	}

	public void setLeftMails(String leftMails) {
		propertyChangeSupport.firePropertyChange("leftMails", this.leftMails,
				this.leftMails = leftMails);
	}

}
