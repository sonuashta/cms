package com.intut.luckylottery.cms.modelProviders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import com.intut.luckylottery.cms.dialogs.AddEditMonthlyTicket;
import com.intut.luckylottery.cms.util.Constants;
import com.intut.luckylottery.cms.util.LotteryLogger;
import com.intut.luckylottery.cms.util.Util;
import com.intut.luckylottery.crudDatabase.Dbloader;
import com.intut.luckylottery.domain.Customer;
import com.intut.luckylottery.domain.Fields;
import com.intut.luckylottery.domain.NewCustomer;

public class ManualEntrydialogModelProvider {

	public ManualEntrydialogModelProvider() {
		newCustomer = new NewCustomer();
		this.bumperWritableList = new WritableList();
		this.monthlyWritableList = new WritableList();
		dbLoader = new Dbloader();
		this.startingSerialNumber = dbLoader.getSerialNumber();
		setSerialNumber("" + startingSerialNumber);
		customers = new ArrayList<Customer>();
		monthlyList = new ArrayList<String>();
		bumperList = new ArrayList<String>();
		setDate(new Date());
	}

	private String name;
	private String serialNumber;
	private Date date;
	private String phoneNumber;
	private String emailId;
	private String address;
	private NewCustomer newCustomer;
	private WritableList bumperWritableList;
	private WritableList monthlyWritableList;
	private List<String> bumperList;

	public List<String> getBumperList() {
		return bumperList;
	}

	public List<String> getMonthlyList() {
		return monthlyList;
	}

	private List<String> monthlyList;
	private Dbloader dbLoader;
	private int startingSerialNumber;
	private List<Customer> customers;
	private String selectedBumper;

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
		propertyChangeSupport.firePropertyChange("phoneNumber",
				this.phoneNumber, this.phoneNumber = phoneNumber);
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

	public void addEditCustomer(String series, String ticketNumber,
			String bumperName, boolean isEdit, int selectionIndex) {

		if (Util.isStringNullOrEmpty(bumperName)) {
			if (isEdit) {
				monthlyList.remove(selectionIndex);
				monthlyList.add(selectionIndex, series + ";" + ticketNumber);
			} else
				monthlyList.add(series + ";" + ticketNumber);
		} else {

			if (isEdit) {
				bumperList.remove(selectionIndex);
				bumperList.add(selectionIndex, series + ";" + ticketNumber
						+ ";" + bumperName);
			} else
				bumperList.add(series + ";" + ticketNumber + ";" + bumperName);
		}
		setBumperWritableList(bumperList);
		setMonthlyWritableList(monthlyList);
	}

	public void openAddEditDialog(boolean isBumper, String series,
			String ticketNumber, String bumperName, boolean isEdit,
			int selectionIndex) {

		AddEditMonthlyTicket dialog = new AddEditMonthlyTicket(Display
				.getCurrent().getActiveShell(), this, isBumper, series,
				ticketNumber, bumperName, isEdit, selectionIndex);
		dialog.open();

	}

	public void saveCustomer() {
		int serialNumber = startingSerialNumber;
		List<Customer> customers = new ArrayList<Customer>();
		for (String string : bumperList) {
			String seriesTicketNumberAndBumperName[] = string.split(";");
			Customer customer = new Customer();
			customer.setName(getName());
			customer.setAddress(getAddress());
			customer.setDate(getDate());
			customer.setEmailId(getEmailId());
			customer.setPhoneNumber(getPhoneNumber());
			customer.setSerialNumber(serialNumber++);
			customer.setSeries(seriesTicketNumberAndBumperName[0]);
			customer.setTicketNumber(seriesTicketNumberAndBumperName[1]);
			customer.setBumperName(seriesTicketNumberAndBumperName[2]);
			customer.setLotteryType(Fields.typeBumper);
			customers.add(customer);
		}

		for (String string : monthlyList) {
			String seriesTicketNumber[] = string.split(";");
			Customer customer = new Customer();
			customer.setAddress(getAddress());
			customer.setDate(getDate());
			customer.setName(getName());
			customer.setEmailId(getEmailId());
			customer.setPhoneNumber(getPhoneNumber());
			customer.setSerialNumber(serialNumber++);
			customer.setSeries(seriesTicketNumber[0]);
			customer.setTicketNumber(seriesTicketNumber[1]);
			customer.setLotteryType(Fields.typeMonthly);
			customers.add(customer);
		}

		dbLoader.savePendings(customers, false, false, Constants.pendingMessage);
	}

	private List<Customer> getCustomers() {
		int serialNumber = startingSerialNumber;

		List<Customer> customers = new ArrayList<Customer>();
		for (String string : bumperList) {
			String seriesTicketNumberAndBumperName[] = string.split(";");
			Customer customer = new Customer();
			customer.setName(getName());
			customer.setAddress(getAddress());
			customer.setDate(getDate());
			customer.setEmailId(getEmailId());
			customer.setPhoneNumber(getPhoneNumber());
			customer.setSerialNumber(serialNumber++);
			customer.setSeries(seriesTicketNumberAndBumperName[0]);
			customer.setTicketNumber(seriesTicketNumberAndBumperName[1]);
			customer.setBumperName(seriesTicketNumberAndBumperName[2]);
			customer.setLotteryType(Fields.typeBumper);
			customers.add(customer);
		}

		for (String string : monthlyList) {
			String seriesTicketNumber[] = string.split(";");
			Customer customer = new Customer();
			customer.setAddress(getAddress());
			customer.setDate(getDate());
			customer.setName(getName());
			customer.setEmailId(getEmailId());
			customer.setPhoneNumber(getPhoneNumber());
			customer.setSerialNumber(serialNumber++);
			customer.setSeries(seriesTicketNumber[0]);
			customer.setTicketNumber(seriesTicketNumber[1]);
			customer.setLotteryType(Fields.typeMonthly);
			customers.add(customer);
		}
		return customers;

	}

	public void sendAndDisplayMessage() {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display
				.getCurrent().getActiveShell());

		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					monitor.beginTask("Initializing Sending Process", 3); // begin
																			// task
					monitor.worked(1);

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						LotteryLogger.getInstance().setError(
								"Error while forcing thread to sleep"
										+ e.getMessage());
					}

					monitor.setTaskName("Sending Message where message is "
							+ filterMessageText());
					boolean isMessageSend = sendSms();

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						LotteryLogger.getInstance().setError(
								"Error while forcing thread to sleep"
										+ e.getMessage());
					}
					monitor.worked(2);
					monitor.setTaskName("Saving to database");
					// TODO add mail send functionality
					boolean isMailSend = true;
					dbLoader.savePendings(getCustomers(), isMessageSend,
							isMailSend, "send");
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

	}

	private String filterMessageText() {
		String messageText = "Thanks for buying ticket(s) with Lucky Lottery.";

		if (bumperList.size() + monthlyList.size() >= 5)
			messageText += "Total No. of tickets bought is: "
					+ (bumperList.size() + monthlyList.size());
		else {
			int count = 0;
			String ids = "";
			for (String s : bumperList) {
				if (count >= 5)
					break;
				ids += s.split(";")[1] + ",";
				count++;
			}
			if (count < 5)
				for (String s : monthlyList) {
					if (count >= 5)
						break;
					ids += s.split(";")[1] + ",";
					count++;
				}
			messageText += "Your ticket no.s are " + ids;
		}
		return messageText;
	}

	public boolean sendSms() {
		return true;
		// String str;
		// String messageText = "";
		// if (Util.isStringNullOrEmpty(messageText))
		// messageText = "empty";
		// try {
		// URI uri = new URI("http", messageText, "");
		//
		// // URL onlyMsgUrl = uri.toURL();
		//
		// URL msgUrl = new URL(
		// "http://www.smszone.in/sendsms.asp?page=SendSmsBulk&username="
		// + Util.getUserName() + "&password="
		// + Util.getPassword() + "&number=91"
		// + getNumberText() + "&message="
		// + uri.toString().replace("http:", ""));
		//
		// HttpURLConnection connection = (HttpURLConnection) msgUrl
		// .openConnection();
		// connection.setDoOutput(false);
		// connection.setDoInput(true);
		//
		// String res = connection.getResponseMessage();
		// System.out.println(res);
		// String returnstring = "";
		// int code = connection.getResponseCode();
		//
		// if (code == HttpURLConnection.HTTP_OK) {
		// // Get response data.
		// BufferedReader in = new BufferedReader(new InputStreamReader(
		// connection.getInputStream()));
		//
		// while (null != ((str = in.readLine()))) {
		// returnstring = returnstring + str;
		// }
		// }
		// connection.disconnect();
		// if (returnstring.equals("SUCCESS"))
		// return true;
		// } catch (IOException e) {
		// return false;
		// } catch (URISyntaxException e) {
		// return false;
		// }
		// return false;
	}

	public WritableList getBumperWritableList() {
		return bumperWritableList;
	}

	public void setBumperWritableList(List<String> bumperList) {
		bumperWritableList.clear();
		bumperWritableList.addAll(bumperList);
	}

	public WritableList getMonthlyWritableList() {
		return monthlyWritableList;
	}

	public void setMonthlyWritableList(List<String> monthlyList) {
		this.monthlyWritableList.clear();
		this.monthlyWritableList.addAll(monthlyList);
	}
}
