package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import com.intut.luckylottery.cms.dialogs.ProcessesProgressDialog;
import com.intut.luckylottery.crudDatabase.Dbloader;
import com.intut.luckylottery.domain.Customer;

public class ProcessDialogModelProvider {
	public ProcessDialogModelProvider(String processName) throws Exception {
		setName(processName);
		dbLoader = new Dbloader();
		setTotal("" + dbLoader.getUniqueCustomersMessagesCount());
		setLeftProcesses(""
				+ dbLoader.getUniqueMessagesCountFromTable(processName));
		setLeftMails("" + dbLoader.getUniqueMailsCountFromTable(processName));
		setTotalEmails("" + dbLoader.getUniqueCustomersMailsCount());
	}

	private Dbloader dbLoader;
	private String name;
	private String total;
	private String leftProcesses;
	private String leftMails;
	private String totalEmails;
	private String message;

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

	public String getTotalEmails() {
		return totalEmails;
	}

	public void setTotalEmails(String totalEmails) {
		propertyChangeSupport.firePropertyChange("totalEmails",
				this.totalEmails, this.totalEmails = totalEmails);
	}

	public void processMessages() throws Exception {
		List<Customer> customers = dbLoader
				.getUniqueMessagesFromTable(getName());
		ProcessesProgressDialog dialog = new ProcessesProgressDialog(Display
				.getCurrent().getActiveShell(), customers, false, getMessage(),
				name, this);
		dialog.open();
	}

	public void processMails() throws Exception {
		List<Customer> customers = dbLoader.getUniqueMailsFromTable(getName());
		ProcessesProgressDialog dialog = new ProcessesProgressDialog(Display
				.getCurrent().getActiveShell(), customers, true, "", name, this);
		dialog.open();

	}

	public String getMessage() {
		return message;
	}

	public void resetData() throws Exception {
		setTotal("" + dbLoader.getUniqueCustomersMessagesCount());
		setLeftProcesses("" + dbLoader.getUniqueMessagesCountFromTable(name));
		setLeftMails("" + dbLoader.getUniqueMailsCountFromTable(name));
		setTotalEmails("" + dbLoader.getUniqueCustomersMailsCount());
		setMessage("");
	}

	public void setMessage(String message) {
		propertyChangeSupport.firePropertyChange("message", this.message,
				this.message = message);
	}

}
