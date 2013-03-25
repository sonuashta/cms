package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.intut.luckylottery.cms.crudDatabase.Dbloader;
import com.intut.luckylottery.cms.domain.Customer;
import com.intut.luckylottery.cms.domain.Fields;
import com.intut.luckylottery.cms.domain.Message;
import com.intut.luckylottery.cms.domain.NewCustomer;
import com.intut.luckylottery.cms.util.Constants;
import com.intut.luckylottery.cms.util.LotteryLogger;
import com.intut.luckylottery.cms.util.Util;

public class ManualEntrydialogModelProvider {

	public ManualEntrydialogModelProvider() throws Exception {
		newCustomer = new NewCustomer();
		dbLoader = new Dbloader();

		this.startingSerialNumber = dbLoader.getSerialNumber();

		setSerialNumber("" + startingSerialNumber);
		customers = new ArrayList<Customer>();
		setDate(new Date());
		setConfigBumper(dbLoader.getSelectedBumper());
	}

	public void setMobileText(Text text) {
		mobileText = text;
	}

	private String configBumper;
	private Text mobileText;
	private boolean dbStatus;
	private String dbErrorMessage;
	private String name;
	private String serialNumber;
	private Date date;
	private String phoneNumber;
	private String emailId;
	private String address;
	private NewCustomer newCustomer;
	private String monthlyTickets;
	private String bumperTickets;

	private Dbloader dbLoader;
	private int startingSerialNumber;
	private List<Customer> customers;
	private String selectedBumper;
	private boolean sendSMSButton;
	private boolean saveToDatabase;

	public boolean isSaveToDatabase() {
		return saveToDatabase;
	}

	public void setSaveToDatabase(boolean saveToDatabase) {
		propertyChangeSupport.firePropertyChange("saveToDatabase",
				this.saveToDatabase, this.saveToDatabase = saveToDatabase);
	}

	public String getSelectedBumper() {
		return selectedBumper;
	}

	public void setSelectedBumper(String selectedBumper) {
		propertyChangeSupport.firePropertyChange("selectedBumper",
				this.selectedBumper, this.selectedBumper = selectedBumper);
	}

	public String getName() {
		return name;
	}

	public NewCustomer getNewCustomer() {
		return newCustomer;
	}

	public void setName(String name) {
		propertyChangeSupport.firePropertyChange("name", this.name,
				this.name = name);
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.startingSerialNumber = Integer.parseInt(serialNumber);
		propertyChangeSupport.firePropertyChange("serialNumber",
				this.serialNumber, this.serialNumber = serialNumber);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		propertyChangeSupport.firePropertyChange("date", this.date,
				this.date = date);
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
		propertyChangeSupport.firePropertyChange("emailId", this.emailId,
				this.emailId = emailId);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		propertyChangeSupport.firePropertyChange("address", this.address,
				this.address = address);
	}

	public void setDoneButtonEnable(boolean doneButtonEnable) {

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

	public String getErrorMessage() {
		return this.dbErrorMessage;
	}

	public void logMessage() {
		String msg = "Message sent to ";
		msg += "name:";
		msg += Util.isStringNullOrEmpty(getName()) ? "---" : getName();
		msg += "number:";
		msg += Util.isStringNullOrEmpty(getPhoneNumber()) ? "---"
				: getPhoneNumber();
		msg += "bumpertickets:";
		msg += Util.isStringNullOrEmpty(bumperTickets) ? "---" : bumperTickets;
		msg += "monthly:";
		msg += Util.isStringNullOrEmpty(monthlyTickets) ? "---"
				: monthlyTickets;
		msg += "on " + new Date();
		LotteryLogger.getInstance().setInfo(msg);
	}

	public boolean saveCustomer() {
		dbStatus = true;
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display
				.getCurrent().getActiveShell());

		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					monitor.beginTask("Saving Data in database....", 2);
					// begin task
					monitor.worked(1);

					try {
						dbLoader.savePendings(getCustomers(), false, false,
								Constants.pendingMessage);
					} catch (Exception e) {
						dbErrorMessage = e.getMessage();
						dbStatus = false;
					}

					monitor.setTaskName("Done");

					monitor.done();

				}
			});
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Progress Dialog Error:" + e.getMessage());

		}
		return dbStatus;
	}

	private List<Customer> getCustomers() {
		int serialNumber = startingSerialNumber;

		List<Customer> customers = new ArrayList<Customer>();
		if (!Util.isStringNullOrEmpty(bumperTickets)) {
			String bumperList[] = bumperTickets.split(",");
			for (String string : bumperList) {
				if (Util.isStringNullOrEmpty(string))
					continue;
				Customer customer = new Customer();
				customer.setName(getName());
				customer.setAddress(getAddress());
				customer.setDate(getDate());
				customer.setEmailId(getEmailId());
				customer.setPhoneNumber(getPhoneNumber());
				customer.setSerialNumber(serialNumber++);

				customer.setTicketNumber(string);
				customer.setBumperName(selectedBumper);
				customer.setLotteryType(Fields.typeBumper);
				customers.add(customer);
			}
		}
		if (!Util.isStringNullOrEmpty(monthlyTickets)) {
			String monthlyList[] = monthlyTickets.split(",");
			for (String string : monthlyList) {
				if (Util.isStringNullOrEmpty(string))
					continue;
				Customer customer = new Customer();
				customer.setAddress(getAddress());
				customer.setDate(getDate());
				customer.setName(getName());
				customer.setEmailId(getEmailId());
				customer.setPhoneNumber(getPhoneNumber());
				customer.setSerialNumber(serialNumber++);
				customer.setTicketNumber(string);
				customer.setLotteryType(Fields.typeMonthly);
				customers.add(customer);
			}
		}
		return customers;
	}

	public boolean sendAndDisplayMessage() {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display
				.getCurrent().getActiveShell());
		dbStatus = true;
		dbErrorMessage = "";
		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					monitor.beginTask("Initializing Sending Process", 3);
					// begin task
					monitor.worked(1);

					monitor.setTaskName("Sending Message where message is "
							+ filterMessageText());
					Message message = sendSms();
					boolean isMessageSend = false;
					if (message.getCode() == HttpURLConnection.HTTP_OK)
						isMessageSend = true;

					monitor.worked(2);
					monitor.setTaskName("Saving to database");
					// TODO add mail send functionality
					boolean isMailSend = true;
					try {
						dbLoader.savePendings(getCustomers(), isMessageSend,
								isMailSend, message.getMessage()
										+ ",and code is " + message.getCode());
					} catch (Exception e) {
						dbStatus = false;
						dbErrorMessage = e.getMessage();
					}
					monitor.setTaskName("Done");

					monitor.done();

				}
			});
		} catch (InvocationTargetException e) {
			LotteryLogger.getInstance().setError(
					"Progress Dialog Error:" + e.getMessage());

		} catch (InterruptedException e) {
			LotteryLogger.getInstance().setError(
					"Progress Dialog Error:" + e.getMessage());
		}

		return dbStatus;

	}

	private int getBumperTicketCount() {
		int count = 0;
		if (!Util.isStringNullOrEmpty(monthlyTickets))
			count += monthlyTickets.split(",").length;
		return count;
	}

	private int getMonthlyTicketCount() {
		int count = 0;
		if (!Util.isStringNullOrEmpty(bumperTickets))
			count += bumperTickets.split(",").length;
		return count;
	}

	private String filterMessageText() {
		String messageText = "Lucky Lottery Agency Zirakpur.";
		int monthlyCount = getMonthlyTicketCount();
		int bumperCount = getBumperTicketCount();
		if ((monthlyCount + bumperCount) >= 5) {
			messageText += "Your ";
			if (bumperCount > 0)
				messageText += "bumper tickets count is " + bumperCount
						+ ", and ";
			messageText += "monthly count is " + monthlyCount
					+ (monthlyCount + bumperCount) + ", where bumper count is "
					+ bumperCount + " and monthly count is " + monthlyCount;
		} else {
			String message = "Your bumper tickets are ";
			if (!Util.isStringNullOrEmpty(bumperTickets)) {
				for (String s : bumperTickets.split(",")) {
					message += s + ",";

				}
				message = message.replaceAll(",$", "") + " and ";
			} else {
				message = "";
			}

			message += "Your monthly tickets are ";
			if (!Util.isStringNullOrEmpty(monthlyTickets)) {
				for (String s : monthlyTickets.split(",")) {
					message += s + ",";
				}
			}
			messageText += message.replaceAll(",$", "");
		}
		return messageText
				+ ". for info www.luckylotteryagency.com, 9815788878";
	}

	public Message sendSms() {
		Message message = new Message();
		String str;

		String messageText = filterMessageText();
		if (Util.isStringNullOrEmpty(messageText))
			messageText = "empty";
		try {
			URI uri = new URI("http", messageText, "");

			// URL onlyMsgUrl = uri.toURL();

			URL msgUrl = new URL(
					"http://dndopen.dove-sms.com/SMSAPI.jsp?username="
							+ Util.getUserName() + "&password="
							+ Util.getPassword() + "&sendername="
							+ Util.getSenderName() + "&mobileno=91"
							+ getPhoneNumber() + "&message="
							+ uri.toString().replace("http:", ""));

			HttpURLConnection connection = (HttpURLConnection) msgUrl
					.openConnection();
			connection.setDoOutput(false);
			connection.setDoInput(true);

			String res = connection.getResponseMessage();
			System.out.println(res);
			String returnstring = "";
			int code = connection.getResponseCode();
			message.setCode(code);
			if (code == HttpURLConnection.HTTP_OK) {
				// Get response data.
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));

				while (null != ((str = in.readLine()))) {
					returnstring = returnstring + str;
				}
			}
			message.setMessage(returnstring);
			connection.disconnect();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in sending message," + e.getMessage());
			message.setCode(0);
			message.setMessage("Exception");
		}
		return message;
	}

	public String getMonthlyTickets() {
		return monthlyTickets;
	}

	public void setMonthlyTickets(String monthlyTickets) {
		if (Util.isStringNullOrEmpty(monthlyTickets)
				&& Util.isStringNullOrEmpty(bumperTickets)) {
			setSaveToDatabase(false);
			setSendSMSButton(false);
		} else {
			setSaveToDatabase(true);
			setSendSMSButton(true);

		}
		propertyChangeSupport.firePropertyChange("monthlyTickets",
				this.monthlyTickets, this.monthlyTickets = monthlyTickets);
	}

	public String getBumperTickets() {
		return bumperTickets;
	}

	public void setBumperTickets(String bumperTickets) {
		if (Util.isStringNullOrEmpty(monthlyTickets)
				&& Util.isStringNullOrEmpty(bumperTickets)) {
			setSaveToDatabase(false);
			setSendSMSButton(false);
		} else {
			setSaveToDatabase(true);
			setSendSMSButton(true);

		}
		propertyChangeSupport.firePropertyChange("bumperTickets",
				this.bumperTickets, this.bumperTickets = bumperTickets);
	}

	public boolean isSendSMSButton() {
		return sendSMSButton;
	}

	public void setSendSMSButton(boolean sendSMSButton) {
		propertyChangeSupport.firePropertyChange("sendSMSButton",
				this.sendSMSButton, this.sendSMSButton = sendSMSButton);
	}

	public void resetMobileText() {
		Display.getCurrent().asyncExec(new Runnable() {

			@Override
			public void run() {
				mobileText.setText("");

			}
		});
	}

	public boolean resetData() {
		dbStatus = true;
		dbErrorMessage = "";
		setDate(new Date());
		setBumperTickets("");
		setEmailId("");
		setAddress("");
		setMonthlyTickets("");
		setName("");
		resetMobileText();
		try {
			setSerialNumber("" + dbLoader.getSerialNumber());
		} catch (Exception e) {
			dbStatus = false;
			dbErrorMessage = e.getMessage();
		}
		return dbStatus;
	}

	public String getConfigBumper() {
		return configBumper;
	}

	public void setConfigBumper(String configBumper) {
		this.configBumper = configBumper;
	}

}
